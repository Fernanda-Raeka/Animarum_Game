package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FireballProjectile extends BaseProjectile {
    
    public FireballProjectile() {
        super();
        this.width = 25f;
        this.height = 25f;
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        
        timer += delta;
        x += speedX * delta;
        y += speedY * delta;
        
        updateBounds();
        
        // Mati setelah 6 detik jika tidak menabrak apa-apa
        if (timer > 2.5f) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(x + width/2f, y + height/2f, width/2f); // Berbentuk lingkaran
    }
}
