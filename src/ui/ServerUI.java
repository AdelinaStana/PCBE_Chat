package ui;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;

import server.Server;

public class ServerUI extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public ServerUI() {
		setResizable(false);
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(263, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		textArea.setBounds(52, 159, 150, 20);

		contentPane.add(textArea);
		JButton btnServer = new JButton("Start Server");

		btnServer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
					new Server();
					textArea.insert("Server Started!" ,0);
			}
			
		});
		btnServer.setBounds(70, 70, 120, 20);
		contentPane.add(btnServer);
	}
	

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try 
				{
					ServerUI frame = new ServerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
