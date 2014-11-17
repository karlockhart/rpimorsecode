/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

import co.zer0.raspberrypi.rpimorsecode.PulseSequence;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author klockhart
 */
public class MorseCodeTranslationTable {
   HashMap<String, PulseSequence> characterMap;
   public MorseCodeTranslationTable(File file){
       System.out.println(file.toString());
   } 
}
