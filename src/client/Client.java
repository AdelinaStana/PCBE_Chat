package client;

import java.io.*;
import java.net.*;

import ui.ClientUI;

public class Client implements Runnable {

    private  ClientUI cltUI ;
    private  String name ;
    private int port ;
    private String host ;
    Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String msg ;
	private String topics;
    
    public Client(String name, int port , String host , String topics) throws IOException {


       this.name =name;
       this.port = port;
       this.host = host;
       this.topics = topics; 
		try {
			 socket = new Socket("localhost", 1234);
			 new Thread( this ).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
       
    }


	public void run()
	{ 
		try {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
	} catch (IOException e1) {
		e1.printStackTrace();
	}

		ClientUI cltUI = new ClientUI(name , port , "localhost");
		cltUI.start();
		
		out.println("My name is :"+name+":"+topics);
		out.flush();
		  
	     while (cltUI.getConnectionStatus()) 
			{  
	    	  try { // Poll every ~10 ms
	              Thread.sleep(10);
	           }
	           catch (InterruptedException e) {}
	    	  
	    	  if(cltUI.msgStatus())
	    	  { 
	    		  out.println(cltUI.getMsg());
	    		  out.flush();
	    		  cltUI.resetMsg();
	    		 
	    	  }
	    	  else
	    	  {
			 try {
				if(in.ready())
						  { System.out.println(msg);
							  msg = in.readLine();
							  if(!msg.equals("EXIT") && !msg.equals("EXIT_ALL"))
							  {
							  cltUI.send(msg+"\n");
							  }
							  else if (msg.equals("EXIT"))
							  {
						      cltUI.changeStatus(1);
						      cltUI.send("Your name is taken\n");
						      break;
							  }
							  else if(msg.equals("EXIT_ALL"))
							  {
							  cltUI.changeStatus(1);
							  cltUI.send("Server is closed \n");
							  break;
							  }
								  
						  }
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	  }
			}
	
	        System.out.println("bye");
	        try {
		        socket.close();
				in.close();
		        out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}


}