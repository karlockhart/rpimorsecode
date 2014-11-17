/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import co.zer0.raspberrypi.rpimorsecode.morsecodeelements.MorseCodeTranslationTable;
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
    public static void main(String[] args) {
        try {
            MorseCodeTranslationTable table = new MorseCodeTranslationTable(
                    new File("./morse_code.def"));
        } catch (IOException ex) {
            Logger.getLogger(RpiMorseCode.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
