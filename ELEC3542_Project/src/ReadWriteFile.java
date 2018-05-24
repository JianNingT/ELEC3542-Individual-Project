import java.lang.String;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class ReadWriteFile {
	
	public ReadWriteFile(){}
	
	public String readFile(String fileName){		
		String input="";
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				input = input.concat(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
		return input;
	}
	
	public void writeFile(String fileName, String input){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
			bw.write(input);
		}catch(IOException e){
		}finally{
			try {
				bw.close();
				fw.close();
			}catch(IOException ex){
			}
		}
	}
	
//	public void readInput(String fileName, LocalData[] data){
	public LocalData[] readDatabase(String fileName){
		String input="";
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				input = input.concat(sCurrentLine);
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		String list[] = input.split(",");
		LocalData[] data = new LocalData[12*18];
		for(int i=0; i<(12*18); i++) {

			data[i]= new LocalData( list[(i*3)],
									list[(i*3)+1],
									list[(i*3)+2] );

		}

		return data;
	}
	
	public void writeDatabase(String fileName, LocalData[] database){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			bw = new BufferedWriter(fw);
		    
			for(LocalData d : database) {
				bw.write( d.hour_String() + "," + d.mins_String() + "," + d.capacity_String() + ",\n");
			}
			
		}catch(IOException e){
		}finally{
			try {
				bw.close();
				fw.close();
			}catch(IOException ex){
			}
		}
	}
	
}