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
public class Dash extends MorseCodePulse{
    
    public static final long DASH_MULTIPLIER = 3;
    
    Dash(){
        this.multiplier = DASH_MULTIPLIER;
    }
}
