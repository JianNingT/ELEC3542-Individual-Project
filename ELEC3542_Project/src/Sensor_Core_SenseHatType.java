
public class Sensor_Core_SenseHatType {
	
	public static void main(String[] args) throws Exception {
		Sensor_Instance_SenseHatType pub;
		if(args.length==1){
			pub = new Sensor_Instance_SenseHatType(Integer.valueOf(args[0]));
		}
		else{
			pub = new Sensor_Instance_SenseHatType(0);
		}
		pub.start();
    }
	
}