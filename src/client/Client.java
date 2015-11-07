package client;

import java.awt.EventQueue;
import java.io.*;
import java.net.*;

import ui.ClientUI;

public class Client {

    private static String fromServer;
    private static String fromUser;
    private static ClientUI cltUI ;

    public static void launchClient() 
    {
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try {
					ClientUI cltUI = new ClientUI("Aaa");
					cltUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    public Client() throws IOException {

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            kkSocket = new Socket("127.0.0.1", 1234);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: 127.0.0.1");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 127.0.0.1");
            System.exit(1);
        }
        
        launchClient();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        while ((fromServer = in.readLine()) != null) 
        {
            cltUI.console("Server: " + fromServer);

            if (fromServer.equals("Bye."))
                break;

            fromUser = "dsd";
	        if (fromUser != null) 
	        {
	                cltUI.console("Client: " + fromUser);
	                out.println(fromUser);
	        }
        }

        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }


}