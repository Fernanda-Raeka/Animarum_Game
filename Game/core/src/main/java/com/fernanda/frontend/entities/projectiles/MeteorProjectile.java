package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MeteorProjectile extends BaseProjectile {

    public MeteorProjectile() {
        super();
        this.width = 60f; // Meteor besar
        this.height = 120f;
    }

    @Override
    public void update(float delta) {
        if (!active)
            return;

        timer += delta;
        x += speedX * delta;
        y += speedY * delta;

        updateBounds();

        // Meteor mati jika umurnya lebih dari 5 detik (keluar layar bawah)
        if (timer > 1.5f) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active)
            return;
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(x, y, width, height);
    }
}
