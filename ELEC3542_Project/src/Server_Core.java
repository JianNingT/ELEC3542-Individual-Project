
public class Server_Core {
	
	public static void main(String[] args) {
		
		try {
			Server_Instance Main;
			if(args.length==0){
				Main = new Server_Instance();
			}
			else{
				Main = new Server_Instance(Integer.valueOf(args[0]));
			}
			Main.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
