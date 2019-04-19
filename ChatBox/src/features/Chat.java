package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import configuration.Response;
import features.Feature;
import features.Messenger.*;

public class Chat implements Feature
{
	private String username;
	 
	public Chat(String query) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is your name?");
		
		try 
		{
			username = br.readLine().trim();
			
			if(username.equals(""))
			{
				username = "Unknown User";
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Please enter a port number.");
		
		try 
		{
			int port = Integer.valueOf(br.readLine().trim());
			ServerThread st = new ServerThread(port);
			st.start();
			selectUsers(br);
			message(br, username, st);
		}
		catch (IOException | NumberFormatException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void selectUsers(BufferedReader br) throws IOException
	{
		System.out.println("Enter the port numbers of the users you would like to talk to seperated by a space.");
		String[] ports = br.readLine().split(" ");
		
		for(String port : ports)
		{
			try
			{
				Socket socket = new Socket("localhost", Integer.valueOf(port.trim()));
				new PeerThread(socket).start();
			}
			catch(NumberFormatException nfe)
			{
				System.out.println("Invalid port number.");
				System.exit(0);
			}
		}
	}
	
	public void message(BufferedReader br, String username, ServerThread st) throws IOException
	{
		System.out.println("You are now in the chat.");
		
		while(true)
		{
			String message = br.readLine();
			
			if(message.equals(null))
			{
				break;
			}
			if(message.trim().equals("exit chat"))
			{
				break;
			}
			else if(!message.trim().equals(""))
			{
				st.sendMessage(username + ": " + message);
			}
		}
	}
	
	@Override
	public Response setResponse() 
	{
		return new Response("You have left the chat.");
	}

	@Override
	public void parseQuery(String query)
	{
		
	}
}
