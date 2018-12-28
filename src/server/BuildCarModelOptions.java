package server;
import util.*;
import model.*;
import adapter.*;
import java.util.*;
import exception.AutoException;

public class BuildCarModelOptions implements AutoServer{
	
	//Accept properties object from client socket over an ObjectStream and create an Automobile
	public Automotive createAuto(Properties props) throws AutoException {
		Automotive newAuto = new Automotive();
		newAuto = new FileIO().readPropFile(props);
		return newAuto;
	}
	
	//Add newly created Automobile to the LinkedHashMap
	public LinkedHashMap<String, Automotive> addAuto(LinkedHashMap<String, Automotive> autoLHM, Automotive newAuto) {
		String newKey = newAuto.getMake() + " " + newAuto.getModel();
		autoLHM.put(newKey, newAuto);
		return autoLHM;
	}
	
	//Add newly created Automobile to the LinkedHashMap
	@Override
	public void addAuto(Properties props) {}
}
