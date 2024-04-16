package com.realTimeChat.controller;

import com.realTimeChat.entity.ChatMessage;
import com.realTimeChat.entity.Status;
import com.realTimeChat.entity.User;
import com.realTimeChat.repository.ChatMessageRepository;
import com.realTimeChat.service.ChatMessageService;
import com.realTimeChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    UserService service;
    @Autowired
    ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return chatMessageService.save(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        service.saveUser(User.builder()
                .fullName(chatMessage.getSender())
                .nickName(chatMessage.getSender())
                .build());
        return chatMessage;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnect(@Payload User user){
        service.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUser(){
        return ResponseEntity.ok(service.findConnectedUser());
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId") String senderId){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId));
    }
}
