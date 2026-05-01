package com.fernanda.backend.controller;

import com.fernanda.backend.model.StageDialogue;
import com.fernanda.backend.repository.StageDialogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dialogues")
public class DialogueController {

    @Autowired
    private StageDialogueRepository dialogueRepository;

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<List<StageDialogue>> getDialoguesByStage(@PathVariable Integer stageId) {
        List<StageDialogue> dialogues = dialogueRepository.findDialoguesByStageId(stageId);

        if (dialogues.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dialogues);
    }
}