/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author klockhart
 */
public class PulseSequence implements Playable {
   
   ArrayList<Pulse> pulseTrain;
    
   @Override
    public void play(GpioPinDigitalOutput pin) throws Exception {
        Iterator ptIterator = pulseTrain.iterator();
        
        while (pulseTrain.iterator().hasNext()){
             pulseTrain.iterator().next().play(pin);
        }
   }
   
   public boolean Add(Pulse pulse){
       return pulseTrain.add(pulse);
   }
   
}
