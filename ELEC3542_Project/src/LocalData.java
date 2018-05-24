public class LocalData {
	
	private String hr;
	private String mn;
	private String cap;
	
	public LocalData() {
		this.hr = "00";
		this.mn = "00";
		this.cap = "0";
	}
	
	public LocalData(String hour, String minute, String capacity) {
		this.hr = hour;
		this.mn = minute;
		this.cap = capacity;
	}
	
	public LocalData(int hour, int minute, double capacity) {
		this.setHour(hour);
		this.setMins(minute);
		this.setCapacity(capacity);
	}
	
	public LocalData(LocalData copy) {
		this.setHour(copy.hour());
		this.setMins(copy.mins());
		this.setCapacity(copy.capacity());
	}
	
	public int hour() {
		return Integer.parseInt(this.hr);
	}
	
	public int mins() {
		return Integer.parseInt(this.mn);
	}
	
	public double capacity() {
		return Double.valueOf(this.cap);
	}
	
	public void setHour(int input){
		if(input < 10) {
			this.hr = "0" + Integer.toString(input);
		}
		else {
			this.hr = Integer.toString(input);
		}
	}
	
	public void setMins(int input){
		if(input < 10) {
			this.mn = "0" + Integer.toString(input);
		}
		else {
			this.mn = Integer.toString(input);
		}
	}
	
	public void setCapacity(double input){
		this.cap = Double.toString(input);
	}
	
	public String hour_String() {
		return this.hr;
	}
	
	public String mins_String() {
		return this.mn;
	}
	
	public String capacity_String() {
		return this.cap;
	}
	
	public String toString(){
		return ("[" + this.hr + ":" + this.mn + "] - " + this.cap + "");
	}
	
}
