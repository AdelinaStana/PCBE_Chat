package server;

public class TopicMessage extends Message
{

	  private String content , topic , time , type;
	  
	  public TopicMessage(String topic , String time, String content)
	  {
		  this.content  = content ;
		  this.topic    = topic;
		  this.time     = time;
		  this.type     = "Topic";
		  
	  }

	  public String getContent() 
	  {
			return this.content;
	  }
		  
	  public String getTime()
	  {
		  return this.time;
	  }
	  
	  public String getType()
	  {
		  return this.type;
	  }
	  
	  public String getTopic()
	  {
		  return this.topic;
	  }
	}
