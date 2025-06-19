package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.naming.AuthenticationException;

@Service
public class AccountService {
    AccountRepository accountRepository;


    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return this.accountRepository.findAll();
    }

    /**
     * Creates a User Account and saves it to the database
     * @param account the account to be saved
     * @return the account being saved
     */
    public Account persistAccount(Account account) throws IllegalArgumentException, IllegalStateException {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new IllegalArgumentException();
        }
        List<Account> accounts = this.accountRepository.findAll();
        for (Account acc: accounts) {
            if (acc.getUsername().equals(account.getUsername())) {
                throw new IllegalStateException();
            }
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password) throws AuthenticationException {
        List<Account> accountList = this.accountRepository.findAll(); // find all accounts already in account repository
        for (Account acc: accountList) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                return acc;
            }
        }
        throw new AuthenticationException("Username and Password credentials are invalid");
    } 
}
