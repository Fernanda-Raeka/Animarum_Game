package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;

public class MagicianAttackLaser implements BossState {
    
    private float attackTimer;
    private int laserCount = 0;
    private float nextSpawnTime = 0f;
    private final float ATTACK_DURATION = 6.2f; 

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        laserCount = 0;
        nextSpawnTime = 0f;
        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.RIGHT);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.YELLOW);
            System.out.println("Sistem: THE MAGICIAN menggunakan AREA DENIAL LASER!");
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        if (boss instanceof MagicianBoss) {
            ((MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }
        
        if (laserCount < 4 && attackTimer >= nextSpawnTime) {
            float charge = 1.5f; 
            int randomType = com.badlogic.gdx.math.MathUtils.random(1, 4);
            
            com.fernanda.frontend.entities.projectiles.BaseProjectile laser = 
                com.fernanda.frontend.factory.ProjectileFactory.obtainLaser(arenaX, arenaY, arenaSize, randomType, charge);
            boss.getProjectiles().add(laser);
            
            laserCount++;
            if (laserCount == 1) {
                nextSpawnTime = 1.5f;
            } else {
                nextSpawnTime += 1.5f;
            }
        }
        
        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
