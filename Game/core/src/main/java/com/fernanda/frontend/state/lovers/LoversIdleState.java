package com.fernanda.frontend.state.lovers;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.factory.SparkFactory;

public class LoversIdleState implements BossState {
    private boolean hasSpawnedSparks;

    public void enter(BaseBoss boss) {
        System.out.println(boss.getName() + " memasuki Idle State (Fase Mengambil Spark).");
        hasSpawnedSparks = false;
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        if (!hasSpawnedSparks && boss instanceof LoversBoss) {
            LoversBoss lovers = (LoversBoss) boss;
            
            AnimaSpark spark1;
            AnimaSpark spark2;
            
            if (lovers.isArenaSplit()) {
                spark1 = SparkFactory.obtainSpark(
                    lovers.getBoundsPlayer1().x, 
                    lovers.getBoundsPlayer1().y, 
                    lovers.getBoundsPlayer1().width,
                    lovers.getBoundsPlayer1().height
                );
                
                spark2 = SparkFactory.obtainSpark(
                    lovers.getBoundsPlayer2().x, 
                    lovers.getBoundsPlayer2().y, 
                    lovers.getBoundsPlayer2().width, 
                    lovers.getBoundsPlayer2().height
                );
            } else {
                spark1 = SparkFactory.obtainSpark(arenaX, arenaY, arenaSize, arenaSize);
                spark2 = SparkFactory.obtainSpark(arenaX, arenaY, arenaSize, arenaSize);
            }
            
            spark1.linkWith(spark2);
            
            sparks.add(spark1);
            sparks.add(spark2);
            hasSpawnedSparks = true;
            System.out.println("Sistem: The Lovers memunculkan sepasang Anima Spark!");
        }

        if (hasSpawnedSparks && sparks.isEmpty()) {
            boss.changeState(new LoversTransitionState());
        }
    }

    @Override
    public void exit(BaseBoss boss) {
        System.out.println(boss.getName() + " keluar dari Idle State.");
    }
}
