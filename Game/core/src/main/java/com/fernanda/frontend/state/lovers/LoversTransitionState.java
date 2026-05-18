package com.fernanda.frontend.state.lovers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.state.BossState;

public class LoversTransitionState implements BossState {
    private float timer;
    private final float TRANSITION_DURATION = 1.0f;
    private int nextAttack;

    @Override
    public void enter(BaseBoss boss) {
        this.timer = 0f;
        this.nextAttack = MathUtils.random(1, 3);
        if (boss instanceof LoversBoss) {
            ((LoversBoss) boss).setTargetFade(1f, 8f); 
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize,
            Array<AnimaSpark> sparks) {
        timer += delta;

        if (timer >= TRANSITION_DURATION) {
            switch (nextAttack) {
                case 1:
                    boss.changeState(new LoversCrossfireState());
                    break;
                case 2:
                    boss.changeState(new LoversMinefieldState());
                    break;
                case 3:
                    boss.changeState(new LoversConvergeState());
                    break;
            }
        }
    }

    @Override
    public void exit(BaseBoss boss) {
    }
}