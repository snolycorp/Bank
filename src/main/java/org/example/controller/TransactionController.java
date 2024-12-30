package org.example.controller;

import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}")
    public String listTransactions(@PathVariable Long accountId, Model model) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        model.addAttribute("transactions", transactions);
        return "transaction-history";
    }

    @PostMapping
    public String createTransaction(@RequestParam Long accountId, @RequestParam Double amount, @RequestParam String type, @RequestParam String description, @RequestParam String receiver) {
        transactionService.createTransaction(accountId, amount, type, description, receiver);
        return "redirect:/transactions/" + accountId;
    }

    @PostMapping("/send")
    public String sendMoney(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam Double amount, @RequestParam String description) {
        transactionService.sendMoney(fromAccountId, toAccountId, amount, description);
        return "redirect:/transactions/" + fromAccountId;
    }
}
