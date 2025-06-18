package com.example.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.*;

import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    // ApplicationContext applicationContext = SpringApplication.run(SocialMediaController.class);
    // AccountService accountService = applicationContext.getBean(AccountService.class);
    // MessageService messageService = applicationContext.getBean(MessageService.class);

    private AccountService accountService;
    private MessageService messageService;
    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = this.messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageFromMessageId(@PathVariable int messageId) {
        Message message = this.messageService.getMessageByMessageId(messageId);
        if (message == null) {
            return ResponseEntity.status(401).body(message);
        } else {
            return ResponseEntity.status(200).body(message);
        }
    }

    // TODO: PUT REQUIREMENTS IF DELETE FAILED
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageFromMessageId(@PathVariable int messageId) {
        this.messageService.deleteMessageByMessageId(messageId);
        return ResponseEntity.accepted().body(1);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        this.messageService.persistMessage(message);
        return ResponseEntity.status(200).body(message);
    }

    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        this.accountService.persistAccount(account);
        return ResponseEntity.status(200).body(account);
    }

    // TODO: fix login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account accountFromDatabase = this.accountService.login(account.getUsername(), account.getPassword());
        return ResponseEntity.status(200).body(accountFromDatabase);
    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Void> handleUnauthorized(AuthenticationException authException) {
        return ResponseEntity.status(401).body(null);
    }

}
