package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class sound_toggle extends Actor {

    private Sprite sprite;
    private TextureAtlas atlas;

    public sound_toggle(final the_bus_never_comes parentClass) {
        atlas = new TextureAtlas(Gdx.files.internal("spriteSheets/buttons/pack.atlas"));
        sprite = new Sprite(atlas.findRegion("soundOn"));
        if(parentClass.getPrefs().getBoolean("sound", true)) {
            sprite.setRegion(atlas.findRegion("soundOn"));
            sprite.setSize(100 * parentClass.getScaleX(), 75 * parentClass.getScaleY());
            sprite.setPosition(Gdx.graphics.getWidth() - 140 * parentClass.getScaleX(), Gdx.graphics.getHeight() - 125 * parentClass.getScaleY());
        }
        else{
            sprite.setRegion(atlas.findRegion("soundOff"));;
            sprite.setSize(100 * parentClass.getScaleX(), 87 * parentClass.getScaleY());
            sprite.setPosition(Gdx.graphics.getWidth() - 140 * parentClass.getScaleX(), Gdx.graphics.getHeight() - 134f * parentClass.getScaleY());
        }

        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (parentClass.getPrefs().getBoolean("sound")) {
                    parentClass.getBgm().pause();
                    parentClass.getPrefs().putBoolean("sound", false);
                    sprite.setRegion(atlas.findRegion("soundOff"));
                    ;
                    sprite.setSize(100 * parentClass.getScaleX(), 87 * parentClass.getScaleY());
                    sprite.setPosition(Gdx.graphics.getWidth() - 140 * parentClass.getScaleX(), Gdx.graphics.getHeight() - 134f * parentClass.getScaleY());
                } else {
                    parentClass.getBgm().play();
                    parentClass.getPrefs().putBoolean("sound", true);
                    sprite.setRegion(atlas.findRegion("soundOn"));
                    sprite.setSize(100 * parentClass.getScaleX(), 75 * parentClass.getScaleY());
                    sprite.setPosition(Gdx.graphics.getWidth() - 140 * parentClass.getScaleX(), Gdx.graphics.getHeight() - 125 * parentClass.getScaleY());
                }
                parentClass.getPrefs().flush();
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
