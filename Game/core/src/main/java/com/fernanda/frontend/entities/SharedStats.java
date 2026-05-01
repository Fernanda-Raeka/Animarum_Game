package com.fernanda.frontend.entities;

import com.fernanda.frontend.observer.GameObserver;
import com.badlogic.gdx.utils.Array;

public class SharedStats {
    private float hp;
    private final float maxHp;
    private final Array<GameObserver> observers;

    public SharedStats(float maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
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
            observer.onPlayerHPChanged(hp, maxHp);
        }
    }

    public void takeDamage(float amount) {
        hp -= amount;
        if (hp < 0)
            hp = 0;
        notifyObservers();
    }

    public void heal(float amount) {
        hp += amount;
        if (hp > maxHp)
            hp = maxHp;
        notifyObservers();
    }

    public float getHp() {
        return hp;
    }

    public float getMaxHp() {
        return maxHp;
    }
}
