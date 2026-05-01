package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.math.Rectangle;

public abstract class BaseProjectile implements Poolable {
    public float x, y;
    public float width, height;
    public float speedX, speedY;
    public float timer;
    public boolean active;
    protected Rectangle bounds;

    public BaseProjectile() {
        bounds = new Rectangle();
        active = false;
    }

    public void init(float x, float y, float speedX, float speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.timer = 0f;
        this.active = true;
        updateBounds();
    }

    protected void updateBounds() {
        bounds.set(x, y, width, height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public abstract void update(float delta);
    public abstract void render(ShapeRenderer shapeRenderer);

    public void checkCollision(com.fernanda.frontend.entities.Player player, com.fernanda.frontend.entities.SharedStats stats) {
        if (!active || player.isInvincible()) return;
        
        if (com.badlogic.gdx.math.Intersector.overlaps(player.getBounds(), getBounds())) {
            System.out.println("Sistem: Player terkena proyektil!");
            stats.takeDamage(10f); // Default damage
            this.active = false;   // Default hancur setelah menabrak
            player.triggerInvincibility();
        }
    }

    @Override
    public void reset() {
        this.x = 0;
        this.y = 0;
        this.speedX = 0;
        this.speedY = 0;
        this.timer = 0f;
        this.active = false;
        this.bounds.set(0,0,0,0);
    }
}
