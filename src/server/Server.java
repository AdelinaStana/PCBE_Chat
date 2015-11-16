package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import ui.ServerUI;

public class Server
{

	private ServerSocket ss;
	private Hashtable<Socket, PrintWriter> outputStreams = new Hashtable<Socket, PrintWriter>();
	ServerUI srvUI ;
	QueueDB queueDb;
	private Hashtable<String, Integer> clients = new Hashtable<String, Integer>();
	
	public Server( int port ) throws IOException {
		    srvUI = new ServerUI("localhost",port);
		    queueDb = new QueueDB();
		    srvUI.start();
			listen( port );
		}
	
   private void listen( int port ) throws IOException {
	   
	   InetAddress addr = InetAddress.getByName("localhost");
	   ss = new ServerSocket(1234,20,addr);
	   srvUI.appendToChatBox( "Listening on "+ss );

	   while (true) {
		   Socket s = ss.accept();
		   srvUI.appendToChatBox( "Connection from "+s );

		   PrintWriter out = new PrintWriter(s.getOutputStream());

		   outputStreams.put( s, out );

		   new ServerThread( this, srvUI, s, queueDb);
	   }
   }	

   	
     Enumeration<PrintWriter> getOutputStreams() {
   			return outputStreams.elements();
   			}
   	

   		void sendToAll( String message ) {
   			synchronized( outputStreams ) {
            
   			for (Enumeration<PrintWriter> e = getOutputStreams(); e.hasMoreElements(); ) {
   				PrintWriter out = (PrintWriter)e.nextElement();
   				out.println( message );
	   			out.flush();
   				}
   			 }
	         }
   		
   		void sendToSomeone(String message , String reciver)
   		{
   			int dest = getReciverPort(reciver);
   			
   			synchronized( outputStreams ) {
   				Set<Entry<String, Integer>> set = clients.entrySet();
   			    Iterator<Entry<String, Integer>> it = set.iterator();
   			    
   			    Set<Entry<Socket, PrintWriter>> set1 = outputStreams.entrySet();
			    Iterator<Entry<Socket, PrintWriter>> it1 = set1.iterator();
   			    
   			    while (it.hasNext()) {
   			      Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
	   			   while (it1.hasNext()) {
	    			      Map.Entry<Socket, PrintWriter> entry1 = (Map.Entry<Socket, PrintWriter>) it1.next();
	    			      int cltPort = entry1.getKey().getPort();
	    			      if(cltPort == dest)
	    			      {   PrintWriter out = entry1.getValue();
	    			    	   out.println( message );
	    			   		   out.flush();
	    			      }
	    			    }
   			     }
   			    }
   		}
   		
	private int getReciverPort(String reciver) 
	    { 
		Set<Entry<String, Integer>> set = clients.entrySet();
	    Iterator<Entry<String, Integer>> it = set.iterator();
	    
	    while (it.hasNext()) {
	      Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
	      if(entry.getKey().equals(reciver))
	      {
	    	  return (int) entry.getValue();
	      }
	    }
	    return -1 ;
		}

	void removeConnection( Socket s ) 
	{
	synchronized( outputStreams )
	{
		srvUI.appendToChatBox( "Removing connection to "+s );
		outputStreams.remove( s );
		
		try {
			s.close();
		} catch( IOException ie ) {
		srvUI.appendToChatBox( "Error closing "+s );
		ie.printStackTrace();
		}
		}
	}

	public void register(String name, int portC)
	{
		if(!clients.containsKey(name))
		{
		clients.put(name, portC);
		}
		else
		{ 
			clients.put(name+"1",portC);
			sendToSomeone("EXIT",name+"1");
			int recvPort = portC;
			    
		    Set<Entry<Socket, PrintWriter>> set = outputStreams.entrySet();
		    Iterator<Entry<Socket, PrintWriter>> it = set.iterator();
			    
			    while (it.hasNext()) {
			    	Map.Entry<Socket, PrintWriter> entry = (Map.Entry<Socket, PrintWriter>) it.next();
			    	if(entry.getKey().getPort() == recvPort)
			    	{
			    		System.out.println(entry.getKey());
			    		removeConnection(entry.getKey());
			    	}
			    		
			    }	    
	    }
			
    }
	
}