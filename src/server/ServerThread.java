package server;
import java.io.*;
import java.net.*;

import ui.ServerUI;

public class ServerThread extends Thread
{
	private Server server;
	private Socket socket;
	private ServerUI srvUI;
	
	public ServerThread( Server server, Socket socket , ServerUI srvUI) {
	this.server = server;
	this.socket = socket;
	this.srvUI  = srvUI;
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
	  				   server.sendToSomeone(name + " : " + msg.getContent(), msg.getReciver());
	  			       //server.sendToAll(name + " : " + msg.getContent());
	  			  }
	  			  else
	  			  {
	  				  
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

