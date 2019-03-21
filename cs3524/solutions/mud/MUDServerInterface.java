/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// Interface provides access to methods in MUDServerImpl.java
public interface MUDServerInterface extends Remote {
	public String start() throws RemoteException; 					            			// misc
	public void createMUD(String name) throws RemoteException; 					            // D3 - D1
	public String moveThing(String loc, String dir, String thing) throws RemoteException;   // D3 - D1
	public void delThing( String loc, String thing ) throws RemoteException;         		// C3 - C1
	public void addThing( String loc, String thing ) throws RemoteException;				// C3 - C1
	public String locationInfo(String loc) throws RemoteException; 				            // C3 - C1
	public String startLocation() throws RemoteException; 									// C3 - C1
	public boolean takeThing( String loc, String thing) throws RemoteException;
	public String serverList() throws RemoteException; 										// B3 - B1
	// Limit no of users logged into MUDs + no of MUDs. New MUDs at runtime.				//   A5
	public void connectUser() throws RemoteException; 										// A4 - A1
	public void disconnectUser() throws RemoteException; 									// A4 - A1
}
