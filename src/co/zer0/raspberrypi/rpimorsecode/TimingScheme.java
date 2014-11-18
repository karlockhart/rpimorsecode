/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import java.util.logging.Logger;

/**
 *
 * @author klockhart
 */
//@Singleton
public class TimingScheme {

    static final Logger logger = Logger.getLogger(TimingScheme.class.getName());
    
    //Instance variable
    private static TimingScheme instance;

    //Default the unit time to 250ms
    public static final long DEFAULT_UNIT_TIME = 100;

    //The global unit time is the length of one unit in uS
    private static long unitTime;

    private TimingScheme() {

        this.setGlobalTimingUnit(DEFAULT_UNIT_TIME);
    }

    public static TimingScheme getInstance() {
        if (instance == null) {
            instance = new TimingScheme();
        }

        return instance;
    }

    //Get the current global unit time
    public long getGlobalTimingUnit() {
        return unitTime;
    }

    //Set the global unit time
    public void setGlobalTimingUnit(long newUnitTime) {
        logger.info("Setting global unit time to: " + newUnitTime);
        unitTime = newUnitTime;
    }
}
