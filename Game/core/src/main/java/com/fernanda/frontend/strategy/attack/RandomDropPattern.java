package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.factory.ProjectileFactory;

public class RandomDropPattern implements AttackPattern {
    private float speedY;

    public RandomDropPattern(float speedY) {
        this.speedY = speedY;
    }

    @Override
    public void execute(BaseBoss boss, float arenaX, float arenaY, float arenaSize, float... optional) {
        float meteorX = MathUtils.random(arenaX, arenaX + arenaSize - 60f);
        float meteorY = arenaY + arenaSize;

        boss.getProjectiles().add(
            ProjectileFactory.obtainMeteor(meteorX, meteorY, speedY)
        );
    }
}
