package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by jono1202 on 9/6/2015.
 */
public class score_board extends Actor {
    private Sprite sprite;
    private the_bus_never_comes parentClass;
    private end_screen parentStage;

    public score_board(end_screen parentStage, the_bus_never_comes parentClass) {
        this.parentClass = parentClass;
        this.parentStage = parentStage;

        sprite = new Sprite(parentStage.getButtons().findRegion("scoreboard"));
        sprite.setSize(parentClass.getScaleX() * 1000, parentClass.getScaleY() * 500);
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, 950 * parentClass.getScaleY());

        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        parentClass.getPixelFont().draw(batch, "Score\n" + "\n" + "High Score", 150 * parentClass.getScaleX(), 1330 * parentClass.getScaleY());
        parentClass.getPixelFont().draw(batch, parentClass.getGameStage().getTouches() + "\n" + "\n" + parentClass.getPrefs().getInteger("highScore", 0), 850*parentClass.getScaleX(), 1330*parentClass.getScaleY());
    }
}
