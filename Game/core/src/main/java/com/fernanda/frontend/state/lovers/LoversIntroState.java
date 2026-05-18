package com.fernanda.frontend.state.lovers;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;
import com.fernanda.frontend.entities.LoversBoss;
import com.fernanda.frontend.state.BossState;

public class LoversIntroState implements BossState {
    private float timer;
    private final float INTRO_DURATION = 3.0f;

    @Override
    public void enter(BaseBoss boss) {
        this.timer = 0f;
        
        if (boss instanceof LoversBoss) {
            LoversBoss lovers = (LoversBoss) boss;
            lovers.setArenaSplit(false);
            lovers.setBoundsPlayer1(0, 0, 1280, 720);
            lovers.setBoundsPlayer2(0, 0, 1280, 720);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        timer += delta;
        
        if (timer >= INTRO_DURATION) {
            boss.changeState(new LoversTransitionState());
        }
    }

    @Override
    public void exit(BaseBoss boss) {
    }
}