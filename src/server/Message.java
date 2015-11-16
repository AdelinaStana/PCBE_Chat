package server;

abstract class Message 
{
	abstract String getContent();
	abstract String getType();
	abstract public String getReciver();

}
