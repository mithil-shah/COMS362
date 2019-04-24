package features.Messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Mithil Shah & Bernard Ang
 *
 */
public class ServerThreadHandler extends Thread
{
	/**
	 * The Socket to run the server
	 */
	private ServerSocket serverSocket;
	/**
	 * A hash set of server threads
	 */
	private Set<ServerThread> serverThreads = new HashSet<ServerThread>();
	
	/**
	 * Constructs a new Server Thread from a port number
	 * 
	 * @param port
	 * 		The port number that the user specifies.
	 * @throws IOException
	 * 		Thrown if a ServerSocket at the port cannot be made.
	 */
	public ServerThreadHandler(int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
	}
	
	/**
	 * Start running the server threads. 
	 */
	@Override
	public void run()
	{
		while(true)
		{
			try 
			{
				ServerThread stt = new ServerThread(serverSocket.accept(), this);
				serverThreads.add(stt);
				stt.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * Sends messages through the server threads. 
	 * 
	 * @param message
	 * 		The message to be sent
	 */
	public void sendMessage(String message)
	{
		for(ServerThread stt : serverThreads)
		{
			stt.getPrintWriter().println(message);
		}
	}
	
	/**
	 * 
	 * @return serverThreads
	 * 		The set of server threads that the server is running.
	 */
	public Set<ServerThread> getServerThreads()
	{
		return serverThreads;
	}
}
