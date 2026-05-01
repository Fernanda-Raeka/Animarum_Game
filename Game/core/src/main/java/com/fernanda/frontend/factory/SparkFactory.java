package com.fernanda.frontend.factory;

import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.pool.SparkPool;

public class SparkFactory {
    private static final SparkPool sparkPool = new SparkPool();

    public static AnimaSpark obtainSpark(float arenaX, float arenaY, float arenaSize, boolean isDuo) {
        AnimaSpark spark = sparkPool.obtain();
        spark.init(arenaX, arenaY, arenaSize, isDuo); 
        return spark;
    }

    public static void freeSpark(AnimaSpark spark) {
        sparkPool.free(spark); 
    }
}
