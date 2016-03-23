package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by jono1202 on 9/1/2015.
 */
public class man extends Actor {
    private Sprite sprite;
    private TextureAtlas atlas;
    private SpriteBatch batch;
    private Rectangle bounds;
    private the_bus_never_comes parentClass;

    public Rectangle getBounds() {
        return bounds;
    }

    private Sprite back;

    public void setSprite(Integer value){
        if(value == 0)
            sprite.setRegion(atlas.findRegion("bus_stop_man_neutral"));
        else if(value == 1)
            sprite.setRegion(atlas.findRegion("bus_stop_man_smile"));
        else{
            sprite.setRegion(atlas.findRegion("bus_man_surprised"));
        }
    }

    public man(SpriteBatch tbatch, Stage parentStage, the_bus_never_comes parentClass) {
        this.parentClass = parentClass;

        float scaleX = parentStage.getWidth()/1080f;
        float scaleY = parentStage.getHeight()/1920f;

        bounds = new Rectangle();
        batch = tbatch;
        atlas = new TextureAtlas(Gdx.files.internal("spriteSheets/people/people.txt"));
        sprite = new Sprite(atlas.findRegion("bus_stop_man_neutral"));
        sprite.setSize((306 / 2f) * scaleX, (630 / 2f) * scaleY);
        sprite.setPosition(660 * scaleX, 220 * scaleY);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        if(parentClass.isShowBack()){
            back = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
            back.setSize(getWidth(), getHeight());
        }
    }

    @Override
    public boolean remove() {
        super.remove();
        atlas.dispose();
        sprite.getTexture().dispose();
        return true;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        if(parentClass.isShowBack()){
            back.setPosition(getX(), getY());
            back.draw(batch, 0.5f);
        }

        Gdx.gl.glClearColor(1,0,0,.25f);
        sprite.draw(batch);
        sprite.setPosition(getX(), getY());
    }
}
