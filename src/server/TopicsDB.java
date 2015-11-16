package server;

import java.util.LinkedList;

public class TopicsDB 
{
	LinkedList<TopicMessage> contents = new LinkedList<TopicMessage>();
	 
	public TopicMessage pushpull(TopicMessage cont)
	{
		synchronized(contents){
			contents.addFirst(cont);
			return cont;
		}
	}
		
	public void cleanOld(){
		synchronized (contents){
			long now = System.currentTimeMillis() / 1000L;
			for(int i=0; i < contents.size(); i++){
				if(now - contents.get(i).getTime() > 180000)
					contents.remove(i);
			}
		}
	}
}
