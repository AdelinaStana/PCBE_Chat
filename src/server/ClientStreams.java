package server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class ClientStreams {
	
	private String name;
	private Socket socket;
	private int port ;
	private PrintWriter out ;
	private LinkedList<String> topicList = new LinkedList<String>();
	
	public synchronized void setName(String name)
	{
		this.name = name;
	}
	
	public synchronized void setSocket(Socket ss)
	{
		this.socket=ss;
	}
	
	public synchronized void setPort(int port)
	{
		this.port = port;
	}
	
	public synchronized void setTopics(String list)
	{
		
	}
	
	public synchronized String getName()
	{
		return this.name;
	}
	
	public Socket getSocket()
	{
		return this.socket;
	}
	
	public synchronized int getPort()
	{
		return this.port;
	}
	
	public synchronized LinkedList<String> getTopics()
	{
		return topicList;
	}
	
	public synchronized void setPrintWriter(PrintWriter out)
	{
		this.out = out;
	}
	
	public synchronized PrintWriter getPrintWriter()
	{
		return this.out;
	}
	
}
