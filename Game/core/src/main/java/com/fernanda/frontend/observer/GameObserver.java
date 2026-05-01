package com.fernanda.frontend.observer;

public interface GameObserver {
    void onPlayerHPChanged(float currentHp, float maxHp);
    void onBossHPChanged(float currentHp, float maxHp);
}
