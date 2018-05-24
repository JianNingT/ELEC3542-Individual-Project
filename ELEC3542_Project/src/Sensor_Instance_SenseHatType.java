//import java.util.Scanner;

public class Sensor_Instance_SenseHatType extends Thread implements Sensor_Instance{
		
	private int nodeNumber;
	private Integer currentCapacity;
	private ReadWriteFile SenseHat_Capacity;
	private String SenseHat_FileLoc;
	
	//Thread
	private Sensor_Publish_Thread mqttPublish;
	
	public Sensor_Instance_SenseHatType(int number) throws Exception{
		this.nodeNumber = number;
		this.currentCapacity = 0;
		
		this.mqttPublish = new Sensor_Publish_Thread( this );
        this.mqttPublish.start();
        
        this.SenseHat_Capacity = new ReadWriteFile();
        this.SenseHat_FileLoc = "/home/pi/ELEC3542_Project_Files/Customer_Count.txt";
        
	}
	
	public void run() {
		String input;
		while(true) {
			input = SenseHat_Capacity.readFile(this.SenseHat_FileLoc);
			if( input != "" ) {
				this.currentCapacity = Integer.parseInt( input );
			}
			
		}
        
    }
	
	public int getNodeNumber() {
		return this.nodeNumber;
	}
	
	public int getCapacity() {
		return this.currentCapacity;
	}

}
