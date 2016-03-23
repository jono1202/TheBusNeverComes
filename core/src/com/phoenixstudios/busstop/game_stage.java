package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.TimerTask;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class game_stage extends Stage {
    private int touches;

    public void setTouchesToZero(){
        touches = 0;
    }
    public void touchesAddOne(){
        touches++;
    }
    public int getTouches(){
        return touches;
    }

    private int currentFrame = 0;
    private int amountOfObjects = 1;
    private int timeTried = 0;
    private float timeOfObjects = 10;
    private float intervalOfObjects = 240;
    private the_bus_never_comes parentClass;
    private MoveToAction mba;
    private TimerTask task;

    public void reset(){
        parentClass.setEndscreen(false);
        timeTried++;
        parentClass.getMan().setSprite(0);
        parentClass.setFrameBuffer(0);
        currentFrame = 0;
        parentClass.setGamestage(true);
        touches = 0;
        Gdx.input.setInputProcessor(parentClass.getGameMultiplexer());
    }

    public MoveToAction getMba(float maxX, float y) {
        mba = new MoveToAction();
        mba.setPosition(maxX, y);
        mba.setDuration(timeOfObjects);
        return mba;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getAmountOfObjects() {
        return amountOfObjects;
    }

    public void setAmountOfObjects(int amountOfObjects) {
        this.amountOfObjects = amountOfObjects;
    }

    public float getTimeOfObjects() {
        return timeOfObjects;
    }

    public void setTimeOfObjects(float timeOfObjects) {
        this.timeOfObjects = timeOfObjects;
    }

    public float getIntervalOfObjects() {
        return intervalOfObjects;
    }

    public void setIntervalOfObjects(float intervalOfObjects) {
        this.intervalOfObjects = intervalOfObjects;
    }

    public int getTimeTried() {
        return timeTried;
    }

    public game_stage(Viewport viewport, the_bus_never_comes parentClass) {
        super(viewport);
        this.parentClass = parentClass;
    }

    public void updateValues(){
        currentFrame++;
        float time = currentFrame/60f;

        if(time < 20){
            intervalOfObjects = 2*60f;
        } else if(time <40){
            intervalOfObjects = 1.5f*60;
        } else if(time < 60){
            intervalOfObjects = 60f;
        } else{
            intervalOfObjects = .7f*60;
        }

        if(time < 30){
            timeOfObjects = 2f;
        } else if(time < 50){
            timeOfObjects = 1.5f;
        } else{
            timeOfObjects = 1.3f;
        }

        if(time < 6){
            amountOfObjects = 1;
        } else if(time < 12){
            amountOfObjects = 2;
        } else if(time < 16){
            amountOfObjects = 3;
        } else if(time < 40){
            amountOfObjects = 4;
        } else if(time < 70){
            amountOfObjects = 5;
        } else{
            amountOfObjects = 6;
        }
    }
}
