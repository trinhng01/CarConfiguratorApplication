package client;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CarModelOptionsIO {
	//Read data from Properties file
	public Properties readData(String fileName) {
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(fileName);
			prop.load(in); //This loads the entire file in memory.
			in.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
