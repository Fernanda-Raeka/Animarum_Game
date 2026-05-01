package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MeteorProjectile extends BaseProjectile {

    public MeteorProjectile() {
        super();
        this.width = 60f;
        this.height = 120f;
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        super.update(delta);

        if (timer > 1.5f) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(x, y, width, height);
    }
}
