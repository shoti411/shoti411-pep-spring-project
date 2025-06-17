package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
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
    public Message persistMessage(Message message) {
        return messageRepository.save(message);
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

    public void deleteMessageByMessageId(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    public int updateMessageByMessageId(Integer messageId, String messageText) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1; // returns 1 because success
        } else {
            return 0; // returns 0 because failure
        }
    }
}
