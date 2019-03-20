/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.rmi.RemoteException;

import cs3524.solutions.mud.MUD;
import cs3524.solutions.mud.MUDServerInterface; // necessary??


// All the methods that appear in MUDServerInterface need to be implemented in this class.

public class MUDServerImpl implements MUDServerInterface {

	public MUD _MUD;	// an instance variable to reference a MUD
	public Map<String, MUD> servers = new HashMap<String, MUD>();	// Map<name, instance> a list of the available MUDs

	public MUDServerImpl() throws RemoteException {
	
	}

	createMUD(String name) throws RemoteException {
		try{
			servers.put(name, new MUD("mymud.edg","mymud.msg","mymud.thg"));
			System.out.println("Created a MUD called " + name);
		}
		catch (Exception e) {
			System.err.println( e.getMessage() );
		}
	}


	void serverList() throws RemoteException {
		System.out.println("-------------------------------\nServers: ");
		System.out.println(servers); // don't know if this will work or if I'll need to print for each entry...
	}

}