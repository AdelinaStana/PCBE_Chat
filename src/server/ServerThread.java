package server;
import java.io.*;
import java.net.*;

import ui.ServerUI;

public class ServerThread extends Thread
{
	private Server server;
	private Socket socket;
	private QueueDB queueDb;
	private TopicsDB topicsDb;
	private ServerUI srvUI;
	
	public ServerThread( Server server, ServerUI srvUI, Socket socket, QueueDB queueDB, TopicsDB topicsDB) {
	this.server = server;
	this.socket = socket;
	this.queueDb = queueDB;
	this.topicsDb = topicsDB;
	this.srvUI = srvUI;
	start();
	}
	
	public void run() {
		try {
	  BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
      String s;
      Protocol protocol = new Protocol() ;
      String name = null ;

      while(true)
      {
      	try {
  			if ((s = in.readLine() )!= null) 
  			{
  			  if(s.contains("My name is :"))
  				{
  					name = s.split(":")[1];
  					server.register(name,socket.getPort());
  				}
  				else
  				{ 
  				  Message msg = protocol.processInput(s);
	  			  if (msg.getType() == "Queue")
	  			  {
	  				  queueDb.push((QueueMessage) msg);
	  				  // async push and send (breaks the thread if needed)
	  				  QueueMessage msgToSend = queueDb.pull();
	  				  
	  				  server.sendToSomeone(name + " : " + msgToSend.getContent(), msgToSend.getReciver());
	  				  srvUI.appendToChatBox( "QueueDB size: "+ queueDb.getSize() );
	  			  }
	  			  else if(msg.getType() == "Topic")
	  			  {
	  				  // atomic push to queue and send (fire and forget, queue cleanup will take place)
	  				  TopicMessage msgToSend = topicsDb.pushpull((TopicMessage) msg);
	  				  // server.sendToAll(name + " : " + msg.getContent());
	  				  
	  			  }
  			  }
  			  
  			}
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
   

      }

		} catch( EOFException ie ) {

		} catch( IOException ie ) {
			ie.printStackTrace();
		} finally {

				server.removeConnection( socket );
		}
	}
}

