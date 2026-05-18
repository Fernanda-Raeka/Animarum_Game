package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.fernanda.frontend.entities.Player;
import com.fernanda.frontend.entities.SharedStats;

public class MineProjectile extends BaseProjectile {
    private float warningDuration;
    private float explosionDuration;
    private Circle collisionCircle;

    public MineProjectile() {
        super();
        this.collisionCircle = new Circle();
    }

    public void initMine(float x, float y, float radius, float warningDuration, float explosionDuration) {
        super.init(x, y, 0, 0);
        this.width = radius * 2;
        this.height = radius * 2;
        this.warningDuration = warningDuration;
        this.explosionDuration = explosionDuration;
        this.collisionCircle.set(x, y, radius);
    }

    @Override
    protected void updateBounds() {
        super.updateBounds();
        if (collisionCircle != null) {
            collisionCircle.set(x, y, width / 2); 
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        if (this.timer >= (warningDuration + explosionDuration)) {
            this.active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;

        float radius = width / 2;

        if (this.timer < warningDuration) {
            float blinkSpeed = 10f; 
            float alpha = (float) (Math.sin(this.timer * blinkSpeed) + 1) / 2f; 
            
            shapeRenderer.setColor(new Color(1f, 0.5f, 0f, 0.3f + alpha * 0.7f));
            shapeRenderer.circle(x, y, radius);
            
            shapeRenderer.end();
            com.badlogic.gdx.Gdx.gl.glLineWidth(2f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.circle(x, y, radius);
            shapeRenderer.end();
            com.badlogic.gdx.Gdx.gl.glLineWidth(1f);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        } else {
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(x, y, radius);
        }
    }

    @Override
    public void checkCollision(Player player, SharedStats stats) {
        if (!active || player.isInvincible()) return;

        if (this.timer >= warningDuration && this.timer < (warningDuration + explosionDuration)) {
            if (Intersector.overlaps(collisionCircle, player.getBounds())) {
                System.out.println("Sistem: Player terkena ledakan ranjau!");
                stats.takeDamage(10f);
                player.triggerInvincibility();
            }
        }
    }
}
