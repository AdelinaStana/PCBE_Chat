package server;

import java.util.LinkedList;

public class QueueDB
{
	private static LinkedList<QueueMessage> queue = new LinkedList<QueueMessage>();
	private static int MAX_LENGHT = 200;

	public synchronized String getContent() 
	{	
		QueueMessage msg = queue.getFirst();
		queue.removeFirst();
		return msg.getContent();
	}

	public synchronized void push(QueueMessage msg)
	{
		if(queue.size()<=MAX_LENGHT)
			queue.add(msg);
	}
	
	public int getSize(){
		return queue.size();
	}
 
}
