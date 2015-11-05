package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;

public class LoginUI extends JFrame
{

private static final long serialVersionUID = 1L;
private JPanel contentPane;
private JTextField textUsername;
private JButton btnLogin;

public LoginUI() throws IOException {
	setResizable(false);
	setTitle("Login");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(263, 300);
	setLocationRelativeTo(null);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	textUsername = new JTextField();
	textUsername.setBounds(52, 75, 152, 20);
	contentPane.add(textUsername);
	textUsername.setColumns(10);
	
	JLabel lblUsername = new JLabel("Username :");
	lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
	lblUsername.setBounds(84, 50, 90, 20);
	contentPane.add(lblUsername);
	
	btnLogin = new JButton("Login");
	btnLogin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String username = textUsername.getText();
			login(username);
		}
		
	});
	
	btnLogin.setBounds(76, 228, 98, 20);
	contentPane.add(btnLogin);
}


private void login(String username) 
{
	dispose();
	try {
		Client cl = new Client();
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
}

public static void main(String[] args)
{
	EventQueue.invokeLater(new Runnable() 
	{
		public void run() {
			try {
				LoginUI frame = new LoginUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
}