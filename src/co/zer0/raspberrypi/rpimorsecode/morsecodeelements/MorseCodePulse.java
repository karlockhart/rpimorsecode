/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

import co.zer0.raspberrypi.rpimorsecode.Pulse;
import co.zer0.raspberrypi.rpimorsecode.TimingScheme;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 *
 * @author klockhart
 */
public abstract class MorseCodePulse extends Pulse {

    protected long multiplier;
    protected boolean setPinHigh = true;

    @Override
    public void play(GpioPinDigitalOutput pin) throws Exception {
        TimingScheme timingScheme = TimingScheme.getInstance();
        
        if (this.setPinHigh == true){
            pin.high();
        }
        Thread.sleep((long) (timingScheme.getGlobalTimingUnit() * this.multiplier));
        pin.low();
        Thread.sleep((long) timingScheme.getGlobalTimingUnit());
    }
       
}
