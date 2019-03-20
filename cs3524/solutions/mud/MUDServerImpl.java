/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.util.*;
import java.rmi.*;
import cs3524.solutions.mud.MUD;
import cs3524.solutions.mud.MUDServerInterface; // necessary??


// All the methods that appear in MUDServerInterface need to be implemented in this class.
public class MUDServerImpl implements MUDServerInterface {

	public MUD _MUD;	// an instance variable to reference a MUD
	//public Map<String, MUD> servers = new HashMap<String, MUD>();	// Map<name, instance> a list of the available MUDs, thinking ahead

	public MUDServerImpl() throws RemoteException {
	
	}

	public String start() throws RemoteException {
		return "\nTo create a new MUD server, enter a server name (can't be nothing).";
	}

	public void createMUD(String name) throws RemoteException {
		try{
			_MUD = new MUD("mymud.edg","mymud.msg","mymud.thg");
			System.out.println("Created a MUD called " + name);
		}
		catch (Exception e) {
			System.err.println( e.getMessage() );
		}
	}

	//public void serverList() throws RemoteException {
	//	System.out.println("-------------------------------\nServers: ");
	//	System.out.println(servers); // don't know if this will work or if I'll need to print for each entry...
	//}

	public String moveThing(String loc, String dir, String thing) throws RemoteException {
		System.out.println("Moved " + thing + " from " + loc + ", " + dir + "wards");
		return _MUD.moveThing(loc, dir, thing);
	}

	public void delThing(String loc, String thing) throws RemoteException {
		_MUD.delThing(loc, thing);
		System.out.println("Deleted " + thing + " from " + loc);
	};

	public void addThing(String loc, String thing) throws RemoteException {
		_MUD.addThing(loc, thing);
		System.out.println("Added " + thing + " to " + loc);
	};

	public String locationInfo(String loc) throws RemoteException {
		return _MUD.locationInfo(loc);
	};

	public String startLocation() throws RemoteException {
		return _MUD.startLocation();
	};

	public void connectUser() throws RemoteException {
		// connect the user securely to the MUD instance.
		// probably keep a synchronised record of who's online. --> maybe an onlineUsers() method?
	}

	public void disconnectUser() throws RemoteException {
		// disconnect the user securely to the MUD instance.
	}
}