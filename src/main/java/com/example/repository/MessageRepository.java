package com.example.repository;

import java.util.List;

import com.example.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Retrieves all messages in database
     * @return A list of all the messages in the database
     */
    public List<Message> findMessages();

    /**
     * Retrieves message by specific id
     * @param messageId an Integer that identifies distinct books.
     * @return the message with a particular messageId
     */
    public Message findMessageByMessageId(Integer messageId);

    /**
     * Retrieves the messages posted by the specific account
     * @param postedBy an Integer that identifies distinct accounts
     * @return a list of the messages posted by the account
     */
    public List<Message> findMessagesByPostedBy(Integer postedBy);
}
