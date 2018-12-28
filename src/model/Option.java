package model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Option implements Serializable{
	//Instance variables
	private String name;
	private float price;

	//Default Constructor
	public Option() {}

	//Getters
	protected String getName() {
		return name;
	}
	protected float getPrice() {
		return price;
	}

	//Setters
	protected void setName(String name) {
		this.name = name;
	}
	protected void setPrice(float price) {
		this.price = price;
	}

	//Print value of each option
	protected void print(int counter)
	{
		StringBuffer buff = new StringBuffer();
		buff.append("    Option Name #").append(counter+1).append(": ").append(name);
		buff.append("\n    Price: $").append(new DecimalFormat("0.00").format(price)).append("\n");
		System.out.print(buff);
	}
}