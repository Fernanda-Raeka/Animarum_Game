package com.fernanda.frontend.state.magician;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.MagicianBoss;
import com.fernanda.frontend.state.BossState;
import com.fernanda.frontend.strategy.attack.AttackPattern;
import com.fernanda.frontend.strategy.attack.RandomDropPattern;

public class MagicianAttackMeteor implements BossState {
    private float attackTimer;
    private float spawnTimer;
    private final float ATTACK_DURATION = 5f;
    private final float SPAWN_INTERVAL = 0.2f;
    private AttackPattern attackPattern;

    @Override
    public void enter(BaseBoss boss) {
        attackTimer = 0f;
        attackPattern = new RandomDropPattern(-400f);

        if (boss instanceof MagicianBoss) {
            MagicianBoss mBoss = (MagicianBoss) boss;
            mBoss.setCurrentSide(MagicianBoss.Side.TOP);
            mBoss.setBossColor(com.badlogic.gdx.graphics.Color.BROWN);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        attackTimer += delta;
        spawnTimer += delta;

        if (boss instanceof MagicianBoss) {
            ((MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }

        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnTimer = 0f;
            attackPattern.execute(boss, arenaX, arenaY, arenaSize);
        }

        if (attackTimer >= ATTACK_DURATION) {
            boss.changeState(new MagicianIdleState());
        }
    }

    @Override
    public void exit(BaseBoss boss) { }
}
