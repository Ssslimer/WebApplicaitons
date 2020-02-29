package task4.model;

import java.io.Serializable;

public class Message implements Serializable
{
	public enum MessageContent
	{
		ADD_RESERVATION, CANCEL_RESERVATION, STRING, GET_FREE_RESERVATIONS, NEW_RESERVATION, CONNECTION_INIT, TERMINATE;
	} 
	
	private static final long serialVersionUID = -5885736425999483646L;
	
	private final MessageContent content;
	private final Object data;
	
	private String sender;
	
	public Message(MessageContent activity, String sender, Object data)
	{
		this.content = activity;
		this.sender = sender;
		this.data = data;
	}
	
	public Message(MessageContent activity, Object data)
	{
		this.content = activity;
		this.data = data;
	}

	public MessageContent getActivity()
	{
		return content;
	}
	
	public Object getData()
	{
		return data;
	}
	
	public String getSender()
	{
		return sender;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Activity: ");
		builder.append(content.toString());
		builder.append(" Data: ");
		builder.append(data.toString());
		
		return builder.toString();
	}
}