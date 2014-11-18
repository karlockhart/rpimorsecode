/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode.morsecodeelements;

import co.zer0.raspberrypi.rpimorsecode.Pulse;
import co.zer0.raspberrypi.rpimorsecode.TimingScheme;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author klockhart
 */
public abstract class MorseCodePulse extends Pulse {

    protected long multiplier;
    protected boolean setPinHigh = true;
    
    static final Logger logger = Logger.getLogger(MorseCodePulse.class.getName());

    @Override
    public void play(GpioPinDigitalOutput pin) throws Exception {
        TimingScheme timingScheme = TimingScheme.getInstance();

        if (this.setPinHigh == true) {
            logger.info("H");
            //Output high to the pin
            pin.high();
            
        }
        //Sleep the required time
        logger.log(Level.INFO, "Wait: {0}", (timingScheme.getGlobalTimingUnit() * this.multiplier));
        Thread.sleep((long) (timingScheme.getGlobalTimingUnit() * this.multiplier));
        logger.info("L");
        //Bring the pin low
        pin.low();
    }

}
