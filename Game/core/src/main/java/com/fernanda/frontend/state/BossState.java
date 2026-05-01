package com.fernanda.frontend.state;

import com.badlogic.gdx.utils.Array;
import com.fernanda.frontend.entities.AnimaSpark;
import com.fernanda.frontend.entities.BaseBoss;

public interface BossState {
    void enter(BaseBoss boss);
    void update(BaseBoss boss, float delta, float arenaX, float arenaY, float arenaSize, Array<AnimaSpark> sparks);
    void exit(BaseBoss boss);
}
