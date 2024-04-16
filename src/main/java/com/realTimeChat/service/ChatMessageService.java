package com.realTimeChat.service;

import com.realTimeChat.entity.ChatMessage;
import com.realTimeChat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;

    public ChatMessage save(ChatMessage chatMessage){
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId){
        var chat = repository.findBySender(senderId);
        return chat;
    }
}
