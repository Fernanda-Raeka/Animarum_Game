package com.fernanda.frontend.entities.projectiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LaserProjectile extends BaseProjectile {
    private int type;
    private float chargeDuration;
    private float flashDuration = 0.2f;

    private float warnX, warnY, warnW, warnH;
    private float warn2X, warn2Y, warn2W, warn2H;

    public LaserProjectile() {
        super();
    }

    public void initLaser(float arenaX, float arenaY, float arenaSize, int type, float chargeDuration) {
        this.type = type;
        this.chargeDuration = chargeDuration;

        super.init(0, 0, 0, 0);

        float twoThirds = arenaSize * 2f / 3f;
        float gap = 20f;

        if (type == 1) {
            this.x = arenaX;
            this.y = arenaY + (arenaSize / 3f);
            this.width = arenaSize;
            this.height = twoThirds;
            warnX = arenaX + arenaSize + gap; warnY = this.y; warnW = 5f; warnH = this.height;
            warn2X = arenaX - gap; warn2Y = this.y; warn2W = 5f; warn2H = this.height;
        } else if (type == 2) {
            this.x = arenaX;
            this.y = arenaY;
            this.width = arenaSize;
            this.height = twoThirds;
            warnX = arenaX + arenaSize + gap; warnY = this.y; warnW = 5f; warnH = this.height;
            warn2X = arenaX - gap; warn2Y = this.y; warn2W = 5f; warn2H = this.height;
        } else if (type == 3) {
            this.x = arenaX + (arenaSize / 3f);
            this.y = arenaY;
            this.width = twoThirds;
            this.height = arenaSize;
            warnX = this.x; warnY = arenaY + arenaSize + gap; warnW = this.width; warnH = 5f;
            warn2X = this.x; warn2Y = arenaY - gap; warn2W = this.width; warn2H = 5f;
        } else if (type == 4) {
            this.x = arenaX;
            this.y = arenaY;
            this.width = twoThirds;
            this.height = arenaSize;
            warnX = this.x; warnY = arenaY + arenaSize + gap; warnW = this.width; warnH = 5f;
            warn2X = this.x; warn2Y = arenaY - gap; warn2W = this.width; warn2H = 5f;
        }
        updateBounds();
    }

    public boolean isFlashing() {
        return timer >= chargeDuration && timer < chargeDuration + flashDuration;
    }

    @Override
    public void update(float delta) {
        if (!active) return;
        super.update(delta);

        if (timer >= chargeDuration + flashDuration) {
            active = false;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (!active) return;

        if (timer < chargeDuration) {
            float progress = timer / chargeDuration;
            shapeRenderer.setColor(Color.YELLOW);

            if (type == 1 || type == 2) {
                float currentWarnW = warnW + (15f * progress);
                shapeRenderer.rect(warnX, warnY, currentWarnW, warnH);
                shapeRenderer.rect(warn2X - currentWarnW, warn2Y, currentWarnW, warn2H);
            } else {
                float currentWarnH = warnH + (15f * progress);
                shapeRenderer.rect(warnX, warnY, warnW, currentWarnH);
                shapeRenderer.rect(warn2X, warn2Y - currentWarnH, warn2W, currentWarnH);
            }
        } else if (isFlashing()) {
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    @Override
    public void checkCollision(com.fernanda.frontend.entities.Player player, com.fernanda.frontend.entities.SharedStats stats) {
        if (!active || !isFlashing() || player.isInvincible()) return;

        if (com.badlogic.gdx.math.Intersector.overlaps(player.getBounds(), getBounds())) {
            System.out.println("Sistem: Player terkena Area Denial Laser!");
            stats.takeDamage(15f);
            player.triggerInvincibility();
        }
    }
}
