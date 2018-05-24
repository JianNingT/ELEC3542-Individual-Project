
public class Server_DatabaseHandler extends Thread {
	
	private Server_Instance parent;
	private int nodeNumber;
	
	private int operationStart;
	private LocalData operationEnd;
	private String day_week;
	private String day_type;
	
	private DateTime DT;
	private ReadWriteFile RF;
	
	private LocalData database_current;
	private LocalData[] database_dayWeek;
	private LocalData[] database_dayType;
	private LocalData[] database_today;
	private String NodeDatabaseDirectory;
	
	private double dayWeek_Update_Weightage = 0.2;
	private double dayType_Update_Weightage = 0.05;
	
	public Server_DatabaseHandler() {
	}
	
	public Server_DatabaseHandler( int nodeNum, Server_Instance parent ) {
		this.parent = parent;
		this.nodeNumber = nodeNum;
		DT = new DateTime();
		RF = new ReadWriteFile();
		
		this.operationStart = 6;
		this.operationEnd = new LocalData(24,0,0);
		
		NodeDatabaseDirectory = "DatabaseFiles/" + this.nodeNumber + "/";
		this.day_week = DT.getDayOfWeek_String();
		boolean day_type_checker = true;
		
		if( day_type_checker ){
			this.day_type = "Normal";
		}else {
			this.day_type = "Holiday";
		}
		
		this.database_today = new LocalData[ 12*18 + 1 ];
		int i=0;
	    for(int h=0; h<18; h+=1){
	        for(int m=0; m<60; m+=5){
	        	this.database_today[i] = new LocalData( this.operationStart + h , m , 0 );
	            i++;
	        }
	    }
	    this.database_today[ 12*18 ] = this.operationEnd;
		
		this.database_dayWeek = RF.readDatabase(NodeDatabaseDirectory + "Day-" + this.day_week + ".txt");
		this.database_dayType = RF.readDatabase(NodeDatabaseDirectory + "Type-" + this.day_type + ".txt");
		
		System.out.println(NodeDatabaseDirectory + "Day-" + this.day_week + ".txt");
		System.out.println(NodeDatabaseDirectory + "Type-" + this.day_type + ".txt");
		
	}
	
	public LocalData getData( int i ) {
		if( i >= 0 && i <= 12*18 ) {
			return this.database_today[ i ];
		}
		return null;
	}
	public int database_timeToSlot_Next() {
		int mnTemp = 5* (int) (DT.getMinute()/5);
		int hrTemp = DT.getHour() - this.operationStart;
		
		return (hrTemp*12 + mnTemp + 1);
	}
	
	private int database_timeToSlot_Next(int hourNow, int minNow) {
		int mnTemp = 5* (int) (minNow/5);
		int hrTemp = hourNow - this.operationStart;
		
		return (hrTemp*12 + mnTemp + 1);
	}
	
	public int database_timeToSlot_Prev() {
		int mnTemp = 5* (int) (DT.getMinute()/5);
		int hrTemp = DT.getHour() - this.operationStart;
		
		return (hrTemp*12 + mnTemp);
	}
	
	private int database_timeToSlot_Prev(int hourNow, int minNow) {
		int mnTemp = 5* (int) (minNow/5);
		int hrTemp = hourNow - this.operationStart;
		
		return (hrTemp*12 + mnTemp);
	}
	
	private void databasePrint(LocalData[] database) {
		for(LocalData i : database) {
			System.out.println(i);
		}
	}
	
	
	
	public void run() {
		
		database_current = database_today[0];
		int currentCapacity = parent.currentCapacityContainer_Get(this.nodeNumber);
		
		while( database_current != this.operationEnd ) {
			
			if( currentCapacity != parent.currentCapacityContainer_Get(this.nodeNumber) ) {
				currentCapacity = parent.currentCapacityContainer_Get(this.nodeNumber);
			}
			
			if( ( DT.getHour() > database_current.hour() ) || ( ( DT.getHour() >= database_current.hour() ) &&
				( DT.getMinute() > database_current.mins() ) ) ) {
				database_current.setCapacity( currentCapacity );
				database_current = database_today[ this.database_timeToSlot_Next() ];
			}
			
		}
		
		LocalData[] database_temp = new LocalData[12*18];
		int i;
		
		i=0;
	    for(int h=0; h<18; h+=1){
	        for(int m=0; m<60; m+=5){
	        	database_temp[i] = new LocalData( this.operationStart + h , m , 
	        									( this.database_today[i].capacity()*(this.dayWeek_Update_Weightage)
	        											+ this.database_dayWeek[i].capacity()*(1-this.dayWeek_Update_Weightage) ) );
	            i++;
	        }
	    }
	    RF.writeDatabase( NodeDatabaseDirectory + "Day-" + this.day_week + ".txt" ,  database_temp );
		
	    i=0;
	    for(int h=0; h<18; h+=1){
	        for(int m=0; m<60; m+=5){
	        	database_temp[i] = new LocalData( this.operationStart + h , m , 
	        									( this.database_today[i].capacity()*(this.dayType_Update_Weightage)
	        											+ this.database_dayType[i].capacity()*(1-this.dayType_Update_Weightage) ) );
	            i++;
	        }
	    }
	    RF.writeDatabase( NodeDatabaseDirectory + "Type-" + this.day_type + ".txt" ,  database_temp );
		
	}
	
}
