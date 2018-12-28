package adapter;

import java.io.IOException;
import java.util.Properties;
import exception.AutoException;
import model.Automotive;

public interface CreateAuto {
	public void BuildAuto(int op, String key, String filename, String filename2) throws AutoException, IOException;
	public void printAuto(String Modelname, String key);
	public Automotive getAuto(String key);
}
