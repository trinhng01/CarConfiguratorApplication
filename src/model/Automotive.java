package model;
import java.io.*;
import java.util.*;

public class Automotive implements Serializable{
	//Instance variables
	private String year;
	private String make;
	private String model;
	private float baseprice;
	private ArrayList<OptionSet> opset = new ArrayList<OptionSet>();
	private ArrayList<Option> choice = new ArrayList<Option>();
	public boolean DEBUG = false;


	//Default Constructors
	public Automotive() {}

	//Overloaded Constructors
	public Automotive(String ma, String mo, float p, int size)
	{
		opset = new ArrayList<OptionSet>();
		initializeOptionSet(size);
		choice = new ArrayList<Option>();
		baseprice = p;
		model = mo; make = ma;
	}

	//Initialize OptionSet object to add to OptionSet ArrayList
	public void initializeOptionSet(int size)
	{
		for(int i = 0; i < size; i++)
		{
			OptionSet optionSet = new OptionSet();
			opset.add(optionSet);
		}
	}


	//Initialize Option object to add to Option ArrayList
	public void initializeOption(int index, int sizeOption)
	{
		opset.get(index).setOption(sizeOption);
	}

	//GETTERS
	public String getYear() {
		return year;
	}

	//Get make of auto
	public String getMake()
	{
		return make;
	}

	//Get model of auto
	public String getModel()
	{
		return model;
	}

	//Get the base price of the model
	public float getBaseprice() {
		return baseprice;
	}

	//Get one OptionSet object in the OptionSet array
	public OptionSet getOpset(int index) {
		return this.opset.get(index);
	}

	//Get OptionSet name
	public String getOpsetName(int index) {
		return this.opset.get(index).getName();
	}
	//Get Option Name
	public String getOptionName(int opsetIndex, int optionIndex){
		return this.opset.get(opsetIndex).getOptName(optionIndex);
	}
	//Get Option Price
	public float getOptionPrice(int opsetIndex, int optionIndex){
		return this.opset.get(opsetIndex).getOptPrice(optionIndex);
	}

	//Get OptionSet ArrayList Size
	public int getOpsetSize()
	{
		return opset.size();
	}

	//Get Option ArrayList Size
	public int getOptionSize(String opsetName)
	{
		int index = findOptionSetName(opsetName);
		if (index == -1)
			return index;
		return opset.get(index).getOptionSize();
	}

	//Setters
	//Set Name
	public void setYear(String year) {
		this.year = year;
	}

	//Set Base Price
	public void setBaseprice(float baseprice) {
		this.baseprice = baseprice;
	}

	//Set Make
	public void setMake (String make)
	{
		this.make = make;
	}

	//Set Model
	public void setModel (String model)
	{
		this.model = model;
	}

	//Set OptionSet name
	public void setOpsetName(String n, int index) {
		this.opset.get(index).setName(n);
	}

	//Set Option Name
	public void setOptionName(String n, int opsetIndex, int optionIndex){
		this.opset.get(opsetIndex).setOptName(n, optionIndex);
	}
	//Set Option Price
	public void setOptionPrice(float p, int opsetIndex, int optionIndex){
		this.opset.get(opsetIndex).setOptPrice(p, optionIndex);
	}

	//FIND (read)
	//Find OptionSet with name
	public int findOptionSetName(String name)
	{
		for(int i = 0; i < opset.size(); i++)
		{
			if (opset.get(i).getName().equals(name))
			{
				if (DEBUG)
					System.out.printf("\n***[%s] is at index [%s]\n", name, i);
				return i;
			}
		}
		if (DEBUG)
			System.out.printf("\n***[%s] NOT FOUND\n", name);
		return -1;
	}
	//Find Option with name (in context of OptionSet)
	public int findOptionName(String OptionSetName, String OptionName)
	{
		int index = findOptionSetName(OptionSetName);
		if (index == -1)
			return index;
		else
			return opset.get(index).findOptName(OptionName);
	}

	//UPDATE (Search and Set)
	//Update values of OptionSet
	public boolean updateOptionSetName(String OptionSetName, String updateName)
	{
		//search for the name -- if found, update -- not found error message
		int index = findOptionSetName(OptionSetName);
		if (index == -1)
		{
			return false;
		}
		else
			opset.get(index).setName(updateName);
		return true;
	}
	//Update values of Options
	public boolean updateOptionName(String OptionSetName, String OptionName, String updateName)
	{
		int index = findOptionSetName(OptionSetName);
		if (index == -1)
			return false;
		else
			return opset.get(index).updateOptName(OptionName, updateName);
	}

