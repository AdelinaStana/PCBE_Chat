package ui;
   
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;


import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import client.Client;

public class ClientUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private String username ;
	private JTextField textInput;
	private JTextArea textArea;
	private DefaultCaret caret;
	private String msg;

	public ClientUI(String username) 
	{ 
		setTitle("Chat Room ~ "+username);
		this.username = username;
		this.msg="";
		
		
		
		setSize(476, 408);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scroll  = new JScrollPane(textArea);
		scroll.setBounds(10, 20, 450, 280);
		textArea.setBounds(10, 11, 450, 296);
		contentPane.add(scroll, textArea);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				send(textInput.getText());
			}
		});
		
		btnEnter.setFont(new Font("Arial", Font.PLAIN, 10));
		btnEnter.setBounds(401, 334, 59, 34);
		contentPane.add(btnEnter);
		
		textInput = new JTextField();
		
		textInput.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send(textInput.getText());
				}
			}
		});
		textInput.setFont(new Font("Arial", Font.PLAIN, 11));
		textInput.setBounds(10, 334, 381, 34);
		contentPane.add(textInput);
		textInput.setColumns(10);
		setVisible(true);
		
		textInput.requestFocusInWindow();
		
	}
	
	private void send(String message) 
	{
		if (message.equals("")) 
			return;
		this.msg=message;
		textInput.setText("");
	}
	
	public void console(String message) 
	{
		textArea.append(message + "\n\r");
	}
	
	public String getMsg()
	{
		return this.msg;
	}
	
	public void connectionFailed()
	{
		System.err.println("Connection failed!");
		console("Connection failed!");
	}

}