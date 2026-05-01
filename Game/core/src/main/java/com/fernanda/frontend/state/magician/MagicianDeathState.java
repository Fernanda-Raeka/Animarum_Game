package com.fernanda.frontend.state.magician;

import com.fernanda.frontend.state.BossState;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;

public class MagicianDeathState implements BossState {
    
    private float deathTimer;
    private final float DEATH_DURATION = 5f;

    @Override
    public void enter(BaseBoss boss) {
        deathTimer = 0f;
        System.out.println(boss.getName() + " MATI: \"Pertunjukan... telah... usai...\"");
    }

    @Override
    public void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks) {
        deathTimer += delta;
        
        if (deathTimer >= DEATH_DURATION) {
            // MainMenu atau menampilkan layar kemenangan
            System.out.println("Sistem: Boss telah dihancurkan sepenuhnya. KEMENANGAN!");
            // boss.changeState(null); 
            deathTimer = -9999f; 
        }
    }

    @Override
    public void exit(BaseBoss boss) {
    }
}