	public boolean updateOptionPrice(String OptionSetName, float oldprice, float newprice)
	{
		int index = findOptionSetName(OptionSetName);
		if (index == -1)
			return false;
		else
			return opset.get(index).updateOptPrice(oldprice, newprice);
	}

	//DELETE
	//Delete an OptionSet (1 at a time) 
	public boolean deleteOptionSet(String name)
	{
		//give optionSet name -- search -- delete name -- delete Option array
		int index = findOptionSetName(name);
		if (index == -1)
			return false;
		else{
			opset.get(index).setName(null);
			for (int i = 0; i < opset.get(index).getOptionArray().size(); i++)
			{
				opset.get(index).setOptName(null, i);
				opset.get(index).setOptPrice(-10000.0f, i);
			}
		}
		//For insert function in the future, save the index of the deleted value and then insert right into that position
		return true;
	}
	//Delete an Option (1 at a time) 
	public boolean deleteOption(String OptionSetName, String OptionName)
	{
		//give option name -- search -- delete name -- delete price
		int index = findOptionSetName(OptionSetName);
		if (index == -1)
			return false;
		else
			return opset.get(index).deleteOpt(OptionName);
	}

	//Return chosen Option Name given the optionSet name
	public String getOptionChoice(String setName)
	{
		int index = findOptionSetName(setName);
		if (opset.get(index).getOptionChoice() == null)
			return null;
		else 
			return opset.get(index).getOptionChoice().getName();
	}

	//Return chosen Option price given the optionSet name
	public int getOptionChoicePrice(String setName)
	{
		int index = findOptionSetName(setName);
		if (opset.get(index).getOptionChoice() == null)
			return -1000;
		else
			return (int) opset.get(index).getOptionChoice().getPrice();
	}

	//User select OptionSet and Option
	public void setOptionChoice(String opSetChoice, String optionChoice)
	{
		int index = findOptionSetName(opSetChoice);
		if (index == -1)
		{
			System.out.println("Option NOT found!");
		}
		else 
		{
			opset.get(index).setOptionChoice(optionChoice);
			choice.add(opset.get(index).getOptionChoice());
		}
	}

	//Compute and return the total price for chosen option
	public float getTotalPrice()
	{
		float total = baseprice;
		for (int i = 0; i < choice.size(); i++)
		{
			if (choice.get(i) != null)
				total += choice.get(i).getPrice();
		}
		return total;
	}

	//PRINT
	//Sort before printing
	//if null (after delete) not printing 
	public void print(){
		StringBuffer buff = new StringBuffer("\n*************** " + make + " " + model + " Model ***************\n\n");
		//buff.append("Ford Focus Wagon ZTW model\n\n");
		//buff.append("Auto Year: ").append(year);
		buff.append("Auto Make: ").append(make);
		buff.append("\nAuto Model: ").append(model);
		buff.append("\nBase Price: $").append(baseprice).append("\n");
		System.out.print(buff);
		for (int i = 0; i < opset.size(); i++)
		{
			opset.get(i).print(i);
		}
	}

	//Print chosen Option name and price
	public void printChoice(String OptionSetName)
	{
		if (getOptionChoice(OptionSetName) != null)
		{
			System.out.printf("\n~ For %s, you choose: %s.", OptionSetName, getOptionChoice(OptionSetName));
			System.out.printf("\n+ Price: $%.2f\n", (float) getOptionChoicePrice(OptionSetName));
		}
		else
			System.out.printf("\n~ For %s, NO option selected.\n", OptionSetName);
	}

	// Print choices
	public void printChoices(String model) {
		System.out.printf("\n*** CONGRATS! You have successfully configured a car for model [%s]\n", model);
		System.out.printf("\n******* Here is the report of your new car *******\n");
		System.out.printf("\nModel: %s\n",(make + " " + model));
		System.out.printf("Base Price: $%.2f\n", baseprice);
		for (int i = 0; i < opset.size(); i++)
		{
			printChoice(opset.get(i).getName());
		}
	}

}

