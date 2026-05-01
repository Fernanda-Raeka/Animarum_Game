package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;

public class MagicianAttackFireball implements BossState {
    
    private float attackTimer;
    private float spawnTimer;
    private float globalAngleOffset = 0f;
    private final float ATTACK_DURATION = 5f;
    private final float SPAWN_INTERVAL = 0.6f;

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.BOTTOM);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.RED); 
            System.out.println("Sistem: THE MAGICIAN menggunakan FIREBALL dari arah " + mBoss.getCurrentSide() + "!");
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        spawnTimer += delta;
        globalAngleOffset += delta * 60f; 

        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.updatePosition(arenaX, arenaY, arenaSize);

            if (spawnTimer >= SPAWN_INTERVAL) {
                spawnTimer = 0f;
                
                float startX = mBoss.getX() + mBoss.getWidth() / 2f;
                float startY = mBoss.getY() + mBoss.getHeight(); 
                
                int numFireballs = 10;
                float baseAngle = 90f + com.badlogic.gdx.math.MathUtils.sinDeg(globalAngleOffset) * 20f; 
                float spreadAngle = 140f; 
                
                float startAngle = baseAngle - (spreadAngle / 2f);
                float angleStep = spreadAngle / (numFireballs - 1);
                
                for (int i = 0; i < numFireballs; i++) {
                    float angleDeg = startAngle + (i * angleStep);
                    float speed = 250f;
                    float speedX = com.badlogic.gdx.math.MathUtils.cosDeg(angleDeg) * speed;
                    float speedY = com.badlogic.gdx.math.MathUtils.sinDeg(angleDeg) * speed;
                    
                    com.fernanda.frontend.entities.projectiles.BaseProjectile fireball = 
                        com.fernanda.frontend.factory.ProjectileFactory.obtainFireball(startX - 10f, startY, speedX, speedY);
                    boss.getProjectiles().add(fireball);
                }
            }
        }
        
        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
