package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class DefaultSocketClient extends Thread{
	private final boolean DEBUG = true;
	//Use ObjectStream
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private Socket socket;
	private String strHost;
	private int iPort;
	
	//Constructor
	public DefaultSocketClient() {}
	
	public DefaultSocketClient(Socket Socket) {
		socket = Socket;
	}
	public DefaultSocketClient(String StrHost, int IPort) {
		strHost = StrHost;
		iPort = IPort;
	}
	
	//Setter and Getter 
	public Socket getSocket() {
		return socket;
	}
	public String getStrHost() {
		return strHost;
	}
	public int getiPort() {
		return iPort;
	}
	public void setReader(ObjectInputStream reader) {
		this.reader = reader;
	}
	public void setWriter(ObjectOutputStream writer) {
		this.writer = writer;
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
			socket = new Socket(strHost, iPort);
		} catch (IOException socketError) {
			if(DEBUG) 
				System.err.printf("Unable to connect to " + strHost + "\n");
			opened = false;
		}
		
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
		if(DEBUG) 
			System.out.printf("Handling session with " + strHost + ": " + iPort);
                      
                try {
			while((strInput = (String) reader.readObject()) != null)
				handleInput(strInput);
		} catch(IOException e) {
			if(DEBUG)
				System.out.printf("Handling session with " + strHost + ": " + iPort);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object obj) { // Write object
		try {
			writer.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object getObject() {
		Object object = null;
		try {
			object = reader.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public void handleInput(String strInput) {
		System.out.printf(" " + strInput + " \n");
	}
	
	public void closeSession() {
		try {
			writer.close();
			reader.close();
			socket.close();
			writer = null;
			reader = null;
			
		} catch (IOException e) {
			e.getMessage();
			if (DEBUG) 
				System.err.println("Error closing socket to " + strHost);

		}
	}	
}
