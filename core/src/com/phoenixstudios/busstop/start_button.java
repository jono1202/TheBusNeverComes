package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by jono1202 on 9/4/2015.
 */
public class start_button extends Actor {

    private Sprite sprite;
    private TextureAtlas atlas;
    private Sprite back;
    private Stage parentStage;
    private the_bus_never_comes parentClass;

    public start_button(final Stage parentStage, final the_bus_never_comes parent_class) {
        this.parentClass = parent_class;
        this.parentStage = parentStage;

        atlas = new TextureAtlas(Gdx.files.internal("spriteSheets/buttons/pack.atlas"));
        sprite = new Sprite(atlas.findRegion("play"));
        sprite.setSize(300 * (Gdx.graphics.getWidth() / 1080f), 300 * (Gdx.graphics.getHeight() / 1920f));
        sprite.setPosition(660 * (Gdx.graphics.getWidth() / 1080f), 550 * (Gdx.graphics.getHeight() / 1920f));
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        if(parent_class.isShowBack()){
            back = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
            back.setSize(getWidth(), getHeight());
        }

        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setRegion(atlas.findRegion("clickedplay"));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sprite.setRegion(atlas.findRegion("play"));
                parent_class.setStartscreen(false);
                Gdx.input.setInputProcessor(parent_class.getGameMultiplexer());
                parent_class.setGamestage(true);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(parentClass.isShowBack()){
            back.setPosition(getX(), getY());
            back.draw(batch, 0.5f);
        }

        sprite.setPosition(getX(),getY());
        sprite.draw(batch);
    }
}
