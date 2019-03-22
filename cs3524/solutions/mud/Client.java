/*
*	Author: 		Duncan van der Wielen
*	Course:			CS3524 Distributed Systems and Security
*	Content:		In-course assessment (MUD game)
*	Adapted from:	rmishout practical material
*/

package cs3524.solutions.mud;

import java.io.*;
import java.util.*;
import java.rmi.Naming;
import java.lang.SecurityManager;
import java.rmi.RemoteException;

public class Client {

	// Main client method is adapted from the rmishout practical class material
	static MUDServerInterface server;
	static BufferedReader in = new BufferedReader(new InputStreamReader( System.in ));	
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
			System.err.println("I/O error.");
			System.err.println(e.getMessage());
		}

		catch (java.rmi.NotBoundException e) {
			System.err.println("Server not bound.");
			System.err.println(e.getMessage());
		}
		
	}

	/*
	* The main menu, this is where the user can choose to generate a new
	* instance of a MUD game (with a name of their choice) or join an
	* existing MUD game from the list of MUDs
	*/
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

		// Switch to handle usernames
		switch(server.storeUsername(username)) {
			
			/*
			* The username already exists in the server's records. The user is
			* given the option to enter a different username or enter 'y' to
			* log in with the username they just gave
			*/
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

			/*
			* The server is at full capacity and won't accept any more unique users.
			* Existing users can still log in with their usernames.
			*/
			case "full" :
				System.out.println("\nMaximum number of unique usernames reached!");
				playGame(mudName);
				break;

			/*
			* The username doesn't already exist and the server hasn't reached the 
			* maximum number of users, so this thread ends and the user can be
			* added to the MUD game (see line 126, line 133)
			*/
			case "success" :
				System.out.println("Welcome on board " + username + "!");
				break;
		}

		// The user's username is added to the list of online users
		server.connectUser(username);
		String spawn = server.startLocation();
		String myLocation = "";
		String info = "";
		String help = "\nCommands:\n1. Moving: north, south, east, west\n2. Location information: info\n3. Pick up an item: take\n4. View your inventory: bag\n5. Other online users: users\n6. Server list: servers\n7. Main menu: quit\n";

		// Add the user's username to the MUD game as a thing
		server.addThing(spawn, username);
		System.out.println("\n\n-------------- Hi " + username + ", you are now in play! --------------\n" + help + "\nYou have started at location " + spawn + "\nMove #1");
		myLocation = spawn;

		// MAIN GAMEPLAY LOOP
		while (inPlay){
			String userInput = in.readLine();
			switch(userInput) {
				case "help" :
					System.out.println(help + "\nWhat now?");
					break;

				/*
				* Movement commands chain to the next case statement body.
				* There is a special condition for the first move, which always explicitly uses 
				* the default start location provided by the MUD. There is a move ticker that
				* keeps track of how many moves the user has made. The core movement
				* functionality is provided by a given method in MUD.java
				*/
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

				/*
				* Take command initiates the process of picking up an item. Afterwards
				* the user is prompted to enter the item they want to pick up. The takeThing() 
				* method makes sure that the thing being picked up isn't another user (or the 
				* user doing the picking up)
				*/
				case "take" :
					System.out.println("\nWhat would you like to take? (you can't pick up players)");
					String thing = in.readLine();
					if (server.takeThing(myLocation, thing, username) == false){
						System.out.println("\nYou can't pick up " + thing + "!\nWhat now?");
					} else {
						System.out.println("\nYou picked up the " + thing + "!\nSee it in your inventory with 'bag'\n\nWhat now?");
					}
					break;

				/*
				* Bag command displays the inventory corresponding to the current username. 
				* The whole inventory is a single string in a String : String hash map and
				* is declared in MUDServerImpl.java
				*/
				case "bag" :
					System.out.println(server.inventory(username) + "What now?");
					break;
				
				/*
				* Info command displays information about the user's current location in 
				* the MUD. The functionality behind the command is provided by a given
				* method in MUD.java
				*/
				case "info" :
					if (move == 1){
						info = server.locationInfo(spawn);
					} else {
						info = server.locationInfo(myLocation);
					}
					System.out.println(info + "What now?");
					break;

				/*
				* Users command displays a list of other online users. The list is 
				* declared in MUDServerImpl.java and is synchronised so that it is always
				* up-to-date across all connected clients
				*/
				case "users" :
					System.out.println(server.usersList() + "\nWhat now?");
					break;

				/*
				* Servers command lists all MUD instances that exist on the server. The
				* list of servers is displayed when a client connects to the server and 
				* when the user quits their current MUD game
				*/
				case "servers" :
					System.out.println(server.serverList() + "\nTo join a different server, go to the main menu with 'quit'\n\nWhat now?");
					break;

				/*
				* Quit command removes the user from a list of online users and from
				* their current location in the MUD game itself. The online users list
				* is synchronised across clients so that it is always up-to-date. The 
				* state of user inventories is preserved in the server implementation 
				* and users iwll be recognised if they try reconnect with the same
				* username
				*/
				case "quit" :
					server.disconnectUser(myLocation, username); // delete user thing from MUD. 
					startGame();
					break;

				/*
				* If the user enters a command that isn't recognised by the game, they 
				* are alerted and have the chance to enter another command (this can happen
				* any number of times)
				*/
				default :
					System.out.println("\nNot a valid command! (type 'help' for a list of commands)\nWhat now?\n");
					break;
			}
		}
		
	}




}