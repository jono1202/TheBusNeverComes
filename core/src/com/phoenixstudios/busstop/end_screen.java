package com.phoenixstudios.busstop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by jono1202 on 9/5/2015.
 */
public class end_screen extends Stage {
    private TextureAtlas buttons;

    public TextureAtlas getButtons() {
        return buttons;
    }

    public Image getCredits() {
        return credits;
    }

    public score_board getFrame() {
        return frame;
    }

    private the_bus_never_comes parentClass;
    private InputMultiplexer multiplexer;
    private end_bus endBus;
    private game_over gameOver;
    private reset_button resetButton;
    private credits_button creditsButton;
    private Image credits;
    private score_board frame;
    private AlphaAction aa;

    public end_screen(Viewport viewport, the_bus_never_comes parentClass) {
        super(viewport);

        aa = new AlphaAction();
        aa.setDuration(1f);
        aa.setInterpolation(Interpolation.circleOut);
        aa.setAlpha(1f);

        buttons = new TextureAtlas(Gdx.files.internal("spriteSheets/buttons/pack.atlas"));

        this.parentClass = parentClass;

        multiplexer = new InputMultiplexer(this, parentClass.getBackStage());

        frame = new score_board(this, parentClass);
        this.addActor(frame);

        credits = new Image(buttons.findRegion("creditbox"));
        credits.setSize(parentClass.getScaleX() * 1000, parentClass.getScaleY() * 500);
        credits.setPosition(Gdx.graphics.getWidth() / 2 - credits.getWidth() / 2, 950 * parentClass.getScaleY());
        this.addActor(credits);

        endBus = new end_bus(parentClass);
        this.addActor(endBus);

        gameOver = new game_over(this, parentClass);
        this.addActor(gameOver);

        resetButton = new reset_button(parentClass, this);
        this.addActor(resetButton);

        creditsButton = new credits_button(parentClass, this);
        this.addActor(creditsButton);

        Gdx.app.log("EndScreen", "Initialized");
    }

    public void use(){
        Gdx.input.setInputProcessor(multiplexer);

        if(parentClass.getPrefs().getInteger("highScore", 0) < parentClass.getGameStage().getTouches())
            parentClass.getPrefs().putInteger("highScore", parentClass.getGameStage().getTouches());
        parentClass.getPrefs().flush();

        if(credits.getZIndex() > frame.getZIndex()){
            int tempZ = credits.getZIndex();
            credits.setZIndex(frame.getZIndex());
            frame.setZIndex(tempZ);
        }

        parentClass.setEndscreen(true);
        parentClass.setGamestage(false);


        gameOver.setPosition(Gdx.graphics.getWidth() / 2 - gameOver.getSprite().getWidth() / 2, 1920 * parentClass.getScaleY());

        endBus.setPosition(-endBus.getSprite().getWidth(), parentClass.getScaleY() * 130);

        gameOver.getMba().reset();
        endBus.getMba().reset();

        gameOver.getMba().setInterpolation(Interpolation.bounceOut);
        endBus.getMba().setInterpolation(Interpolation.swingOut);

        creditsButton.setAlphaToZero();
        resetButton.setAlphaToZero();
        resetButton.setClicked(false);

        gameOver.addAction(gameOver.getMba());
        endBus.addAction(endBus.getMba());

        Gdx.app.log("EndScreen", "Used");
    }
}
