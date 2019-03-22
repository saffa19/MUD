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
		System.out.println("\n_______________\n...MAIN MENU...\n_______________");
		System.out.println(server.serverList());
		System.out.println(server.start());
		String mudName = in.readLine();

		if (mudName == null) {
			System.out.println("Error: mudName can't be null! Enter a valid mudName.");
			startGame();
		} else {
			if (server.createMUD(mudName) == true){
				System.out.println("\nYou are playing on MUD: " + mudName + "\nWho's playing?");
				playGame(mudName);
			} else {
				System.out.println("Maximum number of MUDs has been reached, please join an existing MUD instead, returning to main menu...");
				startGame();
			}
		}
	}


	static void playGame(String mudName) throws Exception {
		boolean inPlay = true;
		int move = 1;
		String username = in.readLine();

		switch(server.storeUsername(username)) {
			case "alreadyexists" :
				System.out.println("\nThat username is taken!\nWant to log in with that username? (y/n)");
				String bad2FA = in.readLine();
				if (bad2FA.equals("y")) {
					System.out.println("Welcome back " + username + "!");
				} else {
					System.out.println("Who's playing?");
					playGame(mudName);
				}
				break;

			case "full" :
				System.out.println("\nMaximum number of unique usernames reached!");
				playGame(mudName);
				break;

			case "success" :
				System.out.println("Welcome on board " + username + "!");
				break;
		}

		String spawn = server.startLocation();
		String myLocation = "";
		String info = "";
		String help = "\nCommands:\n1. Moving: north, south, east, west\n2. Location information: info\n3. Pick up an item: take\n4. View your inventory: bag\n5. Server list: servers\n6. Main menu: quit\n";

		server.addThing(spawn, username);
		System.out.println("\n\n-------------- Hi " + username + ", you are now in play! --------------\n" + help + "\nYou have started at location " + spawn + "\nMove #1");
		myLocation = spawn;

		while (inPlay){
			String userInput = in.readLine();
			switch(userInput) {
				case "help" :
					System.out.println(help);
					System.out.println("What now?");
					break;

				case "north" :
				case "south" :
				case "east" :
				case "west" :
					if (move == 1){
						myLocation = server.moveThing(spawn, userInput, username);
					} else {
						myLocation = server.moveThing(myLocation, userInput, username);
					}
					System.out.println("You move to location " + myLocation);
					move += 1;
					System.out.println("\nMove #" + move);
					break;

				case "take" :
					// return a list of the items in the current location
					// ask player which item they want
					System.out.println("\nWhat would you like to pick up?");
					String thing = in.readLine();
					if (server.takeThing(myLocation, thing, username) == false){
						System.out.println("\nYou didn't pick up the " + thing + "! (make sure your spelling is correct)");
					} else {
						System.out.println("\nYou picked up the " + thing + "!");
					} // delete the item from the current location and add it to player inventory
					System.out.println("What now?");
					break;

				case "bag" :
					System.out.println(server.inventory(username));
					System.out.println("What now?");
					break;
				
				case "info" :
					if (move == 1){
						info = server.locationInfo(spawn);
					} else {
						info = server.locationInfo(myLocation);
					}
					System.out.println(info);
					System.out.println("What now?");
					break;

				case "users" :
					System.out.println(server.usersList());
					System.out.println("What now?");
					break;

				case "servers" :
					System.out.println(server.serverList() + "\nTo join a different server, go to the main menu with 'quit'\n");
					System.out.println("What now?");
					break;

				case "quit" :
					server.disconnectUser(myLocation, username); // delete user thing from MUD. 
					startGame();
					break;

				default :
					System.out.println("\nNot a valid command! (type 'help' for a list of commands)\n");
					break;
			}
		}
		
	}




}