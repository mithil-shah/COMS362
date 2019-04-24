package features.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The thread of the peers on the chat
 * 
 * @author Mithil Shah & Bernard Ang
 *
 */
public class PeerThread extends Thread
{
	/**
	 * The BufferedReader responsible for reading streams
	 */
	private BufferedReader br;
	
	/**
	 * Constructs a new peer thread from a socket's input stream
	 * @param socket
	 * 		The socket from where to get the input stream
	 * @throws IOException
	 * 		Thrown if Socket or BufferedReader cannot handle stream
	 */
	public PeerThread(Socket socket) throws IOException
	{
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	/**
	 * The BufferedReader reads user input while the chat is running.
	 */
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
