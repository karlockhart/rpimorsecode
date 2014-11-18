/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.zer0.raspberrypi.rpimorsecode;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 *
 * @author klockhart
 */
public interface Playable {

    public void play(GpioPinDigitalOutput pin) throws Exception;
}
