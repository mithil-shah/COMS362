package features.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThreadThread extends Thread
{
	private ServerThread st;
	private Socket socket;
	private PrintWriter pw;
	
	public ServerThreadThread(Socket socket, ServerThread st)
	{
		this.socket = socket;
		this.st = st;
	}
	
	public PrintWriter getPrintWriter()
	{
		return pw;
	}
	
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
			st.getServerThreadThreads().remove(this);
		}
	}
}
