package server;

public class TopicMessage extends Message
{

	  private String content , topic , type;
	  private long time;
	  
	  public TopicMessage(String topic , String content)
	  {
		  this.content  = content ;
		  this.topic    = topic;
		  this.time     = System.currentTimeMillis() / 1000L;
		  this.type     = "Topic";
		  
	  }

	  public String getContent() 
	  {
			return this.content;
	  }
		  
	  public long getTime()
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

	@Override
	public String getReciver() {
		return null;
	}
	}
