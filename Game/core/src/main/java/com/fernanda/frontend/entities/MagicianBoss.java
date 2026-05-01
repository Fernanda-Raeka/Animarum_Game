package com.fernanda.frontend.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fernanda.frontend.state.magician.MagicianIntroState;
import com.fernanda.frontend.state.magician.MagicianDeathState;

public class MagicianBoss extends BaseBoss {

    private float width = 80f;
    private float height = 80f;
    
    public enum Side { TOP, BOTTOM, LEFT, RIGHT }
    private Side currentSide = Side.TOP;
    private Color bossColor = Color.PURPLE;

    public MagicianBoss(float arenaX, float arenaY, float arenaSize) {
        super(arenaX + (arenaSize / 2f) - 40f, arenaY + arenaSize + 50f, 80f, 80f, 1000f, "THE MAGICIAN");
        
        changeState(new MagicianIntroState());
    }

    @Override
    protected void onDeath() {
        changeState(new MagicianDeathState());
    }

    public void setCurrentSide(Side side) {
        this.currentSide = side;
    }

    public Side getCurrentSide() {
        return currentSide;
    }

    public void setBossColor(Color color) {
        this.bossColor = color;
    }

    public void updatePosition(float arenaX, float arenaY, float arenaSize) {
        float offset = 20f;
        switch (currentSide) {
            case TOP:
                setX(arenaX + (arenaSize / 2f) - (getWidth() / 2f));
                setY(arenaY + arenaSize + offset);
                break;
            case BOTTOM:
                setX(arenaX + (arenaSize / 2f) - (getWidth() / 2f));
                setY(arenaY - getHeight() - offset);
                break;
            case LEFT:
                setX(arenaX - getWidth() - offset);
                setY(arenaY + (arenaSize / 2f) - (getHeight() / 2f));
                break;
            case RIGHT:
                setX(arenaX + arenaSize + offset);
                setY(arenaY + (arenaSize / 2f) - (getHeight() / 2f));
                break;
        }
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        if (hp <= 0) return;

        shapeRenderer.setColor(bossColor);
        shapeRenderer.rect(x, y, width, height);
    }
}
