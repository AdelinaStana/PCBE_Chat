package ui;

import java.io.*;
import java.net.BindException;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import client.Client;
import server.Server;

public class Start
{

   public static String hostIP = "localhost";
   public static String name = "Server";
   public static String topics = "";
   public static int port = 1234;
   public static boolean isHost = true;
   public static boolean connect = false;

   public static JFrame mainFrame = null;
   public static JTextArea chatText = null;
   public static JTextField chatLine = null;
   public static JPanel statusBar = null;
   public static JLabel statusField = null;
   public static JTextField statusColor = null;
   public static JTextField ipField = null;
   public static JTextField nameField = null;
   public static JTextField topicsField = null;
   public static JTextField portField = null;
   public static JRadioButton hostOption = null;
   public static JRadioButton guestOption = null;
   public static JButton connectButton = null;
   public static JButton disconnectButton = null;


   private static JPanel initOptionsPane() {
      JPanel pane = null;
      JPanel pane1 = null;
      ActionAdapter buttonListener = null;

      // Create an options pane
      JPanel optionsPane = new JPanel(new GridLayout(4, 1));

      // IP address input
      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Host IP:"));
      ipField = new JTextField(10);
      ipField.setText(hostIP);
      ipField.setEnabled(false);
      ipField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                ipField.selectAll();
                hostIP = ipField.getText();
               }
            });
      pane.add(ipField);
      optionsPane.add(pane);
      
      pane1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane1.add(new JLabel("Name:"));
      nameField = new JTextField(10);
      nameField.setText(name);
      nameField.setEnabled(false);
      nameField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                nameField.selectAll();
                name = nameField.getText();
               }
            });
      pane1.add(nameField);
      optionsPane.add(pane1);


      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Port:"));
      portField = new JTextField(10); portField.setEditable(true);
      portField.setText((new Integer(port)).toString());
      portField.addFocusListener(new FocusAdapter() 
      {
            public void focusLost(FocusEvent e) {

                  int temp;
                  try {
                     temp = Integer.parseInt(portField.getText());
                     port = temp;
                  }
                  catch (NumberFormatException nfe) {
                     portField.setText((new Integer(port)).toString());
                     mainFrame.repaint();
                  }
            }
         });
      pane.add(portField);
      optionsPane.add(pane);
      
      JPanel pane2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane2.add(new JLabel("Topics:"));
      topicsField = new JTextField(10);
      topicsField.setText(name);
      topicsField.setEnabled(false);
      topicsField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                topicsField.selectAll();
                topics = topicsField.getText();
               }
            });
      pane2.add(topicsField);
      optionsPane.add(pane2);

      // Host/guest option
      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
                  isHost = e.getActionCommand().equals("host");
                  if (isHost) {
                     ipField.setEnabled(false);
                     ipField.setText("localhost");
                     nameField.setEnabled(false);
                     nameField.setText("Server");
                     topicsField.setEnabled(false);
                     hostIP = "localhost";
                     name = "Server";
                  }
                  else {
                     ipField.setEnabled(true);
                     name = "Guest";
                     nameField.setText(name);
                     nameField.setEnabled(true);
                     topicsField.setEnabled(true);
                  }
            }
         };
      ButtonGroup bg = new ButtonGroup();
      hostOption = new JRadioButton("Host", true);
      hostOption.setMnemonic(KeyEvent.VK_H);
      hostOption.setActionCommand("host");
      hostOption.addActionListener(buttonListener);
      guestOption = new JRadioButton("Guest", false);
      guestOption.setMnemonic(KeyEvent.VK_G);
      guestOption.setActionCommand("guest");
      guestOption.addActionListener(buttonListener);
      bg.add(hostOption);
      bg.add(guestOption);
      pane = new JPanel(new GridLayout(1, 2));
      pane.add(hostOption);
      pane.add(guestOption);
      optionsPane.add(pane);

      JPanel buttonPane = new JPanel(new GridLayout(1, 2));
      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) {
            	 if (e.getActionCommand().equals("connect")) {
                     connect = true ;
                     mainFrame.setVisible(false);
                  }
              
            }
         };
      connectButton = new JButton("Connect");
      connectButton.setMnemonic(KeyEvent.VK_C);
      connectButton.setActionCommand("connect");
      connectButton.addActionListener(buttonListener);
      connectButton.setEnabled(true);
      buttonPane.add(connectButton);
      optionsPane.add(buttonPane);

      return optionsPane;
   }

   private static void initGUI() 
   {

      JPanel optionsPane = initOptionsPane();
      
      JPanel mainPane = new JPanel(new BorderLayout());
      mainPane.add(optionsPane, BorderLayout.WEST);

      mainFrame = new JFrame("Start");
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setContentPane(mainPane);

      mainFrame.setIconImage(new ImageIcon("Jface.png").getImage());
      mainFrame.setResizable(false);
      mainFrame.setSize(mainFrame.getPreferredSize());
      mainFrame.setLocation(200, 200);
      mainFrame.pack();
      mainFrame.setVisible(true);
   }

	public static void main(String args[]) throws IOException, InterruptedException 
	{
	
	      initGUI();
	
	      while(true)
	      {
	    	  try 
	    	  {
	              Thread.sleep(10);
	           }
	           catch (InterruptedException e) {}

	    	  if(connect)
	    	  {	 if(isHost)
		    	  {
	    		  try{
	    			  new Server(port);
		    	  }
		 		    catch(BindException e){
				    	break;
				    }
		    	  }
		    	  
		    	  else {
		    		  new Client(name,port,hostIP,topics);
		    		  break ;
		    	  }
	    	  }
	      }
	      mainFrame.setVisible(false);   
	      			
	}
}