package com.fernanda.frontend.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimaSpark implements Poolable {
    private float x, y;
    private final float radius = 20f;
    private float timer;
    private final float maxDuration = 8f;

    private float captureProgress = 0f;
    private final float captureRequired = 3f;

    private boolean isCollected = false;
    private boolean isDuoType;
    private Circle bounds;

    public AnimaSpark() {}

    public void init(float arenaX, float arenaY, float arenaSize, boolean forceDuo) {
        this.x = MathUtils.random(arenaX + radius, arenaX + arenaSize - radius);
        this.y = MathUtils.random(arenaY + radius, arenaY + arenaSize - radius);
        this.timer = maxDuration;
        this.captureProgress = 0f;
        this.isCollected = false;
        if (this.bounds == null) {
            this.bounds = new Circle(x, y, radius);
        } else {
            this.bounds.set(x, y, radius);
        }
        this.isDuoType = forceDuo;
    }

    @Override
    public void reset() {
        this.timer = maxDuration;
        this.captureProgress = 0f;
        this.isCollected = false;
    }

    public void update(float delta, boolean p1Inside, boolean p2Inside) {
        timer -= delta;

        boolean beingCaptured;
        if (isDuoType) {
            beingCaptured = p1Inside && p2Inside;
        } else {
            beingCaptured = p1Inside || p2Inside;
        }

        if (beingCaptured) {
            captureProgress += delta;
            if (captureProgress >= captureRequired) {
                isCollected = true;
            }
        } else {
            captureProgress -= delta * 0.3f;
            if (captureProgress < 0) captureProgress = 0;
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        float alpha = timer / maxDuration;

        if (isDuoType) {
            shapeRenderer.setColor(new Color(1, 0, 1, alpha));
        } else {
            shapeRenderer.setColor(new Color(1, 1, 0, alpha));
        }
        shapeRenderer.circle(x, y, radius);

        if (captureProgress > 0) {
            shapeRenderer.end();
            com.badlogic.gdx.Gdx.gl.glLineWidth(4f); 
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);
            
            float percentage = captureProgress / captureRequired;
            float degrees = percentage * 360f;
            int segments = Math.max(1, (int)(40 * percentage));
            float step = degrees / segments;
            float r = radius + 6f; 
            
            for (int i = 0; i < segments; i++) {
                float a1 = 90f - (i * step);
                float a2 = 90f - ((i + 1) * step);
                float x1 = x + MathUtils.cosDeg(a1) * r;
                float y1 = y + MathUtils.sinDeg(a1) * r;
                float x2 = x + MathUtils.cosDeg(a2) * r;
                float y2 = y + MathUtils.sinDeg(a2) * r;
                shapeRenderer.line(x1, y1, x2, y2);
            }
            
            shapeRenderer.end();
            com.badlogic.gdx.Gdx.gl.glLineWidth(1f); 
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        }
    }

    public boolean isExpired() { return timer <= 0; }
    public boolean isCollected() { return isCollected; }
    public Circle getBounds() { return bounds; }
}
