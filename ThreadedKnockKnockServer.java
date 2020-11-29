import java.net.*;
import java.io.*;

/*
 * Server class that supports multiple clients
 * through multithreading
 * 
 * @author Timothy Mbaka
 * @author Dinah Onyino
 * 
 * on my honor, as a Carnegie-Mellon Africa student, I have neither
 * given nor received unauthorized assistance on this work.
 * 
 */

public class ThreadedKnockKnockServer {
	static boolean KeepRunning = true;
	public static void main(String[] args) throws IOException {

		Socket          clientSocket;
		ClientHandler   clientHandler;
		ServerSocket    socket = null;
		int             acceptTimeout = -1;

	    if (args.length == 2)
	    {
	        acceptTimeout = Integer.parseInt(args[1]);
	    }
	    else if (args.length != 1)
	    {
	        System.err.println("Usage: java ThreadedEchoServer <port number> [timoutMilliseconds]");
	        System.exit(1);
	    }

		int portNumber = Integer.parseInt(args[0]);
		
	    if ((socket = openSocket(portNumber, acceptTimeout)) == null)
	        System.exit(2);

		while(KeepRunning) {
			clientSocket = acceptConnection(socket);
			if (clientSocket != null)
			{
				clientHandler = new ClientHandler(clientSocket);
				clientHandler.start();
				System.out.println("Server now handling " + clientHandler.getName());
				
			}
		}

		System.out.println("The Server is ending.");

	}   	    	 
	
	private static ServerSocket openSocket(int portNumber, int acceptTimeout)
	{
		ServerSocket socket = null;
		try
		{
			socket = new ServerSocket(portNumber);
			if (acceptTimeout > 0)
				socket.setSoTimeout(acceptTimeout);
		}
	    catch (BindException e)
	    {
	        System.err.println("Server cannot bind to port " + portNumber);
	        System.err.println(e);
	        System.exit(3);
	    }
	    catch (Exception e)
	    {
	        System.err.println("Server caught exception when trying to create socket ");
	        System.err.println(e);
	    }

	    return socket;
	}
	
	private static Socket acceptConnection(ServerSocket socket) {			
		
	    try {
	    	
	    	Socket clientSocket = socket.accept();
	        return clientSocket;		    	        
	    }
		catch (SocketTimeoutException e)
		{
			//System.out.println("Server timed out waiting for a connection.");
		}
		catch (IOException e)
		{
			System.err.println("Server caught exception when trying to listen on port " + socket.getLocalPort() );
			System.err.println(e);
		}

		return null;
	}
}
