/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author klockhart
 */
public class PulseSequence implements Playable {

    static final Logger logger = Logger.getLogger(PulseSequence.class.getName());

    ArrayList<Pulse> pulseTrain;

    public PulseSequence() {
        pulseTrain = new ArrayList();
    }

    @Override
    public void play(GpioPinDigitalOutput pin) throws Exception {
        Iterator ptIterator = pulseTrain.iterator();

        for (Iterator<Pulse> pulseIterator = pulseTrain.iterator(); pulseIterator.hasNext();) {
            (pulseIterator.next()).play(pin);

            //If this is between elements of a pulse train
            if (pulseIterator.hasNext()) {
                //One unit sleep
                logger.log(Level.INFO, "Wait: {0}", TimingScheme.getInstance().getGlobalTimingUnit());
                Thread.sleep((long) TimingScheme.getInstance().getGlobalTimingUnit());
            }
        }
    }

    public boolean add(Pulse pulse) {
        return this.pulseTrain.add(pulse);
    }

}
