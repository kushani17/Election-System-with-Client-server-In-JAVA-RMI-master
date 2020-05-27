package try_3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;


public interface election extends Remote {
	
	
	//this a remote interface

		
		public int RegisterVoter(String name) throws RemoteException;
		public String VerifyVote(int voterId) throws RemoteException;
		public boolean vote(int voterId, String voted_can) throws RemoteException;
		public HashMap <String, Integer> resultVote() throws RemoteException;
		public String winner() throws RemoteException;
	

}
