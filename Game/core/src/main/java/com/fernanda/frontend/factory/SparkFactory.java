package com.fernanda.frontend.factory;

import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.pool.GenericPool;

public class SparkFactory {
    private static final GenericPool<AnimaSpark> sparkPool = new GenericPool<>(50, 200, AnimaSpark::new);

    public static AnimaSpark obtainSpark(float arenaX, float arenaY, float arenaSize, boolean isDuo) {
        AnimaSpark spark = sparkPool.obtain();
        spark.init(arenaX, arenaY, arenaSize, isDuo);
        return spark;
    }

    public static void freeSpark(AnimaSpark spark) {
        sparkPool.free(spark);
    }
}
