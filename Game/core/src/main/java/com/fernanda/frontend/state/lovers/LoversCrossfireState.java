package com.fernanda.frontend.state.lovers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.strategy.attack.AttackPattern;
import com.fernanda.frontend.strategy.attack.CrossfirePattern;

public class LoversCrossfireState implements BossState {
    private AttackPattern attackPattern;
    private float timer;
    private float spawnTimer;
    private final float SPAWN_INTERVAL = 0.3f; 
    private final float STATE_DURATION = 4.5f; 

    @Override
    public void enter(BaseBoss boss) {
        this.timer = 0f;
        this.spawnTimer = 0f;
        attackPattern = new CrossfirePattern();
        
        if (boss instanceof LoversBoss) {
            LoversBoss lovers = (LoversBoss) boss;
            lovers.setTargetFade(0f, 8f); // Layar kembali terang dalam 0.5 detik
            lovers.setArenaSplit(true);
            
            float sw = Gdx.graphics.getWidth();
            float sh = Gdx.graphics.getHeight();
            float boxSize = 350f;
            float gap = 150f;
            
            float totalWidth = (boxSize * 2) + gap;
            float startX = (sw - totalWidth) / 2f;
            float startY = (sh - boxSize) / 2f;
            
            lovers.setBoundsPlayer1(startX, startY, boxSize, boxSize);
            lovers.setBoundsPlayer2(startX + boxSize + gap, startY, boxSize, boxSize);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        timer += delta;
        spawnTimer += delta;

        // Eksekusi tembakan setiap SPAWN_INTERVAL detik
        if (spawnTimer >= SPAWN_INTERVAL) {
            if (attackPattern != null) {
                attackPattern.execute(boss, arenaX, arenaY, arenaSize);
            }
            spawnTimer = 0f; // Reset timer tembakan
        }

        // Jika durasi state habis, pindah ke state Idle untuk memunculkan Spark
        if (timer >= STATE_DURATION) {
            boss.changeState(new LoversIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) {
    }
}