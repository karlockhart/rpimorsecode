/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

import co.zer0.raspberrypi.rpimorsecode.PulseSequence;
import co.zer0.raspberrypi.rpimorsecode.RpiMorseCode;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 *
 * @author klockhart
 */
public class MorseCodeTranslationTable {
   
   HashMap<String, PulseSequence> characterMap;
   
   static Logger logger = Logger.getLogger(MorseCodeTranslationTable.class.getName());

   
   public MorseCodeTranslationTable(File file) throws FileNotFoundException, IOException{
       BufferedReader br = new BufferedReader(new FileReader(file));
       
       Dot dot = new Dot();
       Dash dash = new Dash();
       Space space = new Space();
               
       this.characterMap = new HashMap();
       
       String input = null;
       while((input = br.readLine()) != null){
          String[] parts =  input.split(",");

          PulseSequence sequence = new PulseSequence();
          
          if (parts.length == 2){
              for (int current = 0; current < parts[1].length(); current++ ){
                  if (parts[1].charAt(current) == '.'){
                      sequence.add(dot);                     
                  }else if(parts[1].charAt(current) == '-'){
                      sequence.add(dash);
                  }
              }
          }          
          this.characterMap.put(parts[0], sequence);          
       }
       
       PulseSequence sequence = new PulseSequence();
       sequence.add(space);
       characterMap.put(" ", sequence);

   } 
}
