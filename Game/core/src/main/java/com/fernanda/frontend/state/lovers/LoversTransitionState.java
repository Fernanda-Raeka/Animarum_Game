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
        this.nextAttack = MathUtils.random(1);
        if (boss instanceof LoversBoss) {
            ((LoversBoss) boss).setTargetFade(1f, 8f); // Layar menggelap (fade to black) dalam 0.5 detik
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        timer += delta;
        
        if (timer >= TRANSITION_DURATION) {
            if (nextAttack == 0) {
                boss.changeState(new LoversCrossfireState());
            } else {
                boss.changeState(new LoversMinefieldState());
            }
        }
    }

    @Override
    public void exit(BaseBoss boss) {
    }
}