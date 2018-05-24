import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Server_Subscribe_Thread extends Thread implements MqttCallback{
	
	private MqttClient client;
	
//	private MemoryPersistence persistence = new MemoryPersistence();
	
	private String topic;
	private Server_Instance parent;
	private int numNodes;
	
	
	public Server_Subscribe_Thread() {
	}
	
	public Server_Subscribe_Thread( Server_Instance parent ) throws Exception{
		this.parent = parent;
		this.numNodes = parent.getNumberOfNodes();
		
		this.topic = "ELEC3542_Alex/Node/#";
		
	}
	
	public void run() {
	    try {
	        client = new MqttClient("tcp://iot.eclipse.org:1883", "Server");
	        MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
	        client.connect();
	        client.setCallback(this);
	        client.subscribe(this.topic);
//	        while(true){
	        	
//	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	@Override
	public void connectionLost(Throwable cause) {
	    // TODO Auto-generated method stub
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String list[] = topic.split("/");
		parent.currentCapacityContainer_Set( Integer.valueOf(message.toString()) , Integer.valueOf(list[2]) );
		
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	    // TODO Auto-generated method stub
	}

}