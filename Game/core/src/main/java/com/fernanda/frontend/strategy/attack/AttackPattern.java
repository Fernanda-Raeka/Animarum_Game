package com.fernanda.frontend.strategy.attack;

import com.fernanda.frontend.entities.BaseBoss;

public interface AttackPattern {
    void execute(BaseBoss boss, float x, float y, float size, float... optional);
}
