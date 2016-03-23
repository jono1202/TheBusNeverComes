package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class game_over extends Actor {
    private Sprite sprite;
    private MoveByAction mba;
    public game_over(end_screen parentStage, the_bus_never_comes parentClass) {
        sprite = new Sprite(parentStage.getButtons().findRegion("busarrived"));
        sprite.setSize(198*4*parentClass.getScaleX(), 36*4*parentClass.getScaleY());
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, 1920 * parentClass.getScaleY());

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        mba = new MoveByAction();
        mba.setInterpolation(Interpolation.bounceOut);
        mba.setAmountY(-420 * parentClass.getScaleY());
        mba.setDuration(1.3f);

        game_over.this.addAction(mba);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public MoveByAction getMba() {
        return mba;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
