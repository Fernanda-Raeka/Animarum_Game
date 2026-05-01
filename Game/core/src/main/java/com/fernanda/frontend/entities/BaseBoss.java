package com.fernanda.frontend.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.projectiles.BaseProjectile;
import com.fernanda.frontend.observer.GameObserver;
import com.fernanda.frontend.state.BossState;

public abstract class BaseBoss {
    protected float x, y;
    protected float width, height;
    protected float hp, maxHp;
    protected String name;
    
    private BossState currentState;
    private final Array<GameObserver> observers;
    protected Array<BaseProjectile> projectiles = new Array<>();

    public BaseBoss(float x, float y, float width, float height, float maxHp, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.name = name;
        this.observers = new Array<>();
    }

    public void addObserver(GameObserver observer) {
        if (!observers.contains(observer, true)) {
            observers.add(observer);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.removeValue(observer, true);
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onBossHPChanged(hp, maxHp);
        }
    }

    public void takeDamage(float amount) {
        if (hp <= 0) return;

        hp -= amount;
        if (hp <= 0) {
            hp = 0;
            notifyObservers();
            onDeath(); 
        } else {
            notifyObservers();
        }
    }

    protected abstract void onDeath();

    public void changeState(BossState newState) {
        if (currentState != null) {
            currentState.exit(this);
        }
        currentState = newState;
        if (currentState != null) {
            currentState.enter(this);
        }
    }

    public void update(float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        if (currentState != null) {
            currentState.update(this, delta, arenaX, arenaY, arenaSize, sparks);
        }
    }

    public abstract void render(ShapeRenderer shapeRenderer);

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public String getName() { return name; }
    public Array<BaseProjectile> getProjectiles() { return projectiles; }
}
