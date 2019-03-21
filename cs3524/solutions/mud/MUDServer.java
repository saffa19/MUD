/*
*	Author: 	Duncan van der Wielen
*	Course:		CS3524 Distributed Systems and Security
*	Content:	In-course assessment (MUD game)
*/

package cs3524.solutions.mud;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.lang.SecurityManager;
import java.net.InetAddress;

public class MUDServer {
	public static void main(String args[]) {

		if (args.length < 2) {
			System.err.println("Usage: $ java ShoutServerMainline <registryport> <serverport>");
			return;
		}

		try {
			String hostname = (InetAddress.getLocalHost()).getCanonicalHostName();
			int registryport = Integer.parseInt(args[0]); 	// specify which port the rmregistry is listening at for binding and lookup requests
			int serverport = Integer.parseInt(args[1]); 	// this is the port for our shout service

			// This is the server's security policy
			System.setProperty("java.security.policy", "mud.policy");
			System.setSecurityManager(new SecurityManager());

			// These are the remote objects that will reside in the server
			MUDServerImpl mudserver = new MUDServerImpl();
			MUDServerInterface mudstub = (MUDServerInterface)UnicastRemoteObject.exportObject(mudserver, serverport);

			String regURL = "rmi://" + hostname + ":" + registryport + "/MUDServer";
			System.out.println("Registering " + regURL + "...");
			Naming.rebind(regURL, mudstub);
			System.out.println("MUD server successfully bound to " + regURL);

			mudserver.createMUD("testMUD");
			// Note the server will not shut down!
		}

		catch (java.net.UnknownHostException e) {
			System.err.println("Cannot get local host name.");
			System.err.println(e.getMessage());
		}

		catch (java.io.IOException e) {
				System.err.println("Failed to register.");
				System.err.println(e.getMessage());
			}
	}
}
