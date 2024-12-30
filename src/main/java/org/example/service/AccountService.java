package org.example.service;

import org.example.model.Account;
import org.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public void saveAccount(Account account){
        accountRepository.save(account);
    }

    public List<Account> findByUserId(Long userId){
        return accountRepository.findByUserId(userId);
    }

    // Add the findById method
    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }
}
