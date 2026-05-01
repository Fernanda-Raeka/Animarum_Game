package com.fernanda.frontend.pool;

import com.badlogic.gdx.utils.Pool;

public class GenericPool<T> extends Pool<T> {

    public interface PoolObjectCreator<T> {
        T create();
    }

    private final PoolObjectCreator<T> creator;

    public GenericPool(PoolObjectCreator<T> creator) {
        super();
        this.creator = creator;
    }

    public GenericPool(int initialCapacity, int max, PoolObjectCreator<T> creator) {
        super(initialCapacity, max);
        this.creator = creator;
    }

    @Override
    protected T newObject() {
        return creator.create();
    }
}
