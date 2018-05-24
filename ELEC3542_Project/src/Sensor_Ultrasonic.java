import com.pi4j.io.gpio.*;

public class Sensor_Ultrasonic extends Thread {
	private GpioController gpio;
	private GpioPinDigitalOutput sensorTriggerPin;
	private GpioPinDigitalInput sensorEchoPin;
	private double distance;
	
	public Sensor_Ultrasonic() {
		this.gpio = GpioFactory.getInstance();
		this.distance = 999;
	}
	
	public Sensor_Ultrasonic( int triggerPin, int echoPin ) {
		this.gpio = GpioFactory.getInstance();
		this.distance = 999;
		this.setTriggerPin( triggerPin );
		this.setEchoPin( echoPin );
	}
	
	public boolean getDetected( double threshold ) {
		try {
			Thread.sleep(10);
			sensorTriggerPin.high();
			Thread.sleep((long) 0.01);
			sensorTriggerPin.low();
		
			while(sensorEchoPin.isLow()){}
			long startTime= System.nanoTime();
			while(sensorEchoPin.isHigh()){}
			long endTime= System.nanoTime();
			
			this.distance = ((((endTime-startTime)/1e3)/2) / 29.1);
		} catch (Exception e) {
			
		}
		return (this.distance < threshold);
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public void setTriggerPin( int pin ) {
		if( pin == 0 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
		}
		if( pin == 1 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
		}
		if( pin == 2 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
		}
		if( pin == 3 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);
		}
		if( pin == 4 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
		}
		if( pin == 5 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
		}
		if( pin == 6 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
		}
		if( pin == 7 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07);
		}
		if( pin == 21 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
		}
		if( pin == 22 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);
		}
		if( pin == 23 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
		}
		if( pin == 24 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24);
		}
		if( pin == 25 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25);
		}
		if( pin == 26 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26);
		}
		if( pin == 27 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27);
		}
		if( pin == 28 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28);
		}
		if( pin == 29 ) {
			this.sensorTriggerPin =  gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29);
		}
	}
	
	public void setEchoPin( int pin ) {
		if( pin == 0 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_00,PinPullResistance.PULL_DOWN);
		}
		if( pin == 1 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_01,PinPullResistance.PULL_DOWN);
		}
		if( pin == 2 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02,PinPullResistance.PULL_DOWN);
		}
		if( pin == 3 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03,PinPullResistance.PULL_DOWN);
		}
		if( pin == 4 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04,PinPullResistance.PULL_DOWN);
		}
		if( pin == 5 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05,PinPullResistance.PULL_DOWN);
		}
		if( pin == 6 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06,PinPullResistance.PULL_DOWN);
		}
		if( pin == 7 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07,PinPullResistance.PULL_DOWN);
		}
		if( pin == 21 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_21,PinPullResistance.PULL_DOWN);
		}
		if( pin == 22 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_22,PinPullResistance.PULL_DOWN);
		}
		if( pin == 23 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_23,PinPullResistance.PULL_DOWN);
		}
		if( pin == 24 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_24,PinPullResistance.PULL_DOWN);
		}
		if( pin == 25 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25,PinPullResistance.PULL_DOWN);
		}
		if( pin == 26 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_26,PinPullResistance.PULL_DOWN);
		}
		if( pin == 27 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_27,PinPullResistance.PULL_DOWN);
		}
		if( pin == 28 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_28,PinPullResistance.PULL_DOWN);
		}
		if( pin == 29 ) {
			this.sensorEchoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29,PinPullResistance.PULL_DOWN);
		}
	}
	
}
