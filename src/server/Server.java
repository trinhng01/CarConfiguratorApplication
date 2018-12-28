package server;

import java.net.*;
import java.io.*;
import java.util.*;
import exception.AutoException;
import model.*;

public class Server {
	private Socket socket;
	private ServerSocket serverSocket;
	private DefaultSocketClient defClientSocket;
	private static LinkedHashMap<String, Automotive> autoLHM;
	
	//Constructor
	public Server() {
		socket = new Socket();
		autoLHM = new LinkedHashMap<String, Automotive>();
		
        try {
            serverSocket = new ServerSocket(4138);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4138.");
            System.exit(1);
        }
	}
	
	//Getter and Setter
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	//Start server for connection
	public void startServer() {
		socket = null;
		try {
			socket = serverSocket.accept();
        	defClientSocket = new DefaultSocketClient(socket);
        	defClientSocket.openConnection();
       	} catch (IOException e) {
       		System.err.println("Accept failed.");
       		System.exit(1);
       	}
	}
	
	//Server Connection with Client
	public void serverInteraction() throws AutoException {
		Automotive auto = new Automotive();
		BuildCarModelOptions buildCarOptions = new BuildCarModelOptions();
		
		while(true) {
			Object receivedObject = defClientSocket.getObject();
			Properties prop = new Properties();
			
			// Upload properties file
			if(receivedObject.getClass().equals(prop.getClass())){
				prop = (Properties) receivedObject;
				
				auto = buildCarOptions.createAuto(prop);
				autoLHM = buildCarOptions.addAuto(autoLHM, auto);
				
				defClientSocket.sendObject("UPLOADED");
				System.out.printf("--> Uploaded Properties file from Client\n");
			} 
			else {
				//Quit program
				if(receivedObject.equals("QUIT")) {
					defClientSocket.closeSession();
					System.out.printf("--> Client quits\n"); break;
				} 
				//Display all models
				else if(receivedObject.equals("DISPLAY")) {
					defClientSocket.sendObject(autoLHM);
					System.out.printf("--> Send data for Client to display!\n");
				} 
				//Quit program
				else if(receivedObject.equals("QUIT")) {
					defClientSocket.closeSession();
					System.out.printf("--> Client quits\n"); break;
				} 
				//Send object for client to configure a car
				else if(receivedObject.equals("CONFIG")) {
					defClientSocket.sendObject("START CONFIG");
					System.out.printf("--> Clients configured a model\n");
					defClientSocket.sendObject(autoLHM);
				} 
				//Send object for client to print
				else {
					System.out.printf("--> Client requested a model\n");
					String model = (String) receivedObject;
				
					if(autoLHM.containsKey(model)) {
						defClientSocket.sendObject(autoLHM.get(model));
					} else {
						defClientSocket.sendObject("FAILDISPLAY");
					}
				}
			}
		}
	}

	//Quit Server
	public void quitServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
}
