package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
        super.init(x, y, speedX, 0); // Tsunami bergerak lurus secara horizontal
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        
        x += speedX * delta;
        updateBounds();
        timer += delta;
        
        // Umur peluru agar tidak bocor (Misal setelah 8 detik otomatis hilang)
        if (timer > 3f) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;
        
        if (isFake) {
            // Gambar Ilusi Transparan (Dibuat lebih terlihat agar pemain tidak kebingungan)
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            // Warna Biru Gelap/Pucat (0.2, 0.5, 0.8) dengan transparansi 0.6 agar lebih kontras
            shapeRenderer.setColor(0.2f, 0.5f, 0.8f, 0.6f); 
            shapeRenderer.rect(x, y, width, height);
            Gdx.gl.glDisable(GL20.GL_BLEND);
        } else {
            // Gambar Tsunami Asli (Cyan Terang Menyala)
            shapeRenderer.setColor(0f, 1f, 1f, 1f); 
            shapeRenderer.rect(x, y, width, height);
        }
    }

    @Override
    public void checkCollision(com.fernanda.frontend.entities.Player player, com.fernanda.frontend.entities.SharedStats stats) {
        // Jika peluru palsu, otomatis aman (menembus tanpa damage)
        if (isFake) return;
        
        // Jika peluru asli
        if (!active || player.isInvincible()) return;
        
        if (com.badlogic.gdx.math.Intersector.overlaps(player.getBounds(), getBounds())) {
            System.out.println("Sistem: Player tergiling TSUNAMI ASLI!");
            stats.takeDamage(15f); // Damage mematikan
            // Sengaja TIDAK menset active = false agar tsunami terus berjalan menyapu
            player.triggerInvincibility();
        }
    }
}
