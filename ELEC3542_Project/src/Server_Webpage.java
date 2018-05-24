
public class Server_Webpage extends Thread {

	private Server_Instance parent;
	private int numNodes;
	private int[] nodeCapacities;
	private String[] nodeStatus;
	private final int update_minimalWaitTime = 5000;
	private final int update_minimalIncrease = 5;
	private ReadWriteFile RF;
	private DateTime DT;
	
	private String publicHTMLFile;
	
	private String fileLocation_HTMLResources;
	private String htmlBase1;
	private String htmlUpdateTime;
	private String htmlBase2;
	private String htmlUpdateTable;
	private String htmlBase3;
	
	public Server_Webpage( Server_Instance parent ) {
		this.parent = parent;
		this.publicHTMLFile = "/var/www/html/index.html";
		this.Server_Webpage_Constructor();
	}
	
	public Server_Webpage( Server_Instance parent , String hmtlFileLocation ) {
		this.parent = parent;
		this.publicHTMLFile = hmtlFileLocation;
		this.Server_Webpage_Constructor();
	}
	
	private void Server_Webpage_Constructor(){
		RF = new ReadWriteFile();
		DT = new DateTime();
		this.fileLocation_HTMLResources = "/home/pi/ELEC3542_Project_Files/HTMLFiles/";
		this.numNodes = this.parent.getNumberOfNodes();
		this.nodeCapacities = new int[this.numNodes];
		for( int i=0 ; i < this.numNodes ; i++) {
			this.nodeCapacities[i] = 0;
		}
		this.nodeStatus = new String[this.numNodes];
		for( int i=0 ; i < this.numNodes ; i++) {
			this.nodeStatus[i] = "Good";
		}
		this.htmlBase1 = RF.readFile( this.fileLocation_HTMLResources + "HTML_Base_1.txt" );
		this.htmlBase2 = RF.readFile( this.fileLocation_HTMLResources + "HTML_Base_2.txt" );
		this.htmlBase3 = RF.readFile( this.fileLocation_HTMLResources + "HTML_Base_3.txt" );
	}
	
	private void writeHTML() {
		RF.writeFile(this.publicHTMLFile, this.htmlBase1 + this.htmlUpdateTime + this.htmlBase2 + this.htmlUpdateTable + this.htmlBase3 );
	}
	
	private void updateHTMLTime() {
		this.htmlUpdateTime =
				DT.getDayOfWeek_String() + " , " +
				DT.getDay() + "-" +
				DT.getMonth() + "-" +
				DT.getYear() + " , " +
				DT.getHour() + ":" +
				DT.getMinute() + ":" +
				DT.getSecond();
	}
	
	private void updateHTMLTable() {
		this.htmlUpdateTable =	"<table><tr>" +
								"<th>No.</th>" +
								"<th>Restaurant Name</th>" +
								"<th>Status</th>" +
								"<th>Capacity</th>" +
								"</tr>";
		for( int i=1 ; i <= this.numNodes ; i++) {
			this.nodeCapacities[i-1] = parent.currentCapacityContainer_Get( i );
			this.nodeStatus[i-1] = parent.currentStatusContainer_Get( i );
			this.htmlUpdateTable = 	this.htmlUpdateTable + "<tr>" +
									"<td>" + i + "</td>" +
									"<td>" + "Sensor Node " + i + "</td>" +
									"<td>" + this.nodeStatus[i-1] + "</td>" +
									"<td>" + this.nodeCapacities[i-1] + "</td>" +
									"</tr>";
			
		}
		this.htmlUpdateTable = this.htmlUpdateTable + "</table>";
	}
	
	public void run() {
		long timeStamp;
		while(true){
			timeStamp = System.currentTimeMillis();
			while( System.currentTimeMillis() - timeStamp < update_minimalWaitTime ) {
				for( int i=1 ; i <= this.numNodes ; i++ ) {
					if( parent.currentCapacityContainer_Get( i ) > this.nodeCapacities[i-1] + update_minimalIncrease ) {
						break;
					}
				}
			}
			this.updateHTMLTime();
			this.updateHTMLTable();
			this.writeHTML();
		}
	}

}
