package com.fernanda.frontend.strategy.attack;

import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.factory.ProjectileFactory;

public class TsunamiWavePattern implements AttackPattern {
    @Override
    public void execute(BaseBoss boss, float arenaX, float arenaY, float arenaSize, float... optional) {
        int fake1 = MathUtils.random(0, 9);
        int fake2 = MathUtils.random(0, 9);
        while (fake2 == fake1) fake2 = MathUtils.random(0, 9);
        int fake3 = MathUtils.random(0, 9);
        while (fake3 == fake1 || fake3 == fake2) fake3 = MathUtils.random(0, 9);

        float blockHeight = arenaSize / 10f;
        float blockWidth = 50f;
        float speedX = 200f;

        for (int i = 0; i < 10; i++) {
            boolean isFake = (i == fake1 || i == fake2 || i == fake3);
            float y = arenaY + (i * blockHeight);

            boss.getProjectiles().add(
                ProjectileFactory.obtainTsunami(arenaX, y, blockWidth, blockHeight, speedX, isFake)
            );
        }
    }
}
