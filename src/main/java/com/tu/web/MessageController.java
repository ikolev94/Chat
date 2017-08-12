package com.tu.web;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tu.model.ChatMessage;
import com.tu.service.MessageService;


@RestController
public class MessageController {
  
  private SimpMessagingTemplate template;
  
  @Autowired
  private MessageService messageService;
  
  @Inject
  public MessageController(SimpMessagingTemplate template) {
    this.template = template;
  }

  @MessageMapping("/chat")
  public void greeting(Message<Object> message, @Payload ChatMessage chatMessage) throws Exception {
    Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
    String authedSender = principal.getName();
    chatMessage.setSender(authedSender);
    String recipient = chatMessage.getRecipient();
    this.messageService.save(chatMessage);
    if (!authedSender.equals(recipient)) {
      template.convertAndSendToUser(authedSender, "/queue/messages", chatMessage);
    }
    
    template.convertAndSendToUser(recipient, "/queue/messages", chatMessage);
  }
  
  @RequestMapping("messagesBetween/{sender}/{recipient}")
  public List<ChatMessage> getM(@PathVariable String sender, @PathVariable String recipient){
	  return messageService.findMessagesBetweenTwoUsers(sender, recipient);
  }
  

}
