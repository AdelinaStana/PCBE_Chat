package server;

import java.util.LinkedList;

public class TopicsDB 
{
	LinkedList<String> contents = new LinkedList<String>();
	 
	public void setTopics(String cont)
	{
		contents.add(cont);
	}
	
	public String getContent()
	{
		return contents.getLast();
	}
	

}
