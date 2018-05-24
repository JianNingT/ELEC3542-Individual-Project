
public class Sensor_SensorPackage_CameraType extends Thread{
	
	private Sensor_Instance_CameraType parent;
	
	private Sensor_Ultrasonic ultrasonic1;
	private Sensor_Ultrasonic ultrasonic2;
	private double distanceThreshold;
	
	private Sensor_PiCamera piCamera;
	private Sensor_Image imageProcessor;
	
	public Sensor_SensorPackage_CameraType( Sensor_Instance_CameraType parent , int trig1 , int echo1 , int trig2 , int echo2 ) throws Exception{
		this.parent = parent;
		
		this.ultrasonic1 = new Sensor_Ultrasonic( trig1 , echo1 );
		this.ultrasonic2 = new Sensor_Ultrasonic( trig2 , echo2 );
		this.distanceThreshold = 10;
		
		this.piCamera = new Sensor_PiCamera("/home/pi/Pictures"); // Specify picture directory
        this.piCamera.setTimeout(5); // time to wait before taking picture ( 1000 = 1 second )
        this.piCamera.setWidth(50); // Default Width = 500
        this.piCamera.setHeight(50); // Default Width = 500
        this.piCamera.setQuality(50); // Maximum Quality = 100
        
        this.imageProcessor = new Sensor_Image();
        this.imageProcessor.setSizeThreshold(1000); // Size threshold for object detection in image
        
        this.piCamera.shootStill("calibrate_reference.jpg");
		
	}
	
	public void run() {
		while(true) {
			int peopleCount;
			int sensor1Stat = 0;
			int sensor2Stat = 0;
			while ( !ultrasonic1.getDetected(distanceThreshold) && !ultrasonic2.getDetected(distanceThreshold) ) {
				try {
					Thread.sleep(500);
				}catch(Exception e) {
					
				}
			}
			System.out.println("object detected");
			boolean sensor1Detect_A = ultrasonic1.getDetected(distanceThreshold);
			boolean sensor2Detect_A = ultrasonic2.getDetected(distanceThreshold);
			piCamera.shootStill("current.jpg");
			boolean sensor1Detect_B = ultrasonic1.getDetected(distanceThreshold);
			boolean sensor2Detect_B = ultrasonic2.getDetected(distanceThreshold);
			while ( ultrasonic1.getDetected(distanceThreshold) || ultrasonic2.getDetected(distanceThreshold) ) {
				sensor1Detect_B = ultrasonic1.getDetected(distanceThreshold);
				sensor2Detect_B = ultrasonic2.getDetected(distanceThreshold);
			}
			System.out.println("object left");
			if(sensor1Detect_A) {
				sensor1Stat += 1;
			}
			if(sensor1Detect_B) {
				sensor1Stat -= 1;
			}
			if(sensor2Detect_A) {
				sensor2Stat	+= 1;
			}
			if(sensor2Detect_B) {
				sensor2Stat -= 1;
			}
			if( sensor1Stat != sensor2Stat ) {
				System.out.println("start count");
				peopleCount = imageProcessor.countClusters("calibrate_reference.jpg","current.jpg");
				if( sensor1Stat < sensor2Stat ) {
					peopleCount = -1*peopleCount;
				}
				System.out.println("counted: " + peopleCount);
				parent.addCapacity( peopleCount );
			}
			
		}
		
	}
	
//	public void printDistance() {
//		System.out.println( this.ultrasonic1.getDistance() + " , " + this.ultrasonic2.getDistance() );
//	}
	
	
	
}
