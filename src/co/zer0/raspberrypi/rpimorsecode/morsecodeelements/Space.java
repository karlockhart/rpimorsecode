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
public class Space extends MorseCodePulse {

    public static final long SPACE_MULTIPLIER = 7;

    Space() {
        this.multiplier = SPACE_MULTIPLIER;

        //The space requires the pin to stay low
        this.setPinHigh = false;
    }
}
