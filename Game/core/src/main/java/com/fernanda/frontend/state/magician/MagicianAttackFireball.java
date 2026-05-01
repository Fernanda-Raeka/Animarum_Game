package com.fernanda.frontend.state.magician;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.strategy.attack.AttackPattern;
import com.fernanda.frontend.strategy.attack.FanSpreadPattern;

public class MagicianAttackFireball implements BossState {
    private float attackTimer;
    private float spawnTimer;
    private float globalAngleOffset = 0f;
    private final float ATTACK_DURATION = 5f;
    private final float SPAWN_INTERVAL = 0.6f;
    private AttackPattern attackPattern;

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        spawnTimer = 0f;
        attackPattern = new FanSpreadPattern(10, 140f, 250f);

        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.BOTTOM);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.RED);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        spawnTimer += delta;
        globalAngleOffset += delta * 60f;

        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.updatePosition(arenaX, arenaY, arenaSize);

            if (spawnTimer >= SPAWN_INTERVAL) {
                spawnTimer = 0f;
                float startX = mBoss.getX() + mBoss.getWidth() / 2f;
                float startY = mBoss.getY() + mBoss.getHeight();

                attackPattern.execute(boss, startX, startY, arenaSize, globalAngleOffset);
            }
        }

        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
