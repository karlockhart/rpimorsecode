/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

import co.zer0.raspberrypi.rpimorsecode.PulseSequence;
import co.zer0.raspberrypi.rpimorsecode.TimingScheme;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author klockhart
 */
public class MorseCodeTranslationTable {

    HashMap<String, PulseSequence> characterMap;

    public static final long INTER_LETTER_MULTIPLIER = 3;
    public static final char WORD_SEPARATOR = ' ';
    public static final char CODE_FILE_SEPARATOR = ' ';
    public static final char CODE_FILE_DOT = '.';
    public static final char CODE_FILE_DASH = '-';

    static final Logger logger = Logger.getLogger(MorseCodeTranslationTable.class.getName());

    public MorseCodeTranslationTable(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        //Create instances of the known pulse types
        Dot dot = new Dot();
        Dash dash = new Dash();
        Space space = new Space();

        //Map to store characters
        this.characterMap = new HashMap();

        //Initialize the input string
        String input;

        try {
            //Read all the lines
            while ((input = br.readLine()) != null) {

                //Split on the field separator
                String codeFileSeparator = new Character(CODE_FILE_SEPARATOR).toString();
                String[] parts = input.split(codeFileSeparator);

                //Sequence to store the sequence of pulses
                PulseSequence sequence = new PulseSequence();

                //If the line split into the right number of fields
                if (parts.length == 2) {

                    //Special case for setting the unit time
                    if (parts[0].equals("ut")) {
                        TimingScheme ts = TimingScheme.getInstance();
                        Long timing;
                        try {
                            timing = Long.parseLong(parts[1]);
                            ts.setGlobalTimingUnit(timing);
                        } catch (NumberFormatException e) { /* NOP */ }
                        //No matter what happens continue, trust the init value
                        continue;
                    }

                    //In cases where it isn't a unit time directive
                    for (int current = 0; current < parts[1].length(); current++) {
                        //If it is a dot, add a dot to the sequence
                        if (parts[1].charAt(current) == CODE_FILE_DOT) {
                            sequence.add(dot);
                            //Else add a dash to the sequence
                        } else if (parts[1].charAt(current) == CODE_FILE_DASH) {
                            sequence.add(dash);
                        }
                    }
                }
                //Store the complete pulsetrain in the map
                this.characterMap.put(parts[0].toLowerCase(), sequence);
            }

            //Create the word separator
            PulseSequence sequence = new PulseSequence();
            sequence.add(space);
            String separator = new Character(WORD_SEPARATOR).toString();
            characterMap.put(separator, sequence);

            br.close();

        } catch (IOException ex) {
            throw ex;
        } finally {
            br.close();
        }

    }

    public void play(GpioPinDigitalOutput pin, String inputString) {
        //Clean the input string
        String cleanedString = this.cleanInputString(inputString.toLowerCase());

        try {
            if (cleanedString != null) {
                for (int current = 0; current < cleanedString.length(); current++) {
                    Character character = cleanedString.charAt(current);
                    if (characterMap.containsKey(character.toString())) {
                        characterMap.get(character.toString()).play(pin);
                        //If there is at least one character left
                        if (current + 1 < cleanedString.length()
                                && //And it isn't the word separator
                                cleanedString.charAt(current + 1) != WORD_SEPARATOR) {
                            logger.log(Level.INFO, "Wait: {0}", (TimingScheme.getInstance().getGlobalTimingUnit()
                                    * INTER_LETTER_MULTIPLIER));
                            Thread.sleep(TimingScheme.getInstance().getGlobalTimingUnit()
                                    * INTER_LETTER_MULTIPLIER);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MorseCodeTranslationTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String cleanInputString(String input) {
        //A string builder to store the cleaned string
        StringBuilder cleanedString = new StringBuilder();

        //For each character of the input string
        for (int current = 0; current < input.length(); current++) {
            String thisCharacter = new Character(input.charAt(current)).toString();
            //Check if the character is in the map
            if (this.characterMap.containsKey(thisCharacter)) {
                cleanedString.append(thisCharacter);
            } else {
                logger.warning("Character " + thisCharacter + " is not defined in the definition file.");
                //continue
            }
        }
        logger.info("The cleaned string: " + cleanedString.toString());
        //Return the cleaned string
        return cleanedString.toString();
    }
}
