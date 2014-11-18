/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

/**
 *
 * @author klockhart
 */
public class Dot extends MorseCodePulse {
    
    public static final long DOT_MULTIPLIER = 1;
    
    Dot(){
        this.multiplier = DOT_MULTIPLIER;
    }
}
