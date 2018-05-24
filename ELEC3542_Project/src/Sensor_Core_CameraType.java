public class Sensor_Core_CameraType {
	
	public static void main(String[] args) throws Exception {
		Sensor_Instance_CameraType pub;
		if(args.length==1){
			pub = new Sensor_Instance_CameraType(Integer.valueOf(args[0]));
		}
		else{
			pub = new Sensor_Instance_CameraType(0);
		}
		pub.start();
    }
	
}