/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import co.zer0.raspberrypi.rpimorsecode.morsecodeelements.MorseCodeTranslationTable;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
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
        
    }
    
    public void translate(GpioPinDigitalOutput pin, String message){
        //For testing the global timing unit is 1 sec
        TimingScheme ts = TimingScheme.getInstance();
        ts.setGlobalTimingUnit(1000);
        
        //Create a new translation table
        MorseCodeTranslationTable table;
        try {
            //Populate it from the morse code definition file
            table = new MorseCodeTranslationTable(
                    new File("./morse_code.def"));
            //Test by playing simple messge
            table.play(pin, message);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }             
    }
    
}
