package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.factory.ProjectileFactory;

public class FanSpreadPattern implements AttackPattern {
    private int numProjectiles;
    private float spreadAngle;
    private float speed;

    public FanSpreadPattern(int numProjectiles, float spreadAngle, float speed) {
        this.numProjectiles = numProjectiles;
        this.spreadAngle = spreadAngle;
        this.speed = speed;
    }

    @Override
    public void execute(BaseBoss boss, float startX, float startY, float size, float... optional) {
        float globalAngleOffset = optional.length > 0 ? optional[0] : 0f;
        float baseAngle = 90f + MathUtils.sinDeg(globalAngleOffset) * 20f;
        float startAngle = baseAngle - (spreadAngle / 2f);
        float angleStep = spreadAngle / (numProjectiles - 1);

        for (int i = 0; i < numProjectiles; i++) {
            float angleDeg = startAngle + (i * angleStep);
            float speedX = MathUtils.cosDeg(angleDeg) * speed;
            float speedY = MathUtils.sinDeg(angleDeg) * speed;

            boss.getProjectiles().add(
                ProjectileFactory.obtainFireball(startX - 10f, startY, speedX, speedY)
            );
        }
    }
}
