
public class Server_DatabaseAlgorithm extends Thread {
	
	private Server_Instance parent;
	private Server_DatabaseHandler database;
	private int nodeNumber;
	private int maxCapacity;
	private LocalData currentData;
	
	public Server_DatabaseAlgorithm( int nodeNum, Server_Instance parent ,Server_DatabaseHandler database ){
		this.parent = parent;
		this.nodeNumber = nodeNum;
		this.maxCapacity = Integer.MAX_VALUE;
		this.database = database;
		this.currentData = database.getData( database.database_timeToSlot_Prev() );
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
				if( parent.currentCapacityContainer_Get(nodeNumber) > currentData.capacity()*2 ) {
					parent.currentStatusContainer_Set( "Full Soon" , nodeNumber );
				}
				else if( parent.currentCapacityContainer_Get(nodeNumber) >= maxCapacity ) {
					parent.currentStatusContainer_Set( "NOW FULL" , nodeNumber );
				}
				else {
					parent.currentStatusContainer_Set( "Good" , nodeNumber );
				}
			}catch(Exception e) {
				
			}
			
		}
	}
	
	public void setMaxCapacity( int max ) {
		this.maxCapacity = max;
	}
	
}
