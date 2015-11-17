package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore;

import ui.ServerUI;

public class Server
{

	private ServerSocket ss;
	private LinkedList<ClientStreams> clientsStreams = new LinkedList<ClientStreams>();
	ServerUI srvUI ;
	QueueDB queueDb;
	TopicsDB topicsDb;
	private static boolean isServer = false;
	Semaphore available = new Semaphore(1, true);
	
	public Server( int port ) throws IOException, InterruptedException {
			srvUI = new ServerUI("localhost",port);
		    queueDb = new QueueDB();
		    topicsDb = new TopicsDB();
		    srvUI.start();
		    
		    Thread topicDbCleaner = new Thread(){
		    	@Override
		    	public void run(){
		    		while(srvUI.getConnectionStatus()){
		    			topicsDb.cleanOld();
			    		srvUI.appendToChatBox("Cleaned TopicDB. Size:" + topicsDb.getSize());
			    		try{
			    			this.sleep(3000);
			    		}
			    		catch(InterruptedException e){}
		    		}

		    		   exitToAll();
		    	}
		    };
		    topicDbCleaner.start();
		    
		    try{
			listen( port );
		    }
		    catch(BindException e){
		    	srvUI.appendToChatBox("Sorry, port " + port + " is in use.");
		    	throw e;
		    }
	}
	
/*	public synchronized static Server getServer(int port) throws IllegalStateException, IOException{
			if(isServer == null){
			isServer = new Server(port);
			return isServer;}
			else
			throw new IllegalStateException("Already instantiated");
			
	}*/
	
   private void listen( int port ) throws IOException, InterruptedException {
	   
	   InetAddress addr = InetAddress.getByName("localhost");
	   ss = new ServerSocket(1234,20,addr);
	   srvUI.appendToChatBox( "Listening on "+ss );
	
	   
	   while (true) {
		available.acquire();
		   Socket s = ss.accept();
		   srvUI.appendToChatBox( "Connection from "+s );

		   PrintWriter out = new PrintWriter(s.getOutputStream());

		   ClientStreams cs = new ClientStreams();
		   cs.setSocket(s);
		   cs.setPrintWriter(out);
		   clientsStreams.add(cs);
		   cs=null;

		   new ServerThread( this, srvUI, s, queueDb, topicsDb);
		   
		available.release();  
	   }
   }	

   void exitToAll() {
			synchronized( clientsStreams ) {
	        	System.out.println("jbjhsfeb");
			ListIterator<ClientStreams> listIterator = clientsStreams.listIterator();
        while (listIterator.hasNext()) {
        	ClientStreams cs = listIterator.next();
        	
        	PrintWriter out = cs.getPrintWriter();
        	out.println("EXIT_ALL");
        	out.flush();
        	}
            	
			}
   }	

   
   void sendToAll( String message , String topic) {
			synchronized( clientsStreams ) {
       
			ListIterator<ClientStreams> listIterator = clientsStreams.listIterator();
        while (listIterator.hasNext()) {
        	ClientStreams cs = listIterator.next();
        	if((cs.getTopics()).contains(topic)){
        	PrintWriter out = cs.getPrintWriter();
        	out.println(message);
        	out.flush();}
            }	
			}
     }
		
		void sendToSomeone(String message , String reciver)
		{
			synchronized( clientsStreams ) {
	            //System.out.println(reciver);
	   			ListIterator<ClientStreams> listIterator = clientsStreams.listIterator();
	 	        while (listIterator.hasNext()) {
	 	        	ClientStreams cs = listIterator.next();
	 	            //System.out.println(cs.getName());
	 	        	if((cs.getName()).equals(reciver))
	 	        	{
	 	        	PrintWriter out = cs.getPrintWriter();
	 	        	out.println(message);
	 	        	out.flush();
	 	        	}
	 	            }	
	   			}
		}
		
void removeConnection( Socket s ) 
{
synchronized( clientsStreams )
{
	srvUI.appendToChatBox( "Removing connection to "+s );
	
	ListIterator<ClientStreams> listIterator = clientsStreams.listIterator();
	int i = 0;
       while (listIterator.hasNext()) {
       	ClientStreams cs = listIterator.next();
       	if(cs.getSocket() == s)
       	{
       		clientsStreams.remove(i);
       	}
       	i++;
           }	
	
	try {
		s.close();
	} catch( IOException ie ) {
	srvUI.appendToChatBox( "Error closing "+s );
	ie.printStackTrace();
	}
	}
}

public LinkedList<String> splitTopics(String topics_)
{
	LinkedList<String> topics = new LinkedList<String>();
	String[] arr = topics_.split("#");
	
	for(int i=0;i<arr.length;i++)
	{
		topics.add(arr[i]);
	}
	return topics;
	
}

public void register(String name, int portC,String topics_)
{
	boolean flag = false;
	LinkedList<String> topics = splitTopics(topics_);
	
	synchronized( clientsStreams )
	{
	
	ListIterator<ClientStreams> listIterator = clientsStreams.listIterator();
   while (listIterator.hasNext()) {
   	ClientStreams cs = listIterator.next();
   	if( (cs.getName()!=null) && ( (cs.getName()).equals(name)))
   	{
          flag=true;
   	}
       }	
	
	if(!flag)
	{
		ListIterator<ClientStreams> listIterator1 = clientsStreams.listIterator();
       int i = 0;
		while (listIterator1.hasNext()) {
       	ClientStreams cs = listIterator1.next();
		    Socket s = cs.getSocket();
       	if(s.getPort() == portC)
       	{
              ClientStreams cs_ = new ClientStreams();
              cs_.setName(name);
              cs_.setPort(portC);
              cs_.setPrintWriter(cs.getPrintWriter());
              cs_.setSocket(cs.getSocket());
              cs_.setTopics(topics);
              clientsStreams.remove(i);
              clientsStreams.add(cs_);
       	}
       	i++;
           }			
	}
	else
	{ 
		name = name+"1";
		int i=0;
		listIterator = clientsStreams.listIterator();
       while (listIterator.hasNext()) {
       	ClientStreams cs = listIterator.next();
       	if((cs.getSocket()).getPort() == portC)
       	{
              ClientStreams cs_ = new ClientStreams();
              cs_.setName(name);
              cs_.setPort(portC);
              cs_.setPrintWriter(cs.getPrintWriter());
              cs_.setTopics(topics);
              clientsStreams.remove(i);
              clientsStreams.add(cs_);
              
         	}
       	i++;
         }
       
		sendToSomeone("EXIT",name);
		i=0;
		
		listIterator = clientsStreams.listIterator();
       while (listIterator.hasNext()) {
       	ClientStreams cs = listIterator.next();
       	if((cs.getName()).equals(name))
       	{
       		clientsStreams.remove(i);
              
         	}
       	i++;
         }
	        
   }
		
}
}

}