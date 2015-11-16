package server;

public class QueueMessage extends Message
{
  private String content , receiver , type;
  
  public QueueMessage(String reciever , String content)
  {
	  this.content = content ;
	  this.receiver = reciever;
	  this.type     = "Queue";
	  
  }

@Override
  public String getContent() 
  {
		return this.content;
  }
  
  public String getType()
  {
	  return this.type;
  }

@Override
public String getReciver() {
	 return this.receiver;
}
	  
}
