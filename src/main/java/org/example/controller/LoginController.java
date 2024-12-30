package org.example.controller;

import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class LoginController {

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (userService.authenticateUser(username, password)) {
            // Successful login, redirect to dashboard
            return "redirect:/dashboard";
        } else {
            // Failed login, return to login page with error
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
        }
    }
