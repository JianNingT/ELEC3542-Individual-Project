import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Sensor_Publish_Thread extends Thread {
	
	private final Sensor_Instance parent;
	private int nodeNumber;
	
	public Sensor_Publish_Thread() throws Exception{
		this.parent = null;
		nodeNumber = 0;
	}
	
	public Sensor_Publish_Thread(Sensor_Instance parent) throws Exception{
		this.parent = parent;
		nodeNumber = parent.getNodeNumber();
	}
	
	public void run() {
		
        String topic	= "ELEC3542_Alex/Node/" + nodeNumber;
        String content      	= "";
        int qos 				= 0;
        String broker       	= "tcp://iot.eclipse.org:1883";
        String clientId     	= "Node" + nodeNumber;
        MemoryPersistence persistence = new MemoryPersistence();
        while( true ) {
	        try {
	            MqttClient Client = new MqttClient(broker, clientId, persistence);
	            MqttConnectOptions connOpts = new MqttConnectOptions();
	            connOpts.setCleanSession(true);
	            System.out.println("Connecting to broker: "+broker);
	            Client.connect(connOpts);
	            System.out.println("Connected");
	            System.out.println("Publishing messages");
	            MqttMessage message = new MqttMessage(content.getBytes());
	            message.setQos(qos);
	            while( parent.getCapacity() >= 0 || parent.getCapacity() <= 0 ){
	            	message.setPayload( String.valueOf( parent.getCapacity() ).getBytes() );
	            	Client.publish(topic, message);
	        		Thread.sleep(1000);
	            }
	            
	            Client.disconnect();
	            System.out.println("Disconnected");
	        } catch(Exception e) {
	        	
	        }
        }
    }
	
}
