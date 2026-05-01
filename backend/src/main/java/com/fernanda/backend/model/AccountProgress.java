package com.fernanda.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "account_progress")
public class AccountProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progress_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private Boolean is_cleared = false;
    private Integer stars_earned = 0;

    public Integer getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(Integer progress_id) {
        this.progress_id = progress_id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Boolean getIs_cleared() {
        return is_cleared;
    }

    public void setIs_cleared(Boolean is_cleared) {
        this.is_cleared = is_cleared;
    }

    public Stage getStage() {
        return stage;
    }

    public Integer getStars_earned() {
        return stars_earned;
    }

    public void setStars_earned(Integer stars_earned) {
        this.stars_earned = stars_earned;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}