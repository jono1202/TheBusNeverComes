package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class end_bus extends Actor {
    private Sprite sprite;
    private MoveByAction mba;

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    public end_bus(the_bus_never_comes parentClass) {
        sprite = new Sprite(new Texture(Gdx.files.internal("spritesMisc/end_bus.png")));
        sprite.setSize(2 * 541 * parentClass.getScaleX(), 2 * 339 * parentClass.getScaleY());
        sprite.setPosition(-sprite.getWidth(), parentClass.getScaleY() * 130);

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        mba = new MoveByAction();
        mba.setAmountX(1000f * parentClass.getScaleX());
        mba.setInterpolation(Interpolation.swingOut);
        mba.setDuration(1.3f);

        end_bus.this.addAction(mba);
    }

    public MoveByAction getMba() {
        return mba;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
