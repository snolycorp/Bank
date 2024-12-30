package org.example.controller;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.service.AccountService;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService; // Ensure AccountService is injected

    @GetMapping
    public String showDashboard(Model model) {
        // Add any necessary attributes to the model
        return "dashboard";
    }

    @PostMapping("/find-user")
    public String findUser(@RequestParam String keyword, Model model) {
        Optional<User> user = userService.findUserByUsernameOrEmail(keyword);
        model.addAttribute("foundUser", user.orElse(null));
        return "dashboard";
    }

    @PostMapping("/send-money")
    public String sendMoney(@RequestParam Long fromAccountId, @RequestParam Long toAccountId, @RequestParam Double amount, @RequestParam String description) {
        transactionService.sendMoney(fromAccountId, toAccountId, amount, description);
        return "redirect:/dashboard";
    }

    @GetMapping("/confirm-transaction/{transactionId}")
    public String confirmTransaction(@PathVariable Long transactionId, Model model) {
        Optional<Transaction> transaction = transactionService.confirmTransaction(transactionId);
        model.addAttribute("transaction", transaction.orElse(null));
        return "dashboard";
    }

    @GetMapping("/view-account/{accountId}")
    public String viewAccount(@PathVariable Long accountId, Model model) {
        Optional<Account> account = accountService.findById(accountId);
        model.addAttribute("account", account.orElse(null));
        return "dashboard";
    }
}
