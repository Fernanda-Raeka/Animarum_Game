package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;

public class MagicianIdleState implements BossState {
    
    private float idleTimer;
    private final float IDLE_DURATION = 4.5f;
    private boolean hasSpawnedSpark;

    @Override
    public void enter(BaseBoss boss) {
        System.out.println(boss.getName() + " memasuki Idle State.");
        idleTimer = 0f;
        hasSpawnedSpark = false;
        if (boss instanceof com.fernanda.frontend.entities.MagicianBoss) {
            ((com.fernanda.frontend.entities.MagicianBoss) boss).setBossColor(com.badlogic.gdx.graphics.Color.PURPLE);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        idleTimer += delta;
        
        if (boss instanceof com.fernanda.frontend.entities.MagicianBoss) {
            ((com.fernanda.frontend.entities.MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }
        
        if (!hasSpawnedSpark && idleTimer >= 0f) {
            boolean isDuo = false; // sementara solo spark doang
            sparks.add(com.fernanda.frontend.factory.SparkFactory.obtainSpark(arenaX, arenaY, arenaSize, isDuo));
            hasSpawnedSpark = true;
            System.out.println("Sistem: The Magician memunculkan 1 Anima Spark!");
        }
        
        if (idleTimer >= IDLE_DURATION) {
            int randomAttack = com.badlogic.gdx.math.MathUtils.random(1, 4);
            switch (randomAttack) {
                case 1: boss.changeState(new MagicianAttackFireball()); break;
                case 2: boss.changeState(new MagicianAttackLaser()); break;
                case 3: boss.changeState(new MagicianAttackMeteor()); break;
                case 4: boss.changeState(new MagicianAttackMirror()); break;
            }
        }
    }

    @Override
    public void exit(BaseBoss boss) {
        System.out.println(boss.getName() + " keluar dari Idle State.");
    }
}
