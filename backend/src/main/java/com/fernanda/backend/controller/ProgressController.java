package com.fernanda.backend.controller;

import com.fernanda.backend.model.AccountProgress;
import com.fernanda.backend.model.Stage;
import com.fernanda.backend.repository.AccountProgressRepository;
import com.fernanda.backend.repository.StageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private StageRepository stageRepository;

    @Autowired
    private AccountProgressRepository progressRepository;

    @GetMapping("/stages")
    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    @GetMapping("/account/{accountId}")
    public List<AccountProgress> getAccountProgress(@PathVariable Integer accountId) {
        return progressRepository.findByAccount_AccountId(accountId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProgress(@RequestBody AccountProgress progress) {
        progressRepository.save(progress);
        return ResponseEntity.ok("Progress saved successfully!");
    }
}