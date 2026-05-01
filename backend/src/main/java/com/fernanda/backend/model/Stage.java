package com.fernanda.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stages")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Integer stage_id;

    private String stage_name;
    private String boss_name;
    private String lore_text;

    public Integer getStage_id() {
        return stage_id;
    }

    public void setStage_id(Integer stage_id) {
        this.stage_id = stage_id;
    }

    public String getStage_name() {
        return stage_name;
    }

    public void setStage_name(String stage_name) {
        this.stage_name = stage_name;
    }

    public String getBoss_name() {
        return boss_name;
    }

    public void setBoss_name(String boss_name) {
        this.boss_name = boss_name;
    }

    public String getLore_text() {
        return lore_text;
    }

    public void setLore_text(String lore_text) {
        this.lore_text = lore_text;
    }
}