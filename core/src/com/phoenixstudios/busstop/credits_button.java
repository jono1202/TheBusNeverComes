package com.phoenixstudios.busstop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class credits_button extends Actor{
    private Float alpha;
    private Sprite sprite;

    public void setAlphaToZero() {
        sprite.setAlpha(0f);
        alpha = 0f;
    }

    public credits_button(final the_bus_never_comes parentClass, final end_screen parentStage) {
        sprite = new Sprite(parentStage.getButtons().findRegion("credit"));
        sprite.setSize(120*3f*parentClass.getScaleX(), 120*3f*parentClass.getScaleY());
        sprite.setPosition(270f*parentClass.getScaleX()-sprite.getWidth()/2, 550f*parentClass.getScaleY());

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setRegion(parentStage.getButtons().findRegion("clickedcredit"));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setRegion(parentStage.getButtons().findRegion("credit"));
                if(parentStage.getCredits().getZIndex() > parentStage.getFrame().getZIndex())
                    parentStage.getCredits().setZIndex(parentStage.getCredits().getZIndex()-1);
                else
                    parentStage.getCredits().setZIndex(parentStage.getCredits().getZIndex()+1);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        if(alpha < 1f){
            alpha+=0.05f;
            sprite.setAlpha(alpha);
        }
        super.act(delta);
    }
}
