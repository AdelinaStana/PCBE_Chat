package server;

import java.util.LinkedList;

public class TopicsDB 
{
	LinkedList<TopicMessage> contents = new LinkedList<TopicMessage>();
	 
	public synchronized void push(TopicMessage cont)
	{
		contents.add(cont);
	}
	
	public synchronized String getContent()
	{
		return contents.getLast().getContent();
	}
	
	public synchronized void cleanOld(){
		long now = System.currentTimeMillis() / 1000L;
		for(int i=0; i < contents.size(); i++){
			if(now - contents.get(i).getTime() > 180000)
				contents.remove(i);
		}
	}
}
