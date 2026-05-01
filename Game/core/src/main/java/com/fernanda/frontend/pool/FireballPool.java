package com.fernanda.frontend.pool;

import com.badlogic.gdx.utils.Pool;
import com.fernanda.frontend.entities.projectiles.FireballProjectile;

public class FireballPool extends Pool<FireballProjectile> {
    
    public FireballPool() {
        super(100, 500); 
    }

    @Override
    protected FireballProjectile newObject() {
        return new FireballProjectile();
    }
}
