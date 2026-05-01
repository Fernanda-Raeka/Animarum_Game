package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;

public class MagicianAttackMirror implements BossState {
    
    private float attackTimer;
    private int waveCount = 0;
    private float nextSpawnTime = 0.5f;
    private final float ATTACK_DURATION = 7.5f; 

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        waveCount = 0;
        nextSpawnTime = 0.5f;
        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.LEFT);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.CYAN); 
            System.out.println("Sistem: THE MAGICIAN menggunakan THE PHANTOM WAVES!");
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        if (boss instanceof MagicianBoss) {
            ((MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }
        
        if (waveCount < 4 && attackTimer >= nextSpawnTime) {
            int fakeIndex1 = com.badlogic.gdx.math.MathUtils.random(0, 9);
            int fakeIndex2 = com.badlogic.gdx.math.MathUtils.random(0, 9);
            while (fakeIndex2 == fakeIndex1) {
                fakeIndex2 = com.badlogic.gdx.math.MathUtils.random(0, 9);
            }
            int fakeIndex3 = com.badlogic.gdx.math.MathUtils.random(0, 9);
            while (fakeIndex3 == fakeIndex1 || fakeIndex3 == fakeIndex2) {
                fakeIndex3 = com.badlogic.gdx.math.MathUtils.random(0, 9);
            }
            
            float blockHeight = arenaSize / 10f;
            float blockWidth = 50f; 
            float startX = arenaX; 
            float speedX = 200f; 
            
            for (int i = 0; i < 10; i++) {
                boolean isFake = (i == fakeIndex1 || i == fakeIndex2 || i == fakeIndex3);
                float y = arenaY + (i * blockHeight);
                
                com.fernanda.frontend.entities.projectiles.BaseProjectile tsunami = 
                    com.fernanda.frontend.factory.ProjectileFactory.obtainTsunami(startX, y, blockWidth, blockHeight, speedX, isFake);
                boss.getProjectiles().add(tsunami);
            }
            
            waveCount++;
            nextSpawnTime += 1.5f; 
        }
        
        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
