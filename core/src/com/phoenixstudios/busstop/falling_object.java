package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

/**
 * Created by User on 2015-08-28.
 */
public class falling_object extends Actor{
    private  String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private float boundIncrease;

    private Rectangle returnedRectangle = new Rectangle();

    private man man;
    private Random rand;
    private Sprite sprite;
    private Rectangle bounds;
    private float rotation;
    private game_stage gamestage;
    private the_bus_never_comes parentClass;
    private float scaleX, scaleY;
    private Sprite back;
    private Timer.Task task;

    public falling_object(final game_stage parentStage, man tman, the_bus_never_comes parentClass) {
        this.parentClass = parentClass;

        man = tman;

        gamestage = parentStage;

        scaleX = parentClass.getScaleX();
        scaleY = parentClass.getScaleY();
        boundIncrease = 40*((scaleX+scaleY)/2);

        bounds = new Rectangle();

        rand = parentClass.getRand();
        sprite = new Sprite(parentClass.getRegion());
        sprite.setOrigin(sprite.getWidth() / 2 * scaleX, sprite.getHeight() / 2 * scaleY);
        sprite.setSize(sprite.getWidth() * scaleX, sprite.getHeight() * scaleY);

        Integer maxX = Math.round(Gdx.graphics.getWidth()-sprite.getWidth());
        sprite.setPosition((float) rand.nextInt(maxX), Gdx.graphics.getHeight());
        setBounds(sprite.getX() - (boundIncrease / 2), sprite.getY() - (boundIncrease / 2), boundIncrease + ((120) * scaleX), ((120) * scaleY) + boundIncrease);

        if(parentClass.isShowBack()){
            back = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));
            back.setSize(getWidth(), getHeight());
        }

        /*MoveToAction mba = new MoveToAction()
        mba.setPosition((float) rand.nextInt(maxX), -getHeight());
        mba.setDuration(parentStage.getTimeOfObjects());*/

        falling_object.this.addAction(parentStage.getMba((float) rand.nextInt(maxX), -getHeight()));

        if(rand.nextInt(2) == 1)
            rotation = 5;
        else{
            rotation = -5;
        }

       /*addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Hit", name);
                remove();
                return true;
            }
        });*/

        task = new Timer.Task() {
            @Override
            public void run() {
                remove();
            }
        };
        Timer.schedule(task,parentStage.getTimeOfObjects());
    }

    @Override
    public boolean remove() {
        Gdx.app.log("Object", "Removed");
        super.remove();
        return true;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        if(Intersector.intersectRectangles(bounds, man.getBounds(), returnedRectangle)){
            parentClass.getLose().play();
            parentClass.getMan().setSprite(2);
            parentClass.getEndScreen().use();
            Gdx.app.log("Game", "over");
        }
        sprite.setRotation(sprite.getRotation()+rotation);
        sprite.setPosition(getX()+(boundIncrease/2)+((120*scaleX)-sprite.getWidth())/2, getY()+(boundIncrease/2)+((120*scaleY)-sprite.getHeight())/2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(parentClass.isShowBack()){
            back.setPosition(getX(), getY());
            back.draw(batch, 0.5f);
        }

        sprite.draw(batch);
    }
}
