package features.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread
{
	/**
	 * An instance of the ServerThreadHandler
	 */
	private ServerThreadHandler st;
	/**
	 *  An instance of a Socket
	 */
	private Socket socket;
	/**
	 * An instance of a PrintWriter
	 */
	private PrintWriter pw;
	
	/**
	 * Constructs a ServerThread from a socekt and its assigned handler
	 * @param socket
	 * 		The socket made in the Chat
	 * @param st
	 * 		The ServerThreadHandler made in Chat
	 */
	public ServerThread(Socket socket, ServerThreadHandler st)
	{
		this.socket = socket;
		this.st = st;
	}
	
	/**
	 * Returns the PrintWriter
	 * @return pw
	 * 		The PrintWriter
	 */
	public PrintWriter getPrintWriter()
	{
		return pw;
	}
	
	/**
	 * Sends the message then removes the thread containing that message from the ServerThreads.
	 */
	@Override
	public void run()
	{
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
			
			while(true)
			{
				st.sendMessage(br.readLine());
			}
		} 
		catch (IOException e) 
		{
			st.getServerThreads().remove(this);
		}
	}
}
