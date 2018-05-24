//import java.util.Scanner;

public class Sensor_Instance_CameraType extends Thread implements Sensor_Instance{
		
	private int nodeNumber;
	private Integer currentCapacity;
	
	//Threads
	private Sensor_Publish_Thread mqttPublish;
	private Sensor_SensorPackage_CameraType sensorPackage;
	
	
	
	public Sensor_Instance_CameraType(int number) throws Exception{
		this.nodeNumber = number;
		this.currentCapacity = 0;
		
		this.mqttPublish = new Sensor_Publish_Thread( this );
		
		this.sensorPackage = new Sensor_SensorPackage_CameraType(this,1,4,5,6);
        
	}
	
	public void run() {
		this.mqttPublish.start();
		this.sensorPackage.setPriority(10);
		this.sensorPackage.start();
    }
	
	public int getNodeNumber() {
		return this.nodeNumber;
	}
	
	public int getCapacity() {
		return this.currentCapacity;
	}
	
	public void setCapacity( int val ) {
		this.currentCapacity = val;
	}
	
	public void addCapacity(int val) {
		this.currentCapacity += val;
	}

}
