package features;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import configuration.Response;
import features.Feature;
import features.Messenger.*;
/**
 * Chat with a person over IP
 * 
 * @author Mithil Shah & Bernard Ang
 *
 */
public class Chat implements Feature
{
	/**
	 * The name of the user
	 */
	private String username;
	 
	/**
	 * Constructs a new Chat 
	 * 
	 * @param query
	 * 		The query given by the user
	 * @throws IOException
	 * 		IOException is thrown if the user does not enter correct parameters for a name and port number.
	 */
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
			ServerThreadHandler st = new ServerThreadHandler(port);
			st.start();
			selectUsers(br);
			message(br, username, st);
		}
		catch (IOException | NumberFormatException e)
		{
			e.printStackTrace();
		}
		
	}
	/**
	 * Input the IP Address of the users you want to talk to.
	 * 
	 * @param br
	 * 		BufferedReader that takes care of user input
	 * @throws IOException
	 * 		Thrown if Sockets cannot be made using the IP Addresses and ports provided
	 */
	public void selectUsers(BufferedReader br) throws IOException
	{
		System.out.println("Enter the 'IP Address-port' of the users you would like to talk to. Each user should be seperated by a space.");
		String[] users = br.readLine().split(" ");
		
		for(String user : users)
		{
			try
			{
				String [] userData = user.split("-");
				Socket socket = new Socket(userData[0].trim(), Integer.valueOf(userData[1].trim()));
				new PeerThread(socket).start();
			}
			catch(NumberFormatException nfe)
			{
				System.out.println("Invalid port number.");
				System.exit(0);
			}
		}
	}
	
	/**
	 * Parses the message sent by the user and retrieved by the user.
	 * 
	 * @param br
	 * 		The BufferedReader takes care of parsing input/output
	 * @param username
	 * 		The name of the user
	 * @param st
	 * 		The thread that sends the message to the other users.
	 * @throws IOException
	 * 		Thrown if the message cannot be parsed and sent
	 */
	public void message(BufferedReader br, String username, ServerThreadHandler st) throws IOException
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
	
	/**
	 * The response given when the user leaves the chat
	 */
	@Override
	public Response setResponse() 
	{
		return new Response("You have left the chat.");
	}

	/**
	 * The query is parsed here.
	 * @param query
	 * 		The query sent by the user
	 */
	@Override
	public void parseQuery(String query)
	{
		
	}
}
