/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

/**
 *
 * @author klockhart
 */
public class TimingScheme {
    private static TimingScheme instance;
    private static long unitTime;
    
    private TimingScheme() {
        unitTime = 250;
    }
    
    public static TimingScheme getInstance(){
        if (instance == null)
        {
            instance = new TimingScheme();
        }
        
        return instance;
    }
    
    public double getGlobalTimingUnit(){
        return unitTime;
    }
    
    public void setGlobalTimingUnit(long newUnitTime){
        unitTime = newUnitTime;
    }
}
