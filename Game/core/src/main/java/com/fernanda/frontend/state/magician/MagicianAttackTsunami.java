package com.fernanda.frontend.state.magician;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.strategy.attack.AttackPattern;
import com.fernanda.frontend.strategy.attack.TsunamiWavePattern;

public class MagicianAttackTsunami implements BossState {
    private float attackTimer;
    private int waveCount = 0;
    private float nextSpawnTime = 0.5f;
    private final float ATTACK_DURATION = 7.5f;
    private AttackPattern attackPattern;

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        waveCount = 0;
        nextSpawnTime = 0.5f;
        attackPattern = new TsunamiWavePattern();

        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.LEFT);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.CYAN);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        if (boss instanceof MagicianBoss) {
            ((MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }

        if (waveCount < 4 && attackTimer >= nextSpawnTime) {
            attackPattern.execute(boss, arenaX, arenaY, arenaSize);
            waveCount++;
            nextSpawnTime += 1.5f;
        }

        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
