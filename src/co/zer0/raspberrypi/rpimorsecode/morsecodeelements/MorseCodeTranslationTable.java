/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

import co.zer0.raspberrypi.rpimorsecode.PulseSequence;
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
   
   static final Logger logger = Logger.getLogger(MorseCodeTranslationTable.class.getName());

   
   public MorseCodeTranslationTable(File file) throws FileNotFoundException, IOException{
       BufferedReader br = new BufferedReader(new FileReader(file));
       
       Dot dot = new Dot();
       Dash dash = new Dash();
       Space space = new Space();
               
       this.characterMap = new HashMap();
       
       String input;
       input = null;
       
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
   
   public void play(GpioPinDigitalOutput pin, String string){
       try {
           if (string != null){
               for(int current = 0; current < string.length(); current++){
                   Character character = string.toLowerCase().charAt(current);
                   if (characterMap.containsKey(character.toString())){
                       characterMap.get(character.toString()).play(pin);
                   }
               }
           }
       } catch (Exception ex) {
           Logger.getLogger(MorseCodeTranslationTable.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
