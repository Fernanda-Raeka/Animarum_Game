package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;

public class MagicianIntroState implements BossState {

    private float introTimer;
    private final float INTRO_DURATION = 3f;

    @Override
    public void enter(BaseBoss boss) {
        introTimer = 0f;
        System.out.println(boss.getName() + " MUNCUL: \"Selamat datang di arena pertunjukanku...\"");

        if (boss instanceof com.fernanda.frontend.entities.MagicianBoss) {
            ((com.fernanda.frontend.entities.MagicianBoss) boss).setCurrentSide(com.fernanda.frontend.entities.MagicianBoss.Side.TOP);
        }
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        introTimer += delta;

        if (boss instanceof com.fernanda.frontend.entities.MagicianBoss) {
            ((com.fernanda.frontend.entities.MagicianBoss) boss).updatePosition(arenaX, arenaY, arenaSize);
        }

        if (introTimer >= INTRO_DURATION) {
            int randomAttack = com.badlogic.gdx.math.MathUtils.random(1, 4);
            switch (randomAttack) {
                case 1: boss.changeState(new MagicianAttackFireball()); break;
                case 2: boss.changeState(new MagicianAttackLaser()); break;
                case 3: boss.changeState(new MagicianAttackMeteor()); break;
                case 4: boss.changeState(new MagicianAttackTsunami()); break;
            }
        }
    }

    @Override
    public void exit(BaseBoss boss) {
        System.out.println(boss.getName() + " Intro selesai, pertunjukan dimulai!");
    }
}
