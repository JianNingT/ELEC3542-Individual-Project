import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTT_Subscribe implements MqttCallback{
	
	MqttClient client;
	MemoryPersistence persistence = new MemoryPersistence();
	
	public MQTT_Subscribe() {
	}
	
	public static void main(String[] args) {
	    new MQTT_Subscribe().doDemo();
	}
	
	public void doDemo() {
	    try {
	        client = new MqttClient("tcp://iot.eclipse.org:1883", "Server");
	        client.connect();
	        client.setCallback(this);
	        client.subscribe("ELEC3542_Alex/#");
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void connectionLost(Throwable cause) {
	    // TODO Auto-generated method stub
	
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message)
	        throws Exception {
		System.out.println(topic);
	 System.out.println(message);   
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	    // TODO Auto-generated method stub
	
	}

}