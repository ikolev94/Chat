package com.tu.model;

import javax.persistence.*;

/**
 * An entity class which contains the information of a single message.
 * Representing chatmessages table in the database.
 * 
 * @author ivan
 */
@Entity
@Table(name = "chatmessages")
public class ChatMessage {

	/**
	 * Unique identifier.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Recipient of the message
	 */
	private String recipient;

	/**
	 * Sender of the message
	 */
	private String sender;

	/**
	 * Message text content
	 */
	private String message;

	/**
	 * @return {@link String} Receive the recipient of the message.
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * Set recipient of the message.
	 * 
	 * @param recipient
	 *            name
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return {@link String} Receive the sender of the message.
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Set the sender of the message.
	 * 
	 * @param sender
	 *            name
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return {@link String} Receive the message content.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message content.
	 * 
	 * @param message
	 *            content
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
