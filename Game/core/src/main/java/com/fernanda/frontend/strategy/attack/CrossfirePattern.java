package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.factory.ProjectileFactory;

public class CrossfirePattern implements AttackPattern {
    private float fireballSpeed = 600f; 

    @Override
    public void execute(BaseBoss boss, float x, float y, float size, float... optional) {
        if (!(boss instanceof LoversBoss)) return;
        LoversBoss lovers = (LoversBoss) boss;

        float minY = lovers.getBoundsPlayer1().y;
        float maxY = lovers.getBoundsPlayer1().y + lovers.getBoundsPlayer1().height - 25f; 

        float spawnYLeft = MathUtils.random(minY, maxY);
        float spawnYRight = MathUtils.random(minY, maxY);

        float speedY = 0f;

        float spawnXLeft = -50f;
        float speedXLeft = fireballSpeed;

        float spawnXRight = Gdx.graphics.getWidth() + 50f;
        float speedXRight = -fireballSpeed;

        boss.getProjectiles().add(
            ProjectileFactory.obtainFireball(spawnXLeft, spawnYLeft, speedXLeft, speedY)
        );
        boss.getProjectiles().add(
            ProjectileFactory.obtainFireball(spawnXRight, spawnYRight, speedXRight, speedY)
        );
    }
}