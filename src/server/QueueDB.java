package server;

import java.util.LinkedList;

public class QueueDB
{
	private static LinkedList<String> queue = new LinkedList<String>();
	private static int MAX_LENGHT = 200;

	public String pop() 
	{
		String msg = queue.getFirst();
		queue.removeFirst();
		return msg;
	}

	public void push(String msg)
	{
		if(queue.size()<=MAX_LENGHT)
				queue.add(msg);
	}
 
}
