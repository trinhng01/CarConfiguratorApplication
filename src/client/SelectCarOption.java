package client;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

import model.Automotive;

public class SelectCarOption {
	
	//Display list of all OptionSets
	public void opsetMenu(Automotive auto)
	{
		System.out.println("\nHere are the OptionSet:");
		for (int i = 0; i < auto.getOpsetSize(); i++)
		{
			System.out.printf("\t%s - %s\n", i+1, auto.getOpsetName(i));
		}
	}
	
	//Display list of all Options for one OptionSet
	public void optionMenu(Automotive auto, String opsetName)
	{
		int index = auto.findOptionSetName(opsetName);
		int size = auto.getOptionSize(opsetName);
		if (size == -1)
		{
			System.out.println("Invalid OptionSet Name!");
		}
		else{
			System.out.printf("  Here are %s option(s) available: \n", size);
			for (int i = 0; i < size; i++)
			{
				System.out.printf("\t%s - %s ~ $%.2f\n", i+1, auto.getOptionName(index, i), auto.getOptionPrice(index, i));
			}
		}
		
	}
	
	//Display one Automotive model
		public void displayModel(Scanner scanner, DefaultSocketClient clientSocket) {
			System.out.printf("Enter the model name: ");
			String modelName = scanner.nextLine();

			clientSocket.sendObject(modelName);
			Object obj = clientSocket.getObject();

			if(obj.equals("FAILDISPLAY")) 
				System.out.printf("[ERROR] --> Model does NOT exist!\n");
			else {
				Automotive auto = (Automotive) obj;
				auto.print();
			}
		}
		
	//Display all Automotive models
	public void printallModels(DefaultSocketClient clientSocket) {
		clientSocket.sendObject("DISPLAY");
		LinkedHashMap<String, Automotive> autoLHM = (LinkedHashMap<String, Automotive>) clientSocket.getObject();
		Set<String> set = autoLHM.keySet();
		Iterator<String> it = set.iterator();
		System.out.printf("\n=================== WELCOME TO KBB ===================\n\n"
				+ "Displaying %s available model(s):\n", autoLHM.size());
		while(it.hasNext()) 
			autoLHM.get(it.next()).print();
	}
	//Client configures a car
	public void configureCar(Scanner scanner, LinkedHashMap<String, Automotive> a1) {
		String model = "", optname = "";

		System.out.printf("\n============== MODEL CONFIGURATION ==============\n");

		System.out.printf("Enter the Model name: ");
		model = scanner.nextLine();

		if(a1.containsKey(model)) {
			System.out.printf("Base Price: $%.2f\n", a1.get(model).getBaseprice());
			opsetMenu(a1.get(model));

			for (int i = 0; i < a1.get(model).getOpsetSize(); i++)
			{
				System.out.printf("\n* For Option Set %s - %s\n", i+1, a1.get(model).getOpsetName(i));
				optionMenu(a1.get(model), a1.get(model).getOpsetName(i));
				System.out.printf("\nEnter the " + a1.get(model).getOpsetName(i) + " that you want to choose --> ");
				optname = scanner.nextLine();
				a1.get(model).setOptionChoice(a1.get(model).getOpsetName(i), optname.trim());
				a1.get(model).printChoice(a1.get(model).getOpsetName(i));
			}
			a1.get(model).printChoices(model);
			System.out.printf("\n\t*** TOTAL PRICE: $%.2f ***\n", (float) a1.get(model).getTotalPrice());
		} else 
			System.out.printf("[ERROR] --> Model does NOT exit\n");

	}
}
