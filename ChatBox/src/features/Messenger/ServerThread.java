package features.Messenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread
{
	private ServerSocket serverSocket;
	private Set<ServerThreadThread> serverThreadThreads = new HashSet<ServerThreadThread>();
	
	public ServerThread(int port) throws IOException
	{
		serverSocket = new ServerSocket(port);
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try 
			{
				ServerThreadThread stt = new ServerThreadThread(serverSocket.accept(), this);
				serverThreadThreads.add(stt);
				stt.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public void sendMessage(String message)
	{
		for(ServerThreadThread stt : serverThreadThreads)
		{
			stt.getPrintWriter().println(message);
		}
	}
	
	public Set<ServerThreadThread> getServerThreadThreads()
	{
		return serverThreadThreads;
	}
}
