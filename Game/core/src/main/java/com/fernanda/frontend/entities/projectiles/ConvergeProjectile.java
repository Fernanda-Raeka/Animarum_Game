package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.fernanda.frontend.entities.Player;
import com.fernanda.frontend.entities.SharedStats;

public class ConvergeProjectile extends BaseProjectile {
    private float focalX, focalY;
    private float targetRadius;
    private float speed;
    private Circle collisionCircle;

    public ConvergeProjectile() {
        super();
        this.width = 24f;
        this.height = 24f;
        this.collisionCircle = new Circle();
    }

    public void initConverge(float startX, float startY, float focalX, float focalY, float targetRadius, float speedX, float speedY) {
        super.init(startX, startY, speedX, speedY);
        this.focalX = focalX;
        this.focalY = focalY;
        this.targetRadius = targetRadius;
        this.speed = (float) Math.sqrt(speedX * speedX + speedY * speedY);
        this.collisionCircle.set(startX, startY, width / 2f);
    }

    @Override
    protected void updateBounds() {
        super.updateBounds();
        if (collisionCircle != null) {
            collisionCircle.set(x, y, width / 2f);
        }
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        this.timer += delta;
        
        float dst = Vector2.dst(x, y, focalX, focalY);
        if (dst <= targetRadius || this.timer >= 2.0f) {
            this.active = false;
            return;
        }

        x += speedX * delta;
        y += speedY * delta;
        
        updateBounds();
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;
        
        float alpha = 1.0f;
        if (this.timer > 1.5f) {
            alpha = 1.0f - ((this.timer - 1.5f) / 0.5f);
            if (alpha < 0) alpha = 0;
        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setColor(new Color(1f, 0f, 0f, alpha));
        shapeRenderer.circle(x, y, width / 2f);
    }

    @Override
    public void checkCollision(Player player, SharedStats stats) {
        if (!active || player.isInvincible()) return;

        if (Intersector.overlaps(player.getBounds(), collisionCircle)) {
            System.out.println("Sistem: Player terkena Dinding Converging!");
            stats.takeDamage(10f);
            this.active = false;
            player.triggerInvincibility();
        }
    }
}
