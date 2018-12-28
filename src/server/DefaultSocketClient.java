package server;

import java.net.*;
import java.io.*;

public class DefaultSocketClient extends Thread{
	private final boolean DEBUG = true;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private String strHost;
	private int iPort;
	
	public DefaultSocketClient() {}
	
	public DefaultSocketClient(Socket Socket) {
		socket = Socket;
	}
	
	public DefaultSocketClient(String StrHost, int IPort) {
		strHost = StrHost;
		iPort = IPort;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public String getStrHost() {
		return strHost;
	}
	public int getiPort() {
		return iPort;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public void setStrHost(String strHost) {
		this.strHost = strHost;
	}
	public void setiPort(int iPort) {
		this.iPort = iPort;
	}
	
	public void run() {
		if(openConnection()) {
			this.handleSession();
			this.closeSession();
		}
	}
	
	public boolean openConnection() {
		boolean opened = true;
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());			
		} catch(IOException e) {
			if(DEBUG) {
				e.printStackTrace();
				System.err.printf("Unable to obtain stream to/from " + strHost + "\n");
			}
			opened = false;
		}
		
		return opened;
	}
	
	public void handleSession() {
		String strInput = "";
		
		if(DEBUG) {
			System.out.printf("Handling session with " + strHost + ": " + iPort);	
		}
		try {
			while((strInput = (String) reader.readObject()) != null) {
				handleInput(strInput);
			}
		} catch(IOException e) {
			if(DEBUG) {
				System.out.printf("Handling session with " + strHost + ": " + iPort);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object obj) {
		try {
			writer.writeObject(obj);
			writer.flush();
		} catch (IOException e) {
			if(DEBUG) {
				System.out.printf("Error writing to " + strHost + "\n");
			}
			e.printStackTrace();
		}
	}

	public Object getObject() {
		Object receivedObj = null;
		try {
			receivedObj = reader.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return receivedObj;
	}
	
	public void handleInput(String strInput) {
		System.out.printf(" " + strInput + " \n");
	}

	public void closeSession() {
		try {
			reader.close();
			writer.close();
			writer = null;
			reader = null;
			socket.close();
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
}
