package com.hellokoding.auth.model;

import javax.persistence.*;

@Entity
@Table(name="chatmessages")
public class ChatMessage {
	  
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;
	  private String recipient;
	  private String sender;
	  private String message;
	  
	  public String getRecipient() { return recipient; }
	  public void setRecipient(String recipient) { this.recipient = recipient; }
	  
	  
	  public String getSender() { return sender; }
	  public void setSender(String sender) { this.sender = sender; }
	  
	  public String getMessage() { return message; }
	  public void setMessage(String message) { this.message = message; }

}
