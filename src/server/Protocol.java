package server;

public class Protocol
{

    public Message processInput(String theInput) 
    {
    	String[] listOfArgs = new String[100] ;
    	QueueMessage qMsg ;
    	TopicMessage tMsg ;
    	listOfArgs = theInput.split(":");
    	
    	if(this.countOccurrences(theInput,':')==1)
    	{
    		
    		qMsg = new QueueMessage(listOfArgs[0],listOfArgs[1]);
    		return qMsg ; 
    		
    	}
    	else
    	{
    		tMsg = new TopicMessage(listOfArgs[0],listOfArgs[1]);
    		return tMsg;
    	}
    	
    }
    
    public int countOccurrences(String input, char c)
    {
        int count = 0;
        for (int i=0; i < input.length(); i++)
        {
            if (input.charAt(i) == c)
            {
                 count++;
            }
        }
        return count;
    }
}
