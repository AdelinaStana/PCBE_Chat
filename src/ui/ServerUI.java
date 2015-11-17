package ui;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.net.*;

public class ServerUI
{
   public final static int NULL = 0;
   public final static int DISCONNECTED = 1;
   public final static int BEGIN_CONNECT = 3;
   public final static int CONNECTED = 4;
   public static String hostIP;
   public static int port ;
   private static String message = "";
   
   public final static String statusMessages[] = {
      " Error! Could not connect!", " Disconnected",
      " Disconnecting...", " Connected", " Connected"
   };
  
   public final static String END_CHAT_SESSION =
      new Character((char)0).toString(); 
   
   public static int connectionStatus = CONNECTED;
   public static String statusString = statusMessages[connectionStatus];
   public static StringBuffer toAppend = new StringBuffer("");
   public static StringBuffer toSend = new StringBuffer("");
   
   public final static ServerUI jchat = new ServerUI(hostIP,port);

   public static JFrame mainFrame = null;
   public static JTextArea chatText = null;
   public static JTextField chatLine = null;
   public static JPanel statusBar = null;
   public static JLabel statusField = null;
   public static JTextField statusColor = null;
   public static JTextField ipField = null;
   public static JTextField portField = null;
   public static JRadioButton hostOption = null;
   public static JRadioButton guestOption = null;
   public static JButton connectButton = null;
   public static JButton disconnectButton = null;

   public ServerUI(String string, int port2)
   {
    port = port2;
	hostIP = string; 
   }

private static JPanel initOptionsPane() {
      JPanel pane = null;
      ActionAdapter buttonListener = null;

      JPanel optionsPane = new JPanel(new GridLayout(4, 1));

      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Host IP:"));
      ipField = new JTextField(10);
      ipField.setText(hostIP);
      ipField.setEnabled(false);
      ipField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
               ipField.selectAll();
               if (connectionStatus != DISCONNECTED) {
                  changeStatus(3);
               }
               else {
                  hostIP = ipField.getText();
               }
            }
         });
      pane.add(ipField);
      optionsPane.add(pane);

      pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      pane.add(new JLabel("Port:"));
      portField = new JTextField(10); portField.setEditable(true);
      portField.setText((new Integer(port)).toString());
      portField.setEnabled(false);
      pane.add(portField);
      optionsPane.add(pane);

      JPanel buttonPane = new JPanel(new GridLayout(1, 2));
      buttonListener = new ActionAdapter() {
            public void actionPerformed(ActionEvent e) 
            {

               if (e.getActionCommand().equals("disconnect")) {
            	   connectionStatus = 1;
            	   changeStatus(1);
               }
            }
         };
      disconnectButton = new JButton("Disconnect");
      disconnectButton.setMnemonic(KeyEvent.VK_D);
      disconnectButton.setActionCommand("disconnect");
      disconnectButton.addActionListener(buttonListener);
      disconnectButton.setEnabled(true);
      buttonPane.add(disconnectButton);
      optionsPane.add(buttonPane);

      return optionsPane;
   }

   private static void initGUI() 
   {

      statusField = new JLabel();
      statusField.setText(statusMessages[DISCONNECTED]);
      statusColor = new JTextField(1);
      statusColor.setBackground(Color.red);
      statusColor.setEditable(false);
      statusBar = new JPanel(new BorderLayout());
      statusBar.add(statusColor, BorderLayout.WEST);
      statusBar.add(statusField, BorderLayout.CENTER);


      JPanel optionsPane = initOptionsPane();

      JPanel chatPane = new JPanel(new BorderLayout());
      chatText = new JTextArea(10, 20);
      chatText.setLineWrap(true);
      chatText.setEditable(false);
      chatText.setForeground(Color.blue);
      JScrollPane chatTextPane = new JScrollPane(chatText,
         JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
         JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      chatPane.add(chatTextPane, BorderLayout.CENTER);
      chatPane.setPreferredSize(new Dimension(200, 200));

      JPanel mainPane = new JPanel(new BorderLayout());
      mainPane.add(statusBar, BorderLayout.SOUTH);
      mainPane.add(optionsPane, BorderLayout.WEST);
      mainPane.add(chatPane, BorderLayout.CENTER);


      mainFrame = new JFrame("~Server~");
      mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      mainFrame.setContentPane(mainPane);
      mainFrame.setIconImage(new ImageIcon("Jface.png").getImage());
      mainFrame.setResizable(false);
      mainFrame.setSize(mainFrame.getPreferredSize());
      mainFrame.setLocation(200, 200);
      mainFrame.pack();
      mainFrame.setVisible(true);
      mainFrame.setResizable(true);
      changeStatus(4);
      
      mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
  	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
  	            if (!disconnectButton.isEnabled()){
  	            try { 
  		              Thread.sleep(10);
  		           }
  		           catch (InterruptedException e) {}
  	            mainFrame.setVisible(false);
  	            mainFrame.dispose();
  	        	System.exit(0);
  	        }
  	    }
  	    });
   }
   
   public  void appendToChatBox(String s) {
      synchronized (toAppend) {
         toAppend.append(s+"\n");
         chatText.append(toAppend.toString());
      }
   }


   public static void sendString(String s) {
      synchronized (toSend) {
         toSend.append(s + "\n");
         if (toSend.length() != 0) {
             toSend.setLength(0);
             changeStatus(NULL);
          }
      }
   }
  
  public boolean getConnectionStatus()
  {System.out.println(connectionStatus);
	  if(connectionStatus == 4 )
	  {
		  return true ;
	  }
	  return false;
  }
  
  public boolean msgStatus() {
	  if (message.length() != 0)
      {
         return true;
        
      }
	  return false;
	}
  
  public String getMsg() {
		return message;
	}

  public void resetMsg()
  {
	  message = "";
  }
  
   public static void changeStatus(int connectionStatus) {
      switch (connectionStatus) {
      case DISCONNECTED:
         disconnectButton.setEnabled(false);
         ipField.setEnabled(true);
         portField.setEnabled(true);
         statusColor.setBackground(Color.red);
         statusString = statusMessages[1];
         break;

      case CONNECTED:
         disconnectButton.setEnabled(true);
         ipField.setEnabled(false);
         portField.setEnabled(false);
         statusColor.setBackground(Color.green);
         break;
      }

      ipField.setText(hostIP);
      portField.setText((new Integer(port)).toString());
      statusField.setText(statusString);
      chatText.append(toAppend.toString());
      toAppend.setLength(0);

      mainFrame.repaint();
   }


public void start() 
{
	 	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());    
		}
	    catch (Exception ex) {
	    }

      initGUI();

}


}


