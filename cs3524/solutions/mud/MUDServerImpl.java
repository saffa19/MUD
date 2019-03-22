/**
*	Author: 		Duncan van der Wielen
*	Course:			CS3524 Distributed Systems and Security
*	Content:		In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.util.*;
import java.rmi.*;
import java.util.List;
import java.util.Map.Entry;
import cs3524.solutions.mud.MUD;
import cs3524.solutions.mud.MUDServerInterface;


// All the methods that appear in MUDServerInterface need to be implemented in this class.
public class MUDServerImpl implements MUDServerInterface {

	public MUD 					_MUD; 			// an instance variable to reference MUD objects
	public Map<String, MUD> 	servers 	= 	new HashMap<String, MUD>();	// hash map of available MUDs
	public Map<String, String> 	takenThings = 	new HashMap<String, String>(); // hash map of users and the things they've picked up
	public List<String> 		onlineUsers = 	new ArrayList<String>(); // list of users that are currently logged in to a MUD
	public int 					maxUsers 	= 	3; // adjust the maximum allowed number of unique users
	public int 					maxMUDs 	= 	2; // adjust the maximum allowed number of unique MUDs
	

	public MUDServerImpl() throws RemoteException {
	}

	/**
	* The server displays a string as a welcome message
	*/
	public String start() throws RemoteException {
		return "To join a MUD from the list, enter its name.\nTo create a new MUD, enter a name that isn't listed (can't be nothing).\nTo disconnect: ctrl+C";
	}

	/**
	* Creates a new MUD, populated with data from the files in ../3524
	*/
	public boolean createMUD(String name) throws RemoteException {
		if (servers.containsKey(name)) { 	// if there is already a MUD with the given name
			_MUD = servers.get(name); 		// that MUD is the one we are connecting to
			return true;
		} else {
			if (servers.keySet().size() < maxMUDs){		// check if the maximum number of MUDs has been reached already
				servers.put(name, new MUD("mymud.edg","mymud.msg","mymud.thg"));
				System.out.println("Created a MUD called " + name);
				_MUD = servers.get(name);
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	* The set of keys for the servers hash map is a list of all the MUD's names 
	*
	*/	
	public String serverList() throws RemoteException {
		String msg = "\n----------------- Available MUDs -----------------\n";
		for (Entry<String,MUD> entry : servers.entrySet()) {
			String k = entry.getKey();
			msg += "MUD: ";
			msg += k;
			msg += "\n";
		}
		return msg;
	}


	public String usersList() throws RemoteException {
		String msg = "\n----------------- Online Users -----------------\n";
		msg += onlineUsers;
		return msg;
	}


	public String storeUsername(String username) throws RemoteException {
		if (takenThings.keySet().size() < maxUsers){	// if there are less registered users than the max amount
			if (takenThings.containsKey(username)) {	// if the username being stored already exists
				return "alreadyexists";
			} else {
				takenThings.put(username, "'s Inventory: ");	// initialise the inventory for the new user
				return "success";
			}
		} else {
			return "full";
		}
	}

	// next few methods access methods in MUD.java
	public String moveThing(String loc, String dir, String thing) throws RemoteException {
		System.out.println(thing + " moved " + dir + "wards" + " from " + loc);
		return _MUD.moveThing(loc, dir, thing);
	}

	public void delThing(String loc, String thing) throws RemoteException {
		_MUD.delThing(loc, thing);
		System.out.println("Deleted " + thing + " from " + loc);
	};

	public void addThing(String loc, String thing) throws RemoteException {
		_MUD.addThing(loc, thing);
		System.out.println("Added " + thing + " to location " + loc);
	};

	public String locationInfo(String loc) throws RemoteException {
		System.out.println("User requested info about location: " + loc);
		return _MUD.locationInfo(loc);
	};

	public String startLocation() throws RemoteException {
		System.out.println("Set the start location");
		return _MUD.startLocation();
	};

	// Displays the inventory for a given user
	public String inventory(String name) throws RemoteException {
		String s = "\nNo items in your inventory yet.\n";
		if (takenThings.containsKey(name)){
			String val = takenThings.get(name);
			s = "\n" + name + val + "\n";
		}
		return s; 
	}

	// Lets users pick up a thing from a location in the MUD game, adding it to their inventory
	public boolean takeThing( String loc, String thing, String name) throws RemoteException {
		if (!takenThings.containsKey(thing)){		// makes sure user isn't picking up another user (or themself)
			boolean take = _MUD.takeThing(loc, thing, name);
			if (take){
				String allThings = takenThings.get(name) + thing + " ";		// all the things currently in the user's inventory
				takenThings.put(name, allThings);	// add the thing to the user's inventory
			}
			return take;
		} else {
			return false;
		}
	}

	// Adds the user to the list of online users
	public void connectUser(String username) throws RemoteException {
		try {
			onlineUsers.add(username);
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	// Disconnects the user from a MUD
	public void disconnectUser(String loc, String username) throws RemoteException {
		try {
			onlineUsers.remove(username);	// take user offline
			_MUD.delThing(loc, username);	// remove the user from the MUD
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}