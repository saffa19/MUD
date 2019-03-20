/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.lang.SecurityManager;
import java.rmi.RemoteException;

public class Client {

	static MUDServerInterface server;	// a variable in the Client class scope, so that we can reference the same server across methods declared here
	static BufferedReader in = new BufferedReader(new InputStreamReader( System.in )); // from rmishout: gets input from console.

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Please enter your <host> and <port>");
			return;
		}

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);

		// Specify the security policy and set the security manager.
		System.setProperty("java.security.policy", "mud.policy");
		System.setSecurityManager(new SecurityManager());

		try {
			// Obtain the server handle from the RMI registry
			// listening at hostname:port.
			String regURL = "rmi://" + hostname + ":" + port + "/MUDServer";
			System.out.println("Looking up " + regURL);
			server = (MUDServerInterface)Naming.lookup(regURL);
			System.out.println("Connection is up and running"); // Now we can access methods on the MUDServer through its interface, e.g. server.method()

			startGame(); // Starts the game

			}

		catch (java.io.IOException e) {
			System.err.println( "I/O error." );
			System.err.println( e.getMessage() );
		}

		catch (java.rmi.NotBoundException e) {
			System.err.println( "Server not bound." );
			System.err.println( e.getMessage() );
		}
		
	}

	static void startGame() throws Exception {
		// server.serverList();	// prints a list of the available MUDs to choose from
		System.out.println(server.start());
		String name = in.readLine();

		if (name == null) {
			System.out.println("Please enter a valid name (can't be null)!");
			startGame();
		} else {
			server.createMUD(name);
			playGame(name);
		}
	}


	static void playGame(String name) throws Exception {
		System.out.println("You are playing on: " + name);
		//System.out.println(server.toString());
	}




}