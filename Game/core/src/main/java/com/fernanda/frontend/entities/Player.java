package com.fernanda.frontend.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float x, y;
    private final float radius = 15f;
    private final float speed = 300f;
    private Color color;
    private final int keyUp, keyDown, keyLeft, keyRight;
    private SharedStats sharedStats;
    private float invincibilityTimer = 0f;
    private int id;
    
    private Rectangle currentBounds = new Rectangle();

    public Player(int id, float startX, float startY, Color color, int up, int down, int left, int right, SharedStats sharedStats) {
        this.id = id;
        this.x = startX;
        this.y = startY;
        this.color = color;
        this.keyUp = up;
        this.keyDown = down;
        this.keyLeft = left;
        this.keyRight = right;
        this.sharedStats = sharedStats;
    }

    public void update(float delta, Rectangle bounds) {
        if (currentBounds.width != 0 && (currentBounds.x != bounds.x || currentBounds.y != bounds.y || currentBounds.width != bounds.width || currentBounds.height != bounds.height)) {
            if (bounds.width == 500f && bounds.height == 500f) {
                this.x = bounds.x + (id == 1 ? 150f : 350f);
                this.y = bounds.y + 250f;
            } else {
                this.x = bounds.x + bounds.width / 2f;
                this.y = bounds.y + bounds.height / 2f;
            }
        }
        currentBounds.set(bounds);

        if (invincibilityTimer > 0) {
            invincibilityTimer -= delta;
        }

        if (Gdx.input.isKeyPressed(keyUp)) y += speed * delta;
        if (Gdx.input.isKeyPressed(keyDown)) y -= speed * delta;
        if (Gdx.input.isKeyPressed(keyLeft)) x -= speed * delta;
        if (Gdx.input.isKeyPressed(keyRight)) x += speed * delta;

        if (x - radius < bounds.x) x = bounds.x + radius;
        if (x + radius > bounds.x + bounds.width) x = bounds.x + bounds.width - radius;
        if (y - radius < bounds.y) y = bounds.y + radius;
        if (y + radius > bounds.y + bounds.height) y = bounds.y + bounds.height - radius;
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (isInvincible() && (int)(invincibilityTimer * 15) % 2 == 0) {
            return; 
        }
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
    }

    public boolean isInvincible() {
        return invincibilityTimer > 0;
    }

    public void triggerInvincibility() {
        invincibilityTimer = 1.0f; 
    }

    public Circle getBounds() {
        return new Circle(x, y, radius);
    }
}