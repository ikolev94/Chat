package com.hellokoding.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hellokoding.auth.model.ChatMessage;

public interface MessageRepository extends JpaRepository<ChatMessage, Long>{
	
	@Query(value = "SELECT * FROM chatmessages "
				 + "WHERE recipient = ?1 AND sender = ?2 "
				 + "OR recipient = ?2 AND sender=?1",
			nativeQuery = true)
	List<ChatMessage> findMessage(String sender, String recipient);

}
