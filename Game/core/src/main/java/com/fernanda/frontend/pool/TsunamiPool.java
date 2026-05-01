package com.fernanda.frontend.pool;

import com.badlogic.gdx.utils.Pool;
import com.fernanda.frontend.entities.projectiles.TsunamiProjectile;

public class TsunamiPool extends Pool<TsunamiProjectile> {
    
    public TsunamiPool() {
        super(40, 100); 
    }

    @Override
    protected TsunamiProjectile newObject() {
        return new TsunamiProjectile();
    }
}
