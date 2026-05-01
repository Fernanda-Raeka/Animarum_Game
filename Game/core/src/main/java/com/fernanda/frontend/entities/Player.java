package com.fernanda.frontend.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Player {
    private float x, y;
    private final float radius = 15f;
    private final float speed = 300f;
    private Color color;
    private final int keyUp, keyDown, keyLeft, keyRight;
    private SharedStats sharedStats;
    private float invincibilityTimer = 0f;

    public Player(float startX, float startY, Color color, int up, int down, int left, int right, SharedStats sharedStats) {
        this.x = startX;
        this.y = startY;
        this.color = color;
        this.keyUp = up;
        this.keyDown = down;
        this.keyLeft = left;
        this.keyRight = right;
        this.sharedStats = sharedStats;
    }

    public void update(float delta, float arenaX, float arenaY, float arenaSize) {
        if (invincibilityTimer > 0) {
            invincibilityTimer -= delta;
        }

        if (Gdx.input.isKeyPressed(keyUp)) y += speed * delta;
        if (Gdx.input.isKeyPressed(keyDown)) y -= speed * delta;
        if (Gdx.input.isKeyPressed(keyLeft)) x -= speed * delta;
        if (Gdx.input.isKeyPressed(keyRight)) x += speed * delta;

        if (x - radius < arenaX) x = arenaX + radius;
        if (x + radius > arenaX + arenaSize) x = arenaX + arenaSize - radius;
        if (y - radius < arenaY) y = arenaY + radius;
        if (y + radius > arenaY + arenaSize) y = arenaY + arenaSize - radius;
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
