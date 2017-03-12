package com.hellokoding.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellokoding.auth.model.ChatMessage;
import com.hellokoding.auth.repository.MessageRepository;

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
