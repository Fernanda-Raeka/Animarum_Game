package com.fernanda.frontend.pool;

import com.badlogic.gdx.utils.Pool;
import com.fernanda.frontend.entities.projectiles.LaserProjectile;

public class LaserPool extends Pool<LaserProjectile> {
    
    public LaserPool() {
        super(10, 20); 
    }

    @Override
    protected LaserProjectile newObject() {
        return new LaserProjectile();
    }
}
