
public class Server_Instance {
	
	private int numberOfNodes = 3;
	
	private Server_Subscribe_Thread MQTTSub;
	private Server_DatabaseHandler[] DatabaseHandler;
	private Server_DatabaseAlgorithm[] AlgorithmHandler;
	private Server_Webpage Web_Handler;
	
	private int[] currentCapacityContainer ;
	private String[] currentStatusContainer ;
	
	public Server_Instance() throws Exception{
		this.setup();
	}
	
	public Server_Instance( int Node_Num )throws Exception{
		this.numberOfNodes = Node_Num;
		this.setup();
	}
	
	private void setup() throws Exception{
		this.MQTTSub = new Server_Subscribe_Thread(this);
		
		this.Web_Handler = new Server_Webpage( this );
		
		DatabaseHandler = new Server_DatabaseHandler[this.numberOfNodes]; 
		for(int i=0 ; i<this.numberOfNodes ; i++){
			DatabaseHandler[i] = new Server_DatabaseHandler( i+1 , this );
		}
		
		AlgorithmHandler = new Server_DatabaseAlgorithm[this.numberOfNodes]; 
		for(int i=0 ; i<this.numberOfNodes ; i++){
			AlgorithmHandler[i] = new Server_DatabaseAlgorithm( i+1 , this , DatabaseHandler[i]);
			AlgorithmHandler[i].setMaxCapacity(20);
		}
		
		this.currentCapacityContainer = new int[this.numberOfNodes];
		for(int i=0 ; i<this.numberOfNodes ; i++){
			this.currentCapacityContainer[i] = 0;
		}
		
		this.currentStatusContainer = new String[this.numberOfNodes];
		for(int i=0 ; i<this.numberOfNodes ; i++){
			this.currentStatusContainer[i] = "Good";
		}
	}
	
	public int getNumberOfNodes() {
		return this.numberOfNodes;
	}
	
	public void currentCapacityContainer_Print() {
		System.out.print( "[" );
		for (Integer I : this.currentCapacityContainer) {
			System.out.print( " " + I + " "  );
		}
		System.out.println( "]" );
	}
	
	public void currentCapacityContainer_Set( int val, int slot ) {
		this.currentCapacityContainer[slot-1] = val;
		this.currentCapacityContainer_Print();
	}
	
	public int currentCapacityContainer_Get( int num ) {
		return this.currentCapacityContainer[ num-1 ];
	}

	public void currentStatusContainer_Set( String val, int slot ) {
		this.currentStatusContainer[slot-1] = val;
	}
	
	public String currentStatusContainer_Get( int num ) {
		return this.currentStatusContainer[ num-1 ];
	}
	
	public void start() throws InterruptedException{
		
		MQTTSub.start();
		this.Web_Handler.start();
		
		for( Server_DatabaseHandler d : DatabaseHandler ) {
			d.start();
		}
		for( Server_DatabaseAlgorithm d : AlgorithmHandler ) {
			d.start();
		}
		
    }
	
}
