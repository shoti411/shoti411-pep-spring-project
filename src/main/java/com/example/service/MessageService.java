package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;


import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    MessageRepository messageRepository;
    
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Creates a Message to be saved to the database
     * @param message the message to be saved
     * @return the message being saved
     */
    public Message persistMessage(Message message) throws IllegalArgumentException {
        if (
            message.getMessageText().isBlank() || // if messageText blank
            message.getMessageText().length() > 255 // if messageText over 255 characters
            ) { // TODO: if messageId does not exist
            throw new IllegalArgumentException();
        } else {
            return messageRepository.save(message);
        }

    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByMessageId(Integer messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        } else {
            return null;
        }
    }

    public List<Message> getMessagesByPostedBy(Integer postedBy) {
        return messageRepository.findMessagesByPostedBy(postedBy);
    }

    public Integer deleteMessageByMessageId(Integer messageId) {
        Optional<Message> optionalMessage = this.messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) { // if id exists
            this.messageRepository.deleteById(messageId);
            return 1;
        } else {
            return null;
        }
        
    }

    public Integer patchMessageByMessageId(Integer messageId, String newMessageText) throws IllegalArgumentException {
        System.out.println(newMessageText);
        if (newMessageText.isBlank()) {
            throw new IllegalArgumentException();
        }
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(
            optionalMessage.isPresent() && // if message exists
            // (!newMessageText.isBlank()) && // if messageText is not blank
            newMessageText.length() <= 255) { // if messageText length is not over 255 characters
            Message message = optionalMessage.get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1; // returns 1 because success and thats the number of rows updated
        } else {
            throw new IllegalArgumentException();
        }
    }
}
