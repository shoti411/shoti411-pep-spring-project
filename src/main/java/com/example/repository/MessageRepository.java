package com.example.repository;

import java.util.List;

import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Retrieves the messages posted by the specific account (this is a named query method using JpaRepository)
     * @param postedBy an Integer that identifies distinct accounts
     * @return a list of the messages posted by the account
     */
    public List<Message> findMessagesByPostedBy(Integer postedBy);
}
