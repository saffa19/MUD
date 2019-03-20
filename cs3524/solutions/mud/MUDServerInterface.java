/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface provides access to methods in MUDServerImpl.java
public interface MUDServerInterface extends Remote
{
    public void createMUD(String name) throws RemoteException;
    public void serverList() throws RemoteException;
    public String moveThing(loc,dir,thing) throws RemoteException;
}
