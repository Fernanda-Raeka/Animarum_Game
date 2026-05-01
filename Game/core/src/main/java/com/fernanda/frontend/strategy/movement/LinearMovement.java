package com.fernanda.frontend.strategy.movement;

import com.fernanda.frontend.entities.projectiles.BaseProjectile;

public class LinearMovement implements MovementStrategy {
    private float speedX;
    private float speedY;

    public void init(float speedX, float speedY) {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    @Override
    public void update(BaseProjectile projectile, float delta) {
        projectile.x += speedX * delta;
        projectile.y += speedY * delta;
    }

    @Override
    public void reset() {
        speedX = 0f;
        speedY = 0f;
    }
}
