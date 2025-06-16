package com.example.repository;

import java.util.List;

import com.example.entity.Account;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    
}
