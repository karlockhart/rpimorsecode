/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import co.zer0.raspberrypi.rpimorsecode.morsecodeelements.MorseCodeTranslationTable;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author klockhart
 */
public class RpiMorseCode {

    /**
     * @param args the command line arguments
     */

    private static final Logger logger = Logger.getLogger(RpiMorseCode.class.getName());

    public static void main(String[] args) {
        RpiMorseCode morse = new RpiMorseCode();

        final GpioController gpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MORSE", PinState.LOW);

        if (args.length >= 1) {
            morse.translate(pin, args[0]);
        }else{
            logger.log(Level.SEVERE, "Arguments: \"message\" <debug>");
            System.exit(1);
        }
        
        if (args.length >= 2 && args[1].equals("debug"))
        {
            Logger.getGlobal().setLevel(Level.ALL);
        }else{
            Logger.getGlobal().setLevel(Level.WARNING);
        }
    }

    public void translate(GpioPinDigitalOutput pin, String message) {

        //Create a new translation table
        MorseCodeTranslationTable table;
        try {
            //Populate it from the morse code definition file
            table = new MorseCodeTranslationTable(
                    new File("./morse_code.def"));
            table.play(pin, message);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

}
