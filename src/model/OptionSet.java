package model;
import java.io.*;
import java.util.*;

class OptionSet implements Serializable{
	//Instance variables
	private ArrayList <Option> opt = new ArrayList<Option>();
	private String name;
	private Option choice;
	public boolean DEBUG = false;


	//Default Constructor
	public OptionSet() {}

	//Initialize Option object to add to Option ArrayList
	protected void setOption(int size)
	{
		for(int i = 0; i < size; i++)
		{
			Option option = new Option();
			opt.add(option);
		}
	}


	//Getters
	protected ArrayList<Option> getOptionArray() {
		return opt;
	}

	protected String getOptName(int index) {
		return opt.get(index).getName();
	}
	protected float getOptPrice(int index) {
		return opt.get(index).getPrice();
	}
	protected String getName() {
		return name;
	}
	protected int getOptionSize()
	{
		return opt.size();
	}

	//Setters
	protected void setName(String name) {
		this.name = name;
	}

	protected void setOptName(String name, int index) {
		this.opt.get(index).setName(name);
	}

	protected void setOptPrice(float price, int index) {
		this.opt.get(index).setPrice(price);
	}

	//FIND OPTION 
	protected int findOptName(String name)
	{
		for(int i = 0; i < opt.size(); i++)
		{
			if (opt.get(i).getName().equals(name))
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
	protected int findOptPrice(float price)
	{
		for(int i = 0; i < opt.size(); i++)
		{
			if (opt.get(i).getPrice() == price)
			{
				if (DEBUG)
					System.out.printf("\n***[%.2f] is at index [%s]\n", price, i);
				return i;
			}	
		}
		if (DEBUG)
			System.out.printf("\n***[%.2f] NOT FOUND\n", price);
		return -1;
	}

	//UPDATE
	protected boolean updateOptName(String OptionName, String updateName)
	{
		//search for the name -- if found, update -- not found error message
		int index = findOptName(OptionName);
		if (index == -1)
			return false;
		else
			opt.get(index).setName(updateName);
		return true;
	}

	protected boolean updateOptPrice(float oldprice, float newprice)
	{
		//search for the name -- if found, update -- not found error message
		int index = findOptPrice(oldprice);
		if (index == -1)
			return false;
		else
			opt.get(index).setPrice(newprice);
		return true;
	}

	//DELETE
	protected boolean deleteOpt(String name)
	{
		//give optionSet name -- search -- delete name -- delete Option array
		int index = findOptName(name);
		if (index == -1)
			return false;
		else
		{
			opt.get(index).setName(null);
			opt.get(index).setPrice(-10000.0f);
			return true;
		}
	}

	//Return private Option choice object
	protected Option getOptionChoice()
	{
		if (choice != null)
			return choice;
		else
			return null;
	}

	//Set private Option choice object with the given the Option Name that the user choose
	protected void setOptionChoice(String optionName)
	{
		int index = findOptName(optionName);
		if (index == -1)
		{
			if (DEBUG)
				System.out.printf("\nError --> Option Name [%s] NOT found!\n\n", optionName);
		}
		else
		{
			Iterator<Option> iter = this.opt.iterator();
			while(iter.hasNext()){
				Option op = iter.next();
				String name = op.getName();
				if(name.equals(optionName)){
					choice = op;
				}
			}
		}
	}

	//PRINT
	protected void print(int counter)
	{
		StringBuffer buff = new StringBuffer();
		buff.append("\nOption Set Name #").append(counter+1).append(": ").append(name).append("\n");
		//System.out.printf("|Size %s|", opt.length);
		System.out.print(buff);

		for (int n = 0; n < opt.size(); n++)
		{
			opt.get(n).print(n);
		}
	}

}