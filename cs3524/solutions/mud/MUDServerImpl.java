/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
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

	public MUD _MUD;	// an instance variable to reference a MUD
	public Map<String, MUD> servers = new HashMap<String, MUD>();	// Map<name, instance> a list of the available MUDs, thinking ahead
	public Map<String, String> takenThings = new HashMap<String, String>();
	public int maxUsers = 3;	// adjust the maximum allowed number of unique users
	public int maxMUDs = 2;		// adjust the maximum allowed number of unique MUDs
	

	public MUDServerImpl() throws RemoteException {
	}


	public String start() throws RemoteException {
		return "To join a MUD from the list, enter its name.\nTo create a new MUD, enter a name that isn't listed (can't be nothing).\nTo disconnect: ctrl+C";
	}


	public boolean createMUD(String name) throws RemoteException {

		if (servers.containsKey(name)) { //if entered mud name exists already
			_MUD = servers.get(name); //_MUD = the value of the corresponding entry in the servers hashmap
			return true;
		} else {
			if (servers.keySet().size() < maxMUDs){
				servers.put(name, new MUD("mymud.edg","mymud.msg","mymud.thg"));
				System.out.println("Created a MUD called " + name);
				_MUD = servers.get(name);
				return true;
			} else {
				return false;
			}
		}
	}


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
		for (Entry<String,String> entry : takenThings.entrySet()) {
			String k = entry.getKey();
			msg += k;
			msg += "\n";
		}
		return msg;
	}


	public String storeUsername(String username) throws RemoteException {
		if (takenThings.keySet().size() < maxUsers){
			if (takenThings.containsKey(username)) {
				System.out.println("username already exists");
				return "taken";
			} else {
				takenThings.put(username, "");
				System.out.println("username stored");
				return "success";
			}
		} else {
			return "full";
		}
	}


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
		System.out.println("Added " + thing + " to " + loc);
	};


	public String locationInfo(String loc) throws RemoteException {
		return _MUD.locationInfo(loc);
	};


	public String startLocation() throws RemoteException {
		return _MUD.startLocation();
	};


	public String inventory(String name) throws RemoteException {
		String s = "\nNo items in your inventory yet.\n";
		if (takenThings.containsKey(name)){
			String val = takenThings.get(name);
			s = "\nYou (" + name + ") have: " + val + "\n";
		}
		return s; 
	}


	public boolean takeThing( String loc, String thing, String name) throws RemoteException {
		boolean take = _MUD.takeThing(loc, thing, name);

		if (take){ //if an item gets taken successfully
			if (takenThings.containsKey(name)){ // if the user has already taken an item
				String allThings = takenThings.get(name) + ", " + thing;
				takenThings.put(name, allThings);	// get the item(s) already taken by that user
				// add the new thing to the list of items
			} else {
				takenThings.put(name, thing);
			}
		}
		return take;
	}


	// these will be used for the exception handling in a4-a1 and also the restrictions on number of users etc.
	public void connectUser() throws RemoteException {
		// connect the user securely to the MUD instance.
		// probably keep a synchronised record of who's online. --> maybe an onlineUsers() method?
	}


	public void disconnectUser() throws RemoteException {
		// disconnect the user securely to the MUD instance.
	}
}