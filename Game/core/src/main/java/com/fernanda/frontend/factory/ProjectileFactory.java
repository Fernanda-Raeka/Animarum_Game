package com.fernanda.frontend.factory;

import com.fernanda.frontend.entities.projectiles.BaseProjectile;
import com.fernanda.frontend.entities.projectiles.MeteorProjectile;
import com.fernanda.frontend.entities.projectiles.FireballProjectile;
import com.fernanda.frontend.entities.projectiles.LaserProjectile;
import com.fernanda.frontend.entities.projectiles.TsunamiProjectile;
import com.fernanda.frontend.pool.MeteorPool;
import com.fernanda.frontend.pool.FireballPool;
import com.fernanda.frontend.pool.LaserPool;
import com.fernanda.frontend.pool.TsunamiPool;

public class ProjectileFactory {
    
    private static final MeteorPool meteorPool = new MeteorPool();
    private static final FireballPool fireballPool = new FireballPool();
    private static final LaserPool laserPool = new LaserPool();
    private static final TsunamiPool tsunamiPool = new TsunamiPool();

    public static MeteorProjectile obtainMeteor(float x, float y, float speedX, float speedY) {
        MeteorProjectile meteor = meteorPool.obtain();
        meteor.init(x, y, speedX, speedY);
        return meteor;
    }

    public static FireballProjectile obtainFireball(float x, float y, float speedX, float speedY) {
        FireballProjectile fireball = fireballPool.obtain();
        fireball.init(x, y, speedX, speedY);
        return fireball;
    }

    public static LaserProjectile obtainLaser(float arenaX, float arenaY, float arenaSize, int type, float chargeDuration) {
        LaserProjectile laser = laserPool.obtain();
        laser.initLaser(arenaX, arenaY, arenaSize, type, chargeDuration);
        return laser;
    }

    public static TsunamiProjectile obtainTsunami(float x, float y, float width, float height, float speedX, boolean isFake) {
        TsunamiProjectile tsunami = tsunamiPool.obtain();
        tsunami.initTsunami(x, y, width, height, speedX, isFake);
        return tsunami;
    }

    public static void freeProjectile(BaseProjectile projectile) {
        if (projectile instanceof MeteorProjectile) {
            meteorPool.free((MeteorProjectile) projectile);
        } else if (projectile instanceof FireballProjectile) {
            fireballPool.free((FireballProjectile) projectile);
        } else if (projectile instanceof LaserProjectile) {
            laserPool.free((LaserProjectile) projectile);
        } else if (projectile instanceof TsunamiProjectile) {
            tsunamiPool.free((TsunamiProjectile) projectile);
        }
    }
}
