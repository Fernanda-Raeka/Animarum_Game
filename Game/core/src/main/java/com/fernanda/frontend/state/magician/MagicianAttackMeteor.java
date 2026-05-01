package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;

public class MagicianAttackMeteor implements BossState {
    
    private float attackTimer;
    private float spawnTimer;
    private final float ATTACK_DURATION = 5f;
    private final float SPAWN_INTERVAL = 0.2f;

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.TOP);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.BROWN); 
            System.out.println("Sistem: THE MAGICIAN menjatuhkan METEOR dari arah " + mBoss.getCurrentSide() + "!");
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        spawnTimer += delta;
        
        if (boss instanceof MagicianBoss) {
            ((MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }
        
        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0f;
            float meteorX = MathUtils.random(arenaX, arenaX + arenaSize - 60f);
            float meteorY = arenaY + arenaSize; 
            float speedY = -400f; 
            
            com.fernanda.frontend.entities.projectiles.BaseProjectile meteor = 
                com.fernanda.frontend.factory.ProjectileFactory.obtainMeteor(meteorX, meteorY, 0, speedY);
            boss.getProjectiles().add(meteor);
        }
        
        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
