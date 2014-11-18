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
    public static final char CODE_FILE_SEPARATOR = '|';
    public static final char CODE_FILE_DOT = '.';
    public static final char CODE_FILE_DASH = '-';

    static final Logger logger = Logger.getLogger(MorseCodeTranslationTable.class.getName());

    public MorseCodeTranslationTable(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        Dot dot = new Dot();
        Dash dash = new Dash();
        Space space = new Space();

        this.characterMap = new HashMap();

        String input;

        while ((input = br.readLine()) != null) {
            String codeFileSeparator = new Character(CODE_FILE_SEPARATOR).toString();
            String[] parts = input.split(codeFileSeparator);

            PulseSequence sequence = new PulseSequence();

            if (parts.length == 2) {

                if (parts[0].equals("ut")) {
                    TimingScheme ts = TimingScheme.getInstance();
                    Long timing;
                    try {
                        timing = Long.parseLong(parts[1]);
                        ts.setGlobalTimingUnit(timing);
                    } catch (NumberFormatException e) { /* NOP */ }
                    continue;
                }
                
                for (int current = 0; current < parts[1].length(); current++) {
                    if (parts[1].charAt(current) == CODE_FILE_DOT) {
                        sequence.add(dot);
                    } else if (parts[1].charAt(current) == CODE_FILE_DASH) {
                        sequence.add(dash);
                    }
                }
            }
            this.characterMap.put(parts[0], sequence);
        }

        PulseSequence sequence = new PulseSequence();
        sequence.add(space);
        String separator = new Character(WORD_SEPARATOR).toString();
        characterMap.put(separator, sequence);

    }

    public void play(GpioPinDigitalOutput pin, String inputString) {
        try {

            String separator = new Character(WORD_SEPARATOR).toString();

            //Clean the input string
            String cleanedString = this.cleanInputString(inputString);
            
            if (cleanedString != null) {
                for (int current = 0; current < cleanedString.length(); current++) {
                    Character character = cleanedString.toLowerCase().charAt(current);
                    if (characterMap.containsKey(character.toString())) {
                        characterMap.get(character.toString()).play(pin);
                        //If there is at least one character left
                        if (current + 1 < cleanedString.length()
                                && //And it isn't the word separator
                                cleanedString.charAt(current + 1) != WORD_SEPARATOR) {
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
    
    private String cleanInputString(String input){
        
        StringBuilder cleanedString = new StringBuilder();
        
        for (int current = 0; current < input.length(); current++)
        {
            String thisCharacter = new Character(input.charAt(current)).toString();
            if (this.characterMap.containsKey(thisCharacter)){
                cleanedString.append(thisCharacter);
            }
        }
        logger.info("Cleaned String - " + cleanedString.toString());
        return cleanedString.toString();
    }
}
