package try_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;



public class Server extends UnicastRemoteObject implements election{
	static ArrayList<String> cadName = new ArrayList<String>();
	static ArrayList<Integer> l2 = new ArrayList<Integer>(1000);
	HashMap<Integer, String> hmap = new HashMap<Integer, String>();
	HashMap<String,Integer> count_vote = new HashMap<String,Integer>();
	HashMap<Integer, String> voterList = new HashMap<Integer, String>();
	static int count = 1;
	public Server() throws RemoteException
	{
		super();
	}
	
	
	@Override 
	public int RegisterVoter(String name) throws RemoteException{
		//return 0;
		if((voterList.containsValue(name)))
		{
			return 0;
		}
		
		int key = l2.get(count);
		voterList.put(key, name);
		count++;
		return key;
		
		
		
		
	}
	@Override
	public String VerifyVote(int voterId) throws RemoteException{
		
		if(!(voterList.containsKey(voterId)))
		{
			return "New";
		}
		
		 if(!(hmap.containsKey(voterId)))
		 {
			return (voterList.get(voterId) + " " + "and verified voter, allow to vote");
		 }
		 
		 
			return (voterList.get(voterId) + " " + "and voter voted already not allowed to vote again");
		 
		 
		 
		
		
	}
	
	@Override
	public boolean vote(int voterId, String voted_cand) throws RemoteException{
		
		hmap.put(voterId,voted_cand);
		return true;
		
		
	}
	
	@Override
	public HashMap <String, Integer> resultVote() throws RemoteException{
		
		int l=0;
		while(l != 10)
		{
		String canName = cadName.get(l);
		int c = 0;
		for(String s : hmap.values()){
			if(canName.equals(s))
			{
				c++;
			}
		}
		
		
		count_vote.put(canName,c);
		l++;
		}
		return count_vote;
	}
	
	@Override
	public String winner() throws RemoteException{
		
		
		ArrayList<Integer> st = new ArrayList<>();
		
		
		for(Integer s : count_vote.values()){
			st.add(s);
			
		}
		
		Collections.sort(st);
		int top = st.get(st.size()-1);
		String result = "";
		for(Object obj: count_vote.keySet())
		{
			if(count_vote.get(obj).equals(top)){
				result= (String) obj;
			}
			
		}
		
		return result;
		
	}
	

	
	public static void main(String args[]) throws RemoteException{
		
		
		try {
			
			
			Scanner scan = new Scanner(System.in);
			System.out.println("enter file name");
			String f = scan.nextLine();
			System.out.println(f);
			int c =1;
			
			BufferedReader bf = new BufferedReader(new FileReader(f));
			String line = bf.readLine();
			while(line != null)
			{
				//System.out.println(line);
				cadName.add(line);
				line = bf.readLine();
				
			}
			
			System.out.println("Candidate list");
			
			for(int i=0;i<cadName.size();i++)	
			{
				System.out.printf("%d %s \n",i+1,cadName.get(i));
			}
		
		 while (c != 1000)
		 {
		  Random r = new Random();
		  int num = 123456 + r.nextInt(1000) + 60 * 40;
		  
		  if(num % 2 != 0)
		  {
			  int temp = num/10 ;
			  num = num + temp;
			  
		  }
		  
		  else
		  {
			  int temp = num/10 ;
			  num = num - temp;
		  }
		  
		  if(!(l2.contains(num)))
		  {
			  l2.add(num);
			  c++;
		  }
		  
		 }
		 
		 for(int a : l2)
		 {
			 System.out.println(a);
		 }
		  
		 Registry reg = LocateRegistry.createRegistry(4004);
			reg.rebind("hi", new Server());
			System.out.println("server is ready");
			
		 
			
		}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
	
}}

