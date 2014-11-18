
import co.zer0.raspberrypi.rpimorsecode.RpiMorseCode;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author klockhart
 */
public class Application {
   public static void main(String[] args){
       RpiMorseCode morse = new RpiMorseCode();
       
       final GpioController gpio = GpioFactory.getInstance();
       final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
       
       if (args.length > 1){
           morse.translate(pin, args[0]);
       }
       
   } 
}
