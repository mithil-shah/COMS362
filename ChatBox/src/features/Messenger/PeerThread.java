package features.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PeerThread extends Thread
{
	private BufferedReader br;
	
	public PeerThread(Socket socket) throws IOException
	{
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				System.out.println(br.readLine());
			}
			catch(IOException e)
			{
				interrupt();
				break;
			}
		}
	}
}
