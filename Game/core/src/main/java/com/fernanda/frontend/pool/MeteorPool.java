package com.fernanda.frontend.pool;

import com.badlogic.gdx.utils.Pool;
import com.fernanda.frontend.entities.projectiles.MeteorProjectile;

public class MeteorPool extends Pool<MeteorProjectile> {
    
    public MeteorPool() {
        super(50, 200); 
    }

    @Override
    protected MeteorProjectile newObject() {
        return new MeteorProjectile(); 
    }
}
