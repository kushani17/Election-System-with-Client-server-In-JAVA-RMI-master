package try_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Client {
	HashMap<String,Integer> count_vote = new HashMap<String,Integer>();
	
	public static void main(String args[]) throws RemoteException
	{
		Client c = new Client();
		c.connectRemote();
	}

	public void connectRemote() throws RemoteException{
		// TODO Auto-generated method stub
		
		try{
			Registry reg = LocateRegistry.getRegistry("localhost",4004);
			election stub = (election )reg.lookup("hi");
			System.out.println("ready");
			
			
			Scanner scan = new Scanner(System.in);
			int qt = 1;
			
			while(qt != 0)
			{
				System.out.println("************************************************");
				System.out.println("Choose any one of the options below");
				System.out.println("1. Register vote");
				System.out.println("2. Verify Voter");
				System.out.println("3. Vote");
				System.out.println("4. Tally Results");
				System.out.println("5. Announce Winner");
				System.out.println("0. Quit");
				
				int c = scan.nextInt();
			switch(c)
			{
			
			case 0:
				qt = 0;
				break;
				//exit(0);
			case 1: 
				//System.out.println("case 1");
				System.out.println("enter name to register vote");
				String voterName = scan.next();
				int voterId = stub.RegisterVoter(voterName);
				if(voterId == 0)
				{
				   System.out.println("You are already registered voter");
				}
				else
				{
					System.out.printf("voter %s register with voterid : %d \n", voterName, voterId);
				}
				break;
				
			case 2: 	
				System.out.println("enter voter Id for verification");
				int id = scan.nextInt();
				String response = stub.VerifyVote (id);

				
				if(response.equals("New"))
				{
					System.out.println("Yor are not registered user. Please press 1 to register for the vote");
				}
				else
				{
					//System.out.println(response);
					System.out.printf("voter with voter Id: %d is registered with name  : %s \n", id, response);
				}
				
				break;
				
			case 3: 
				System.out.println("Enter your voter Id");
				int voterid = scan.nextInt();
				
				String response2 = stub.VerifyVote (voterid);
				if(response2.contains("New"))
				{
					System.out.println("You are not registered voter please press 1 to register");
				}
				
				else if(response2.contains("voted already not allowed"))
				{
					System.out.println("You have already voted not allowed to vote again");
				}
				
				else
				{
				BufferedReader bf = new BufferedReader(new FileReader("candidate"));
				String line = bf.readLine();
				ArrayList<String> cadName = new ArrayList<String>();
				System.out.println("Please enter any name from below candidates name to vote");
				
				while(line != null)
				{
					cadName.add(line);
					line = bf.readLine();
					
				}
				System.out.println("Candidate list");
				
				for(int i=0;i<cadName.size();i++)	
				{
					System.out.printf("%d %s \n",i+1,cadName.get(i));
				}
				
				
				String voted_cand = scan.next();
				boolean answer =stub.vote(voterid, voted_cand);
				if(answer == true)
				{
					System.out.println("You have voted successfully");
				}
				}
				
				break;	
			case 4: 
					
				count_vote = stub.resultVote();
				Set s2 = count_vote.entrySet();
				Iterator it = s2.iterator();
				while(it.hasNext())
				{
					
					Map.Entry get = (Map.Entry)it.next();
					System.out.println("Candidate name :" +get.getKey()+ " won vote:" +get.getValue());
				}
				
				break;
				
			case 5: 
				System.out.printf("Winner is: %s \n", stub.winner());
				break;
			
			}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
