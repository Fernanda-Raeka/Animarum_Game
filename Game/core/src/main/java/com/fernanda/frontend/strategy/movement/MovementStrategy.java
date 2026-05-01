package com.fernanda.frontend.strategy.movement;

import com.fernanda.frontend.entities.projectiles.BaseProjectile;

public interface MovementStrategy {
    void update(BaseProjectile projectile, float delta);
    void reset(); 
}
