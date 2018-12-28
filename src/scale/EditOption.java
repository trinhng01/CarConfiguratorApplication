package scale;
import adapter.*;
import model.*;

public class EditOption extends ProxyAutomotive implements Runnable, EditThread {
	private int ops;
	private Thread thread;
	private String [] input;
	private boolean DEBUG = true;

	//Constructors
	public EditOption() {}

	public EditOption(int ops, String [] input)
	{
		this.ops = ops;
		this.input = input;
		thread = new Thread(this);
		start();
	}
	
	//Run Thread
	public void run() {
		 synchronized(System.out){
			switch (ops)
			{
			case 1:
				System.out.printf("\n+++++++++ %s Operation-%s\n", thread.getName(), ops);
				this.editOptionSetName(input[0],input[1], input[2]);
				stop();
				break;
			case 2:
				System.out.printf("\n+++++++++ %s Operation-%s\n", thread.getName(), ops);
				this.editOptionName(input[0],input[1], input[2], input[3]);
				stop();
				break;
			case 3:
				System.out.printf("\n+++++++++ %s Operation-%s\n", thread.getName(), ops);
				this.editOptionPrice(input[0],input[1], Float.valueOf(input[2]),  Float.valueOf(input[3]));
				stop();
				break;
			default:
				System.out.printf("\n+++++++++ %s Invalid operation\n", thread.getName());
				stop();
				break;
			}
		}
		//this.getAuto(input[0]).print();
	}

	//Start Thread function
	public void start() {
		try {
			System.out.printf("\n+++++++++ %s starts\n", thread.getName());
			Thread.sleep(1000);
			thread.start();
		} catch(InterruptedException e) {
			e.getMessage();
		}	
	}

	//Stop Thread function
	public void stop() 	{
		System.out.printf("\n+++++++++ %s stops\n", thread.getName());
		thread = null;
	}

	//synchronized method to edit OptionSet Name
	public synchronized void editOptionSetName(String modelKey, String opSetName, String updateName)
	{
		this.getAuto(modelKey).updateOptionSetName(opSetName, updateName);
		try {
			Thread.sleep(2000);
			if (DEBUG)
				this.getAuto(modelKey).print();
		} catch(InterruptedException e) {
			e.getMessage();
		}
		notify();
	}

	//synchronized method to edit Option Name
	public synchronized void editOptionName(String modelKey, String opSetName, String optionName, String updateName)
	{
		this.getAuto(modelKey).updateOptionName(opSetName, optionName, updateName);
		try {
			Thread.sleep(2000);
			if (DEBUG)
				this.getAuto(modelKey).print();
		} catch(InterruptedException e) {
			e.getMessage();
		}
		notify();
	}

	//synchronized method to edit Option Price
	public synchronized void editOptionPrice(String modelKey, String opSetName, float oldPrice, float updatePrice)
	{
		this.getAuto(modelKey).updateOptionPrice(opSetName, oldPrice, updatePrice);
		try {
			Thread.sleep(2000);
			if (DEBUG)
				this.getAuto(modelKey).print();
		} catch(InterruptedException e) {
			e.getMessage();
		}
		notify();
	}
}
