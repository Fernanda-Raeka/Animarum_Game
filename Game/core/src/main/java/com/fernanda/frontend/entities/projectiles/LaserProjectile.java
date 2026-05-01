package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LaserProjectile extends BaseProjectile {
    private int type;
    private float chargeDuration;
    private float flashDuration = 0.2f;
    
    // Posisi peringatan (Sisi Kanan / Atas)
    private float warnX, warnY, warnW, warnH;
    // Posisi peringatan (Sisi Kiri / Bawah berseberangan)
    private float warn2X, warn2Y, warn2W, warn2H;

    public LaserProjectile() {
        super();
    }

    public void initLaser(float arenaX, float arenaY, float arenaSize, int type, float chargeDuration) {
        this.type = type;
        this.chargeDuration = chargeDuration;
        
        super.init(0, 0, 0, 0); // Tidak bergerak
        
        float twoThirds = arenaSize * 2f / 3f;
        float gap = 20f; // Jarak warning di luar arena
        
        if (type == 1) { // Top 2/3
            this.x = arenaX;
            this.y = arenaY + (arenaSize / 3f);
            this.width = arenaSize;
            this.height = twoThirds;
            
            warnX = arenaX + arenaSize + gap; // Di luar kanan
            warnY = this.y;
            warnW = 5f;
            warnH = this.height;
            
            warn2X = arenaX - gap; // Di luar kiri
            warn2Y = this.y;
            warn2W = 5f;
            warn2H = this.height;
        } else if (type == 2) { // Bottom 2/3
            this.x = arenaX;
            this.y = arenaY;
            this.width = arenaSize;
            this.height = twoThirds;
            
            warnX = arenaX + arenaSize + gap; // Di luar kanan
            warnY = this.y;
            warnW = 5f;
            warnH = this.height;
            
            warn2X = arenaX - gap; // Di luar kiri
            warn2Y = this.y;
            warn2W = 5f;
            warn2H = this.height;
        } else if (type == 3) { // Right 2/3
            this.x = arenaX + (arenaSize / 3f);
            this.y = arenaY;
            this.width = twoThirds;
            this.height = arenaSize;
            
            warnX = this.x;
            warnY = arenaY + arenaSize + gap; // Di luar atas
            warnW = this.width;
            warnH = 5f;
            
            warn2X = this.x;
            warn2Y = arenaY - gap; // Di luar bawah
            warn2W = this.width;
            warn2H = 5f;
        } else if (type == 4) { // Left 2/3
            this.x = arenaX;
            this.y = arenaY;
            this.width = twoThirds;
            this.height = arenaSize;
            
            warnX = this.x;
            warnY = arenaY + arenaSize + gap; // Di luar atas
            warnW = this.width;
            warnH = 5f;
            
            warn2X = this.x;
            warn2Y = arenaY - gap; // Di luar bawah
            warn2W = this.width;
            warn2H = 5f;
        }
        
        updateBounds();
    }
    
    public boolean isFlashing() {
        return timer >= chargeDuration && timer < chargeDuration + flashDuration;
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        timer += delta;
        
        if (timer >= chargeDuration + flashDuration) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;
        
        if (timer < chargeDuration) {
            // FASE CHARGE: Gambar garis peringatan
            float progress = timer / chargeDuration;
            shapeRenderer.setColor(Color.YELLOW);
            
            if (type == 1 || type == 2) {
                float currentWarnW = warnW + (15f * progress);
                shapeRenderer.rect(warnX, warnY, currentWarnW, warnH); // Tumbuh ke Kanan
                shapeRenderer.rect(warn2X - currentWarnW, warn2Y, currentWarnW, warn2H); // Tumbuh ke Kiri
            } else {
                float currentWarnH = warnH + (15f * progress);
                shapeRenderer.rect(warnX, warnY, warnW, currentWarnH); // Tumbuh ke Atas
                shapeRenderer.rect(warn2X, warn2Y - currentWarnH, warn2W, currentWarnH); // Tumbuh ke Bawah
            }
        } else if (isFlashing()) {
            // FASE FLASH: Gambar Laser Padat
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    @Override
    public void checkCollision(com.fernanda.frontend.entities.Player player, com.fernanda.frontend.entities.SharedStats stats) {
        if (!active || !isFlashing() || player.isInvincible()) return;
        
        if (com.badlogic.gdx.math.Intersector.overlaps(player.getBounds(), getBounds())) {
            System.out.println("Sistem: Player terkena Area Denial Laser!");
            stats.takeDamage(15f); // Damage lebih besar
            // Laser tidak diset active = false karena menembus badan
            player.triggerInvincibility();
        }
    }

    @Override
    public void reset() {
        super.reset();
    }
}
