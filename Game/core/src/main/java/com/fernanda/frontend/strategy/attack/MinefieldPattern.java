package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.entities.projectiles.MineProjectile;
import com.fernanda.frontend.factory.ProjectileFactory;

public class MinefieldPattern implements AttackPattern {
    
    @Override
    public void execute(BaseBoss boss, float arenaX, float arenaY, float arenaSize, float... optional) {
        if (!(boss instanceof LoversBoss)) return;
        
        LoversBoss lovers = (LoversBoss) boss;
        float p1X = lovers.getBoundsPlayer1().x;
        float p1Y = lovers.getBoundsPlayer1().y;
        float p1Width = lovers.getBoundsPlayer1().width;
        float p1Height = lovers.getBoundsPlayer1().height;
        
        float p2X = lovers.getBoundsPlayer2().x;
        float p2Y = lovers.getBoundsPlayer2().y;
        float p2Width = lovers.getBoundsPlayer2().width;
        float p2Height = lovers.getBoundsPlayer2().height;
        
        int minesPerCorridor = MathUtils.random(5, 8);
        float radius = 35f;
        float warningDuration = 1.5f;
        float explosionDuration = 0.3f;
        
        com.badlogic.gdx.utils.Array<com.badlogic.gdx.math.Vector2> spawnedPositions = new com.badlogic.gdx.utils.Array<>();
        
        java.util.function.BiFunction<Float, Float, Boolean> isOverlapping = (x, y) -> {
            for (com.badlogic.gdx.math.Vector2 pos : spawnedPositions) {
                if (pos.dst(x, y) < radius * 2.5f) { 
                    return true;
                }
            }
            return false;
        };

        for (int i = 0; i < minesPerCorridor; i++) {
            float mineX = 0, mineY = 0;
            boolean valid = false;
            for (int attempt = 0; attempt < 50; attempt++) {
                mineX = MathUtils.random(p1X + radius, p1X + p1Width - radius);
                mineY = MathUtils.random(p1Y + radius, p1Y + p1Height - radius);
                if (!isOverlapping.apply(mineX, mineY)) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                spawnedPositions.add(new com.badlogic.gdx.math.Vector2(mineX, mineY));
                MineProjectile mine = ProjectileFactory.obtainMine(mineX, mineY, radius, warningDuration, explosionDuration);
                boss.getProjectiles().add(mine);
            }
        }
        
        for (int i = 0; i < minesPerCorridor; i++) {
            float mineX = 0, mineY = 0;
            boolean valid = false;
            for (int attempt = 0; attempt < 50; attempt++) {
                mineX = MathUtils.random(p2X + radius, p2X + p2Width - radius);
                mineY = MathUtils.random(p2Y + radius, p2Y + p2Height - radius);
                if (!isOverlapping.apply(mineX, mineY)) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                spawnedPositions.add(new com.badlogic.gdx.math.Vector2(mineX, mineY));
                MineProjectile mine = ProjectileFactory.obtainMine(mineX, mineY, radius, warningDuration, explosionDuration);
                boss.getProjectiles().add(mine);
            }
        }
        
        System.out.println("Sistem: The Lovers menebar ranjau darat tanpa overlap!");
    }
}