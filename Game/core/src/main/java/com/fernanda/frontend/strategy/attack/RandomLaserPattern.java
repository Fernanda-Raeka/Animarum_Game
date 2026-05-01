package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.factory.ProjectileFactory;

public class RandomLaserPattern implements AttackPattern {
    private float chargeDuration;

    public RandomLaserPattern(float chargeDuration) {
        this.chargeDuration = chargeDuration;
    }

    @Override
    public void execute(BaseBoss boss, float arenaX, float arenaY, float arenaSize, float... optional) {
        int randomType = MathUtils.random(1, 4);
        boss.getProjectiles().add(
            ProjectileFactory.obtainLaser(arenaX, arenaY, arenaSize, randomType, chargeDuration)
        );
    }
}
