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

	public MUD _MUD;	// an instance variable to reference a MUD object
	public Map<String, MUD> servers = new HashMap<String, MUD>();	// list of available MUDs
	public Map<String, String> takenThings = new HashMap<String, String>();		// list of users and the things they've picked up
	public List<String> onlineUsers = new ArrayList<String>(); 
	public int maxUsers = 3;	// adjust the maximum allowed number of unique users
	public int maxMUDs = 2;		// adjust the maximum allowed number of unique MUDs
	

	public MUDServerImpl() throws RemoteException {
	}


	public String start() throws RemoteException {
		return "To join a MUD from the list, enter its name.\nTo create a new MUD, enter a name that isn't listed (can't be nothing).\nTo disconnect: ctrl+C";
	}


	public boolean createMUD(String name) throws RemoteException {
		if (servers.containsKey(name)) { 	// if given mudName exists already
			_MUD = servers.get(name); 		// _MUD = the value of the corresponding entry in the servers hashmap
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
		msg += onlineUsers;
		return msg;
	}


	public String storeUsername(String username) throws RemoteException {
		if (takenThings.keySet().size() < maxUsers){
			if (takenThings.containsKey(username)) {
				//System.out.println("username already exists");
				return "alreadyexists";
			} else {
				takenThings.put(username, "'s Inventory: ");
				//System.out.println("username stored");
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


	public String inventory(String name) throws RemoteException {
		String s = "\nNo items in your inventory yet.\n";
		if (takenThings.containsKey(name)){
			String val = takenThings.get(name);
			s = "\n" + name + val + "\n";
		}
		return s; 
	}


	public boolean takeThing( String loc, String thing, String name) throws RemoteException {
		if (!takenThings.containsKey(thing)){		// if the user isn't trying to pick up another user...
			boolean take = _MUD.takeThing(loc, thing, name);
			if (take){ //if an item gets taken successfully
				String allThings = takenThings.get(name) + thing + " ";
				takenThings.put(name, allThings);	// get the item(s) already taken by that user
				// add the new thing to the list of items
			}
			return take;
		} else {
			return false;
		}
	}


	public void connectUser(String username) throws RemoteException {
		try {
			onlineUsers.add(username);
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}


	public void disconnectUser(String loc, String username) throws RemoteException {
		try {
			onlineUsers.remove(username);
			_MUD.delThing(loc, username);
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}



}