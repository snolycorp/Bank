package org.example.service;

import org.example.exception.InsufficientFundsException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Account;
import org.example.model.Transaction;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Transaction createTransaction(Long accountId, Double amount, String type, String description, String receiver) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));
        Double charge = calculateTransactionCharge(amount);
        Double totalAmount = amount + charge;
        validateTransaction(account, totalAmount, type);
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCharge(charge);
        transaction.setDescription(description);
        transaction.setReceiver(receiver);
        updateAccountBalance(account, totalAmount, type);
        transactionRepository.save(transaction);
        accountRepository.save(account);
        return transaction;
    }

    @Transactional
    public Transaction sendMoney(Long fromAccountId, Long toAccountId, Double amount, String description) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found for this id :: " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found for this id :: " + toAccountId));

        Double charge = calculateTransactionCharge(amount);
        Double totalAmount = amount + charge;

        validateTransaction(fromAccount, totalAmount, "debit");

        // Create debit transaction for sender
        Transaction debitTransaction = new Transaction();
        debitTransaction.setAccount(fromAccount);
        debitTransaction.setAmount(amount);
        debitTransaction.setType("debit");
        debitTransaction.setTransactionDate(LocalDateTime.now());
        debitTransaction.setCharge(charge);
        debitTransaction.setDescription(description);
        debitTransaction.setReceiver(toAccount.getAccountNumber());
        updateAccountBalance(fromAccount, totalAmount, "debit");

        // Create credit transaction for receiver
        Transaction creditTransaction = new Transaction();
        creditTransaction.setAccount(toAccount);
        creditTransaction.setAmount(amount);
        creditTransaction.setType("credit");
        creditTransaction.setTransactionDate(LocalDateTime.now());
        creditTransaction.setCharge(0.0);
        creditTransaction.setDescription(description);
        creditTransaction.setReceiver(fromAccount.getAccountNumber());
        updateAccountBalance(toAccount, amount, "credit");

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return debitTransaction;
    }

    private Double calculateTransactionCharge(Double amount) {
        if (amount <= 1000) {
            return 10.00;
        } else if (amount <= 5000) {
            return 25.00;
        } else {
            return 50.00;
        }
    }

    private void validateTransaction(Account account, Double totalAmount, String type) {
        if (type.equalsIgnoreCase("debit") && account.getBalance() < totalAmount) {
            throw new InsufficientFundsException("Insufficient funds for this transaction");
        }
    }

    private void updateAccountBalance(Account account, Double totalAmount, String type) {
        if (type.equalsIgnoreCase("debit")) {
            account.setBalance(account.getBalance() - totalAmount);
        } else if (type.equalsIgnoreCase("credit")) {
            account.setBalance(account.getBalance() + totalAmount);
        }
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    // Add the confirmTransaction method
    public Optional<Transaction> confirmTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }
}
