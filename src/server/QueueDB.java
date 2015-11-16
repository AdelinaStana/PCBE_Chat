package server;

import java.util.LinkedList;

public class QueueDB
{
	private static LinkedList<QueueMessage> queue = new LinkedList<QueueMessage>();
	private static int MAX_LENGHT = 200;

	public QueueMessage pull() 
	{	
		synchronized (queue) {
			QueueMessage msg = queue.getFirst();
			queue.removeFirst();
			return msg;
		}
	}

	public void push(QueueMessage msg)
	{
		synchronized (queue) {		
			if(queue.size()<=MAX_LENGHT)
				queue.addFirst(msg);
		}
	}
	
	public int getSize(){
		synchronized (queue) {
			return queue.size();			
		}
	}
 
}
