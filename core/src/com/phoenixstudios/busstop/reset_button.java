package com.phoenixstudios.busstop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class reset_button extends Actor {
    private Float alpha;
    private Sprite sprite;
    private Boolean clicked = false;

    public void setAlphaToZero() {
        sprite.setAlpha(0f);
        alpha = 0f;
    }

    public reset_button(final the_bus_never_comes parentClass, final end_screen parentStage) {
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setRegion(parentStage.getButtons().findRegion("reset2"));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setRegion(parentStage.getButtons().findRegion("reset1"));
                if(clicked == false){
                    parentClass.getGameStage().reset();
                    clicked = true;
                }
            }
        });

        sprite = new Sprite(parentStage.getButtons().findRegion("reset1"));
        sprite.setSize(120 * 3f * parentClass.getScaleX(), 120 * 3f * parentClass.getScaleY());
        sprite.setPosition(810f * parentClass.getScaleX() - sprite.getWidth() / 2, 550f * parentClass.getScaleY());

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(alpha < 1f){
            alpha+=0.05f;
            sprite.setAlpha(alpha);
        }
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setClicked(Boolean clicked) {
        this.clicked = clicked;
    }
}
