package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.entities.projectiles.ConvergeProjectile;
import com.fernanda.frontend.factory.ProjectileFactory;

public class ConvergingEmbracePattern implements AttackPattern {
    
    @Override
    public void execute(BaseBoss boss, float arenaX, float arenaY, float arenaSize, float... optional) {
        if (!(boss instanceof LoversBoss)) return;
        
        float focalX = MathUtils.random(arenaX + 50f, arenaX + arenaSize - 50f);
        float focalY = MathUtils.random(arenaY + 50f, arenaY + arenaSize - 50f);
        
        float spawnRadius = 800f; 
        float safeRadius = 50f;
        float travelTime = 2.0f;
        float distanceToTravel = spawnRadius - safeRadius;
        float speed = distanceToTravel / travelTime;
        
        int projectileCount = 36; 
        
        for (int i = 0; i < projectileCount; i++) {
            float angle = (360f / projectileCount) * i;
            
            float spawnX = focalX + MathUtils.cosDeg(angle) * spawnRadius;
            float spawnY = focalY + MathUtils.sinDeg(angle) * spawnRadius;
            
            float speedX = MathUtils.cosDeg(angle + 180f) * speed;
            float speedY = MathUtils.sinDeg(angle + 180f) * speed;
            
            ConvergeProjectile proj = ProjectileFactory.obtainConverge(spawnX, spawnY, focalX, focalY, safeRadius, speedX, speedY);
            boss.getProjectiles().add(proj);
        }
        
        System.out.println("Sistem: The Lovers mengeksekusi Converging Embrace menuju (" + focalX + ", " + focalY + ")");
    }
}
