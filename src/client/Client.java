package client;

import model.*;
import java.io.File;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Client{
	private SelectCarOption carOpt;
	private DefaultSocketClient clientSocket;
	private Scanner scanner;

	//Constructor 
	public Client() {
		carOpt = new SelectCarOption();
		scanner = new Scanner(System.in);
		try {
			String hostIP = Inet4Address.getLocalHost().getHostAddress();
			//System.out.println(hostIP);
			clientSocket = new DefaultSocketClient(hostIP, 4138);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		clientSocket.openConnection();
	}

	//Main interaction of client
	public void clientInteraction() {
		boolean quit = false;
		int counter = 1;
		String option = "";
		do {
			displayMenu();
			//Check if String is numeric for switch statement
			do{
				System.out.printf("--> Please enter your option (1-5): ");
				option = scanner.nextLine();
			}while (!isNumeric(option.trim()));
			int numopt = Integer.parseInt(option.trim());
			switch(numopt) {
			case 1:
				this.uploadPropFile(); //Upload properties file	
				quit = false;
				break;
			case 2:
				this.displayModel(); //Display 1 model
				quit = false;
				break;
			case 3:
				this.displayDatabase(); //Display all models
				quit = false;
				break;
			case 4:
				this.configureCar(); //Client configures a car
				quit = false;
				break;				
			case 5:
				this.displayMenu(); //Display menu
				quit = false;
				break;		
			case 6:
				this.quit(); //Quit program
				quit = true;
				break;				
			default:
				System.out.printf("Invalid option! Please reenter your option (1-5).\n");
				break;
			}
			counter++;
		} while(true && !quit);			
	}
	
	//Check if user input is a number
	public boolean isNumeric(String str)  
	{  
		try {  
			int num = Integer.parseInt(str);  
		}  
		catch(NumberFormatException e) {  
			return false;  
		}  
		return true;  
	}
	
	//Upload Properties file to database
	public void uploadPropFile() {
		CarModelOptionsIO modelOptionsIO = new CarModelOptionsIO();

		System.out.printf("Enter the Properties filename/filepath: ");
		String propFileName = scanner.nextLine();
		File propFile = new File(propFileName);

		if(!propFile.exists()) //Check if the file does not exist, then print a message
			System.out.printf("[ERROR] --> Properties file does NOT exit!\n");
		else {
			Properties prop = modelOptionsIO.readData(propFileName);
			if(prop == null) //Check if the file is empty
				System.out.printf("[ERROR] --> Properties file is EMPTY!\n");
			else 
			{
				clientSocket.sendObject(prop);
				if(clientSocket.getObject().equals("UPLOADED"))
					System.out.printf("[CONGRATS] --> Properties file uploaded successfully!\n");
				else 
					System.out.printf("[ERROR] --> FAIL to upload your properties file!\n");
			}
		}
	}

	//Client configure a car
	public void configureCar() {
		clientSocket.sendObject("CONFIG");

		if(clientSocket.getObject().equals("START CONFIG")) {
			LinkedHashMap<String, Automotive> a1 = (LinkedHashMap<String, Automotive>) clientSocket.getObject();
			carOpt.configureCar(scanner, a1);
		} else 
			System.out.printf("[ERROR] --> NO connection with server!\n");
	}

	//Display 1 model
	public void displayModel() {
		carOpt.displayModel(scanner, clientSocket);
	}

	//Display all models
	public void displayDatabase() {		
		carOpt.printallModels(clientSocket);
	}

	//Quit program
	public void quit() {
		clientSocket.sendObject("QUIT");
		clientSocket.closeSession();
	}

	//Display Menu for client to choose
	public void displayMenu() {
		System.out.printf("\n================= MENU OF SERVICES =================\n"
				+ "  1 - Upload properties file\n"
				+ "  2 - Display a specific model\n"
				+ "  3 - Display all available model(s)\n"
				+ "  4 - Configure a car\n"
				+ "  5 - Display menu\n"
				+ "  6 - Quit\n");
	}

}
