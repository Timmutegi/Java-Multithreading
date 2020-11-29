import java.net.*;
import java.io.*;
/*
 * Client Handler class that supports multiple clients
 * through multithreading
 * 
 * @author Timothy Mbaka
 * @author Dinah Onyino
 * 
 * on my honor, as a Carnegie-Mellon Africa student, I have neither
 * given nor received unauthorized assistance on this work.
 * 
 */
public class ClientHandler extends Thread{
	static int clientNumber = 0;
		 
	BufferedReader request;
	PrintWriter response;

	public ClientHandler(Socket clientSocket) throws IOException
	{
	    request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    response = new PrintWriter(clientSocket.getOutputStream(), true);                 
	    clientNumber++;
	    setName("Client " + clientNumber);
	}

	public void run()
	{
		String inputLine, outputLine;
	    try
	    {	    	
	        KnockKnockProtocol kkp = new KnockKnockProtocol();
	        outputLine = kkp.processInput(null);
	        response.println(outputLine);

	        while ((inputLine = request.readLine()) != null)
	        {            
	            outputLine = kkp.processInput(inputLine);
	            response.println(outputLine);
	            if (outputLine.equals("Bye."))
	                break;
	        }
	        
	    }
	    catch (IOException e)
	    {
	        System.err.println("Exception caught when trying to read requests");
	        System.err.println(e.getMessage());
	    }
	}

}
