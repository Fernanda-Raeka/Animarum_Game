package com.fernanda.frontend.pool;

import com.badlogic.gdx.utils.Pool;
import com.fernanda.frontend.entities.AnimaSpark;

public class SparkPool extends Pool<AnimaSpark> {
    
    public SparkPool() {
        super(10, 50);
    }

    @Override
    protected AnimaSpark newObject() {
        return new AnimaSpark();
    }
}
