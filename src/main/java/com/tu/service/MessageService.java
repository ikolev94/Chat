package com.tu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tu.model.ChatMessage;
import com.tu.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public void save(ChatMessage message){
		messageRepository.save(message);
	}
	
	public List<ChatMessage> findMessagesBetweenTwoUsers(String sender,String recipient){
		List<ChatMessage> a = messageRepository.findMessage(sender,recipient);
		
		return a;
	}
}
