package com.fernanda.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "stage_dialogues")
public class StageDialogue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dialogue_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private Integer sequence_order;
    private String speaker_name;
    private String dialogue_text;

    public Integer getDialogue_id() {
        return dialogue_id;
    }

    public void setDialogue_id(Integer dialogue_id) {
        this.dialogue_id = dialogue_id;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Integer getSequence_order() {
        return sequence_order;
    }

    public void setSequence_order(Integer sequence_order) {
        this.sequence_order = sequence_order;
    }

    public String getSpeaker_name() {
        return speaker_name;
    }

    public void setSpeaker_name(String speaker_name) {
        this.speaker_name = speaker_name;
    }

    public String getDialogue_text() {
        return dialogue_text;
    }

    public void setDialogue_text(String dialogue_text) {
        this.dialogue_text = dialogue_text;
    }
}