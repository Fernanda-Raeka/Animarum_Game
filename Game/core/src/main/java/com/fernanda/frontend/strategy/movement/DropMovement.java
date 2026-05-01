package com.fernanda.frontend.strategy.movement;

import com.fernanda.frontend.entities.projectiles.BaseProjectile;

public class DropMovement implements MovementStrategy {
    private float speedY;

    public void init(float speedY) {
        this.speedY = speedY;
    }

    @Override
    public void update(BaseProjectile projectile, float delta) {
        projectile.y += speedY * delta;
    }

    @Override
    public void reset() {
        speedY = 0f;
    }
}
