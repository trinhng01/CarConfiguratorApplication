
/*
Trinh Nguyen
CIS 35B – 63Y
Assignment #5
06/10/2017
06/15/2017 
*/

package server_driver;
import adapter.BuildAuto;
import exception.AutoException;
import server.Server;

public class ServerDriver extends BuildAuto{

	public static void main(String[] args) throws AutoException {
		// TODO Auto-generated method stub
		Server newServer = new Server();
        newServer.startServer();
        newServer.serverInteraction();
        newServer.quitServer();
	}
}



