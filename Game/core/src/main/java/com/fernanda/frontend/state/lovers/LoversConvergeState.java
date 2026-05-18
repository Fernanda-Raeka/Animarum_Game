package com.fernanda.frontend.state.lovers;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.strategy.attack.AttackPattern;
import com.fernanda.frontend.strategy.attack.ConvergingEmbracePattern;

public class LoversConvergeState implements BossState {
    private AttackPattern attackPattern;
    private float timer;

    private float spawnTimer;
    private final float SPAWN_INTERVAL = 2.0f; 
    private final float STATE_DURATION = 10.0f; 
    private int waveCount;

    @Override
    public void enter(BaseBoss boss) {
        this.timer = 0f;
        this.spawnTimer = SPAWN_INTERVAL; 
        this.waveCount = 0;
        attackPattern = new ConvergingEmbracePattern();
        
        if (boss instanceof LoversBoss) {
            LoversBoss lovers = (LoversBoss) boss;
            lovers.setTargetFade(0f, 8f); 
            lovers.setArenaSplit(false); 
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        timer += delta;
        spawnTimer += delta;

        if (spawnTimer >= SPAWN_INTERVAL && waveCount < 5) {
            spawnTimer = 0f;
            attackPattern.execute(boss, arenaX, arenaY, arenaSize);
            waveCount++;
        }

        if (timer >= STATE_DURATION) {
            boss.changeState(new LoversIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) {
        System.out.println("The Lovers menyelesaikan Converging Embrace.");
    }
}
