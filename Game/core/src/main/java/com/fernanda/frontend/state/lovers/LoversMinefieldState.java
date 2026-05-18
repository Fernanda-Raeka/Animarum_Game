package com.fernanda.frontend.state.lovers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.strategy.attack.AttackPattern;
import com.fernanda.frontend.strategy.attack.MinefieldPattern;

public class LoversMinefieldState implements BossState {
    private AttackPattern attackPattern;
    private float timer;

    private float spawnTimer;
    private final float SPAWN_INTERVAL = 1.8f; // Jeda 1.8s (umur ranjau 1.0s + 0.3s = 1.3s, sisa 0.5s layar bersih)
    private final float STATE_DURATION = 8.0f;

    @Override
    public void enter(BaseBoss boss) {
        this.timer = 0f;
        this.spawnTimer = SPAWN_INTERVAL; 
        attackPattern = new MinefieldPattern();
        
        if (boss instanceof LoversBoss) {
            LoversBoss lovers = (LoversBoss) boss;
            lovers.setTargetFade(0f, 8f); 
            lovers.setArenaSplit(true);
            
            float sw = Gdx.graphics.getWidth();
            float sh = Gdx.graphics.getHeight();
            float boxWidth = 1000f;
            float boxHeight = 120f; 
            float gap = 150f;
            
            float totalHeight = (boxHeight * 2) + gap;
            float startX = (sw - boxWidth) / 2f;
            float startY = (sh - totalHeight) / 2f;
            
            lovers.setBoundsPlayer2(startX, startY, boxWidth, boxHeight);
            lovers.setBoundsPlayer1(startX, startY + boxHeight + gap, boxWidth, boxHeight);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        timer += delta;
        spawnTimer += delta;

        if (spawnTimer >= SPAWN_INTERVAL) {
            if (attackPattern != null) {
                attackPattern.execute(boss, arenaX, arenaY, arenaSize);
            }
            spawnTimer = 0f;
        }

        if (timer >= STATE_DURATION) {
            boss.changeState(new LoversIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) {
    }
}