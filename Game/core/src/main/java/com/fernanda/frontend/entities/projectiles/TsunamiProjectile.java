package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TsunamiProjectile extends BaseProjectile {

    private boolean isFake;

    public TsunamiProjectile() {
        super();
    }

    public void initTsunami(float x, float y, float width, float height, float speedX, boolean isFake) {
        this.width = width;
        this.height = height;
        this.isFake = isFake;
        super.init(x, y, speedX, 0);
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        super.update(delta);

        if (timer > 3f) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;

        if (isFake) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setColor(0.2f, 0.5f, 0.8f, 0.6f);
            shapeRenderer.rect(x, y, width, height);
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            shapeRenderer.setColor(0f, 1f, 1f, 1f);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    @Override
    public void checkCollision(com.fernanda.frontend.entities.Player player, com.fernanda.frontend.entities.SharedStats stats) {
        if (isFake || !active || player.isInvincible()) return;

        if (com.badlogic.gdx.math.Intersector.overlaps(player.getBounds(), getBounds())) {
            System.out.println("Sistem: Player tergiling TSUNAMI ASLI!");
            stats.takeDamage(15f);
            player.triggerInvincibility();
        }
    }
}
