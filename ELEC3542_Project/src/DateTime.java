import java.time.LocalTime;
import java.time.LocalDate;

public class DateTime {
	
	private LocalDate today;
	private LocalTime time;
	
	public DateTime(){
		this.today = null;
		this.time = null;
	}
	
	public void main(String[] args) {
		
		//Current Date
		this.today = LocalDate.now();
		System.out.println("Current Date="+today);
		
		//Current Time
		this.time = LocalTime.now();
		System.out.println("Current Time="+time);
		
	}
	
	public int getSecond(){
		return LocalTime.now().getSecond();
	}
	
	public int getMinute(){
		return LocalTime.now().getMinute();
	}
	
	public int getHour(){
		return LocalTime.now().getHour();
	}
	
	public int getDay(){
		return LocalDate.now().getDayOfMonth();
	}
	
	public int getMonth(){
		return LocalDate.now().getMonthValue();
	}

	public int getYear(){
		return LocalDate.now().getYear();
	}
	
	public int getDayOfWeek(){
		return LocalDate.now().getDayOfWeek().getValue();
	}
	
	public String getDayOfWeek_String(){
		int dayNum = LocalDate.now().getDayOfWeek().getValue();
		if(1==dayNum){
			return "Monday";
		}
		if(2==dayNum){
			return "Tuesday";
		}
		if(3==dayNum){
			return "Wednesday";
		}
		if(4==dayNum){
			return "Thursday";
		}
		if(5==dayNum){
			return "Friday";
		}
		if(6==dayNum){
			return "Satuday";
		}
		else{
			return "Sunday";
		}
	}

}



	