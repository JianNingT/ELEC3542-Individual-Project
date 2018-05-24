import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
public class Sensor_LED {
	
    public static void main(String[] args) throws InterruptedException {
    	
        // get a handle to the GPIO controller
    	final GpioController gpio = GpioFactory.getInstance();
    	gpio.shutdown();
        // creating the pin with parameter PinState.HIGH
        // will instantly power up the pin
        final GpioPinDigitalOutput pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "PinLED", PinState.HIGH);
        final GpioPinDigitalOutput pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, "PinLED", PinState.HIGH);
        final GpioPinDigitalOutput pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "PinLED", PinState.HIGH);
        System.out.println("light is: ON");
        
        // turn off GPIO 1
        boolean test = true;
        pin1.low();
        pin2.low();
        pin3.low();
        while(test) {
        	pin1.high();
        	Thread.sleep(1000);
        	pin1.low();
        	pin2.high();
        	Thread.sleep(1000);
            pin2.low();
            pin3.high();
            Thread.sleep(1000);
            pin3.low();
        }
        
        
        System.out.println("light is: OFF");
        // wait 1 second
        Thread.sleep(1000);
        // turn on GPIO 1 for 1 second and then off
        System.out.println("light is: ON for 1 second");
        pin1.pulse(1000, true);
        pin2.pulse(1000, true);
        pin3.pulse(1000, true);
        
        // release the GPIO controller resources
        gpio.shutdown();
    }
}