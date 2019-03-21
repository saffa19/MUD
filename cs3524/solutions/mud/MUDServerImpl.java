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
import cs3524.solutions.mud.MUDServerInterface; // necessary??


// All the methods that appear in MUDServerInterface need to be implemented in this class.
public class MUDServerImpl implements MUDServerInterface {

	public MUD _MUD;	// an instance variable to reference a MUD
	public Map<String, MUD> servers = new HashMap<String, MUD>();	// Map<name, instance> a list of the available MUDs, thinking ahead

	public MUDServerImpl() throws RemoteException {
	
	}

	public String start() throws RemoteException {
		return "To join a MUD from the list, enter its name.\nTo create a new MUD, enter a name that isn't listed (can't be nothing).";
	}

	public void createMUD(String name) throws RemoteException {
		try{
			if (servers.containsKey(name)) { //if entered mud name exists already
				_MUD = servers.get(name); //_MUD = the value of the corresponding entry in the servers hashmape
			} else {
				servers.put(name, new MUD("mymud.edg","mymud.msg","mymud.thg"));
				System.out.println("Created a MUD called " + name);
				_MUD = servers.get(name);
			}
		}
		catch (Exception e) {
			System.err.println( e.getMessage() );
		}
	}

	public String serverList() throws RemoteException {
		String msg = "\n------------------------ Available MUDs ------------------------\n";
		for (Entry<String,MUD> entry : servers.entrySet()) {
    		String k = entry.getKey();    
    		msg += "MUD: ";
    		msg += k;
    		msg += "\n";
		}
		return msg;
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

	public boolean takeThing( String loc, String thing, String name) throws RemoteException {
		return _MUD.takeThing(loc, thing, name);
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