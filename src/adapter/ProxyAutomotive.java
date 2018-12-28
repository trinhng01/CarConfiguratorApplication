package adapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Properties;
import exception.AutoException;
import model.*;
import scale.EditOption;
import server.BuildCarModelOptions;
import util.FileIO;

public abstract class ProxyAutomotive {
	private static LinkedHashMap<String, Automotive> a1 = new LinkedHashMap<String, Automotive>();

	//Build LHM of Automotive
	public void BuildAuto(int op, String key, String filename, String filename2) throws AutoException, IOException
	{
		FileIO IO = new FileIO();
		switch(op){
		case 1: a1.put(key, IO.readTextFile(filename)); break;
		case 2: 
			Properties props= new Properties(); //
			FileInputStream in = new FileInputStream(filename2); 
			props.load(in);
			a1.put(key, IO.readPropFile(props)); break;
		}
	}
	
	//Add Automotive object to LinkedHashMap
	public void addAuto(Automotive newAuto, String model){
		a1.put(model, newAuto);
	}

	//Add Automotive object to LinkedHashMap given a properties file
	public void addAuto(Properties props) throws AutoException {
		BuildCarModelOptions buildAuto = new BuildCarModelOptions();
		a1 = buildAuto.addAuto(a1, buildAuto.createAuto(props));
	}

	//Print 1 instance of Automotive from LHM with Object key
	public void print(String key)
	{
		a1.get(key).print();
	}

	//Print 1 instance of Automotive from LHM
	public void printAuto(String modelName, String key)
	{
		if ((a1.get(key).getMake()+a1.get(key).getModel()).equals(modelName))
			a1.get(key).print();
		else 
			System.out.printf("***[%s] NOT FOUND\n", modelName);
	}

	//Update OptionSet Name
	public void updateOptionSetName(String modelName, String OptionSetName, String newName, String key)
	{
		System.out.printf("\n************************************************************\n");
		if ((a1.get(key).getMake()+a1.get(key).getModel()).equals(modelName))
		{
			if (a1.get(key).updateOptionSetName(OptionSetName, newName))
			{
				System.out.printf("\n-->UPDATED OptionSet Name [%s] to %s SUCCESSFULLY!\n", OptionSetName, newName);
				//System.out.printf("\n***HERE IS THE UPDATED MODEL DATABASE\n");
				//a1.print();
			}
			else
				System.out.printf("\n-->UPDATE OptionSet Name [%s] to %s FAILS!\n", OptionSetName, newName);
		}
		else 
			System.out.printf("***[%s] NOT FOUND!", modelName);
	}
	
	//Update Option Price
	public void updateOptionPrice(String modelName, String OptionSetName, float oldprice, float newPrice, String key)
	{
		System.out.printf("\n************************************************************\n");
		if ((a1.get(key).getMake()+a1.get(key).getModel()).equals(modelName))
		{
			if (a1.get(key).updateOptionPrice(OptionSetName, oldprice, newPrice))
			{
				System.out.printf("\n-->UPDATED Option Price of OptionSet [%s] with Name [%s] to price "
						+ "[$%.2f] SUCCESSFULLY!\n", OptionSetName, oldprice, newPrice);
				//System.out.printf("\n***HERE IS THE UPDATED MODEL DATABASE\n");
				//a1.print();
			}
			else
				System.out.printf("\n-->UPDATE Option Price of OptionSet [%s] with Name [%s] to price [$%.2f] FAILS!\n", OptionSetName, oldprice, newPrice);
		}
		else 
			System.out.printf("***[%s] NOT FOUND!", modelName);
	}

	//Fix Exception
	public void fix (int errorno)
	{
		AutoException ex = new AutoException();
		ex.fix(1);
	}

	//Getter that return an instance of Automotive
	public Automotive getAuto(String key)
	{
		return a1.get(key);
	}

	//Use interface EditThread to create thread
	public void editAuto(int ops, String[] input) {
		new EditOption(ops, input);
	}

	public synchronized void editOptionSetName(String modelKey, String opSetName, String updateName){}
	public synchronized void editOptionName(String modelKey, String opSetName, String optionName, String updateName){}
	public synchronized void editOptionPrice(String modelKey, String opSetName, float oldPrice, float updatePrice){}

}
