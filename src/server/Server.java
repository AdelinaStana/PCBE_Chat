package server;

import java.net.*;
import java.io.*;

public class Server 
{
	private static QueueDB queueList = new QueueDB();
	private static TopicsDB topicsList = new TopicsDB();
	private static ServerSocket serverSocket = null;
	static Socket clientSocket = null;
	 
	public Server()
	{
        try 
        {
           serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            System.exit(1);
        }
   
        try
        {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

	}

   public static void main() throws IOException 
   {
            Message msg ;

	        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                clientSocket.getInputStream()));
	        
	        String inputLine;
	        
	        Protocol p = new Protocol();
	        out.println("Hello!");
	        while ((inputLine = in.readLine()) != null) 
	        {
	             msg = p.processInput(inputLine);

	             if(msg.getType()=="Queue")
	             {
	            	
	            	 queueList.push(msg.getContent());
	            	 out.println(queueList.pop());
	             }
	             else
	             {
	            	 topicsList.setTopics(msg.getContent());
	            	 out.println(topicsList.getContent());
	             }
	             
	             
	        }
	        out.close();
	        in.close();
	        clientSocket.close();
	        serverSocket.close();
   }
}
