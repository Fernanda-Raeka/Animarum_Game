package com.fernanda.backend.controller;

import com.fernanda.backend.dto.RegisterRequest;
import com.fernanda.backend.model.Account;
import com.fernanda.backend.repository.AccountRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        if (accountRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        Account newAccount = new Account();
        newAccount.setUsername(request.getUsername());
        newAccount.setPasswordHash(request.getPassword());

        accountRepository.save(newAccount);
        return ResponseEntity.ok("Registration successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isPresent() && account.get().getPasswordHash().equals(password)) {
            return ResponseEntity.ok(account.get());
        }

        return ResponseEntity.status(401).body("Invalid username or password!");
    }
}