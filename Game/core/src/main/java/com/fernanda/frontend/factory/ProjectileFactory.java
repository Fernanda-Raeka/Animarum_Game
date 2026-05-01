package com.fernanda.frontend.factory;

import com.fernanda.frontend.entities.projectiles.BaseProjectile;
import com.fernanda.frontend.entities.projectiles.FireballProjectile;
import com.fernanda.frontend.entities.projectiles.LaserProjectile;
import com.fernanda.frontend.entities.projectiles.MeteorProjectile;
import com.fernanda.frontend.entities.projectiles.TsunamiProjectile;
import com.fernanda.frontend.pool.GenericPool;
import com.fernanda.frontend.strategy.movement.DropMovement;
import com.fernanda.frontend.strategy.movement.LinearMovement;

public class ProjectileFactory {

    private static final GenericPool<MeteorProjectile> meteorPool = new GenericPool<>(MeteorProjectile::new);

    private static final GenericPool<FireballProjectile> fireballPool = new GenericPool<>(100, 500, FireballProjectile::new);

    private static final GenericPool<LaserProjectile> laserPool = new GenericPool<>(LaserProjectile::new);

    private static final GenericPool<TsunamiProjectile> tsunamiPool = new GenericPool<>(TsunamiProjectile::new);

    public static MeteorProjectile obtainMeteor(float x, float y, float speedY) {
        MeteorProjectile meteor = meteorPool.obtain();
        meteor.init(x, y, 0, speedY);

        DropMovement dropStrategy = new DropMovement();
        dropStrategy.init(speedY);
        meteor.setMovementStrategy(dropStrategy);

        return meteor;
    }

    public static FireballProjectile obtainFireball(float x, float y, float speedX, float speedY) {
        FireballProjectile fireball = fireballPool.obtain();
        fireball.init(x, y, speedX, speedY);

        LinearMovement linearStrategy = new LinearMovement();
        linearStrategy.init(speedX, speedY);
        fireball.setMovementStrategy(linearStrategy);

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

        LinearMovement linearStrategy = new LinearMovement();
        linearStrategy.init(speedX, 0);
        tsunami.setMovementStrategy(linearStrategy);

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
