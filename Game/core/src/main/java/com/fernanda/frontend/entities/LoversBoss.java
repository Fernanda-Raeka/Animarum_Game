package com.fernanda.frontend.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.fernanda.frontend.state.lovers.LoversIntroState;

public class LoversBoss extends BaseBoss {
    private Rectangle boundsPlayer1;
    private Rectangle boundsPlayer2;
    private boolean isArenaSplit;

    public LoversBoss(float x, float y) {
        super(x, y, 64, 64, 2000, "The Lovers");
        this.boundsPlayer1 = new Rectangle(0, 0, 1280, 720);
        this.boundsPlayer2 = new Rectangle(0, 0, 1280, 720);
        this.isArenaSplit = false;
        
        changeState(new LoversIntroState());
    }

    public Rectangle getBoundsPlayer1() {
        return boundsPlayer1;
    }

    public void setBoundsPlayer1(float x, float y, float width, float height) {
        this.boundsPlayer1.set(x, y, width, height);
    }

    public Rectangle getBoundsPlayer2() {
        return boundsPlayer2;
    }

    public void setBoundsPlayer2(float x, float y, float width, float height) {
        this.boundsPlayer2.set(x, y, width, height);
    }

    public boolean isArenaSplit() {
        return isArenaSplit;
    }

    public void setArenaSplit(boolean isArenaSplit) {
        this.isArenaSplit = isArenaSplit;
    }

    @Override
    protected void onDeath() {
    }

    private float fadeAlpha = 0f;
    private float targetFadeAlpha = 0f;
    private float fadeSpeed = 2f;

    public void setTargetFade(float target, float speed) {
        this.targetFadeAlpha = target;
        this.fadeSpeed = speed;
    }

    @Override
    public void update(float delta, float arenaX, float arenaY, float arenaSize, com.badlogic.gdx.utils.Array<com.fernanda.frontend.entities.AnimaSpark> sparks) {
        if (fadeAlpha < targetFadeAlpha) {
            fadeAlpha = Math.min(fadeAlpha + delta * fadeSpeed, targetFadeAlpha);
        } else if (fadeAlpha > targetFadeAlpha) {
            fadeAlpha = Math.max(fadeAlpha - delta * fadeSpeed, targetFadeAlpha);
        }
        super.update(delta, arenaX, arenaY, arenaSize, sparks);
    }

    public float getFadeAlpha() {
        return fadeAlpha;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
    }
}