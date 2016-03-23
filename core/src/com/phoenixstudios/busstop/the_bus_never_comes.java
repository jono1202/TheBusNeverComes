package com.phoenixstudios.busstop;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

public class the_bus_never_comes extends Game implements InputProcessor{

	private InputMultiplexer gameMultiplexer, startMultiplexer, endMultiplexer;

	public InputMultiplexer getGameMultiplexer() {
		return gameMultiplexer;
	}

	public InputMultiplexer getStartMultiplexer() {
		return startMultiplexer;
	}

	public InputMultiplexer getEndMultiplexer() {
		return endMultiplexer;
	}

	private Random rand;

	private boolean showBack = false;

	private Boolean gamestage, startscreen, endscreen;

	private float scaleX;

	private float scaleY;

	private Sound clickedItem, lose;
	private Music bgm;

	private Stage backStage;

	private SpriteBatch batch;
	private Texture img;
	private game_stage gameStage;
	private Sprite background;
	private falling_object falling_objects_temp;
	private man man;
	private Stage startScreen;
	private end_screen endScreen;
	private TextureAtlas buttons;
	private TextureAtlas objects;
	private BitmapFont pixelFont;
	private Integer frameBuffer = 0;
	private Preferences prefs;
	private sound_toggle soundToggle;

	private int lens;

	public TextureAtlas.AtlasRegion getRegion(){
		return objects.findRegion(Integer.toString(rand.nextInt(6)+1));
	}

	@Override
	public void dispose() {
		backStage.dispose();
		startScreen.dispose();
		gameStage.dispose();
		endScreen.dispose();
		img.dispose();
		man.remove();
	}

	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("My Preferences");
		bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/bgm.mp3"));
		bgm.play();
		if(!prefs.getBoolean("sound", true)){
			bgm.pause();
		}
		bgm.setLooping(true);

		clickedItem = Gdx.audio.newSound(Gdx.files.internal("sounds/beep.mp3"));
		lose = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.mp3"));

		rand = new Random();

		objects = new TextureAtlas(Gdx.files.internal("spriteSheets/objects/objects.txt"));

		scaleX = Gdx.graphics.getWidth()/1080f;
		scaleY = Gdx.graphics.getHeight()/1920f;

		backStage = new Stage(new ScreenViewport());
		man = new man(batch ,backStage, this);
		man.setName("man");
		backStage.addActor(man);
		soundToggle = new sound_toggle(this);
		backStage.addActor(soundToggle);

		pixelFont = new BitmapFont(Gdx.files.internal("pixelFont.fnt"));
		pixelFont.getData().setScale(3*((scaleX+scaleY)/2));

		startscreen = true;
		startScreen = new Stage(new ScreenViewport());

		start_button play_button = new start_button(startScreen, this);
		startScreen.addActor(play_button);
		buttons = new TextureAtlas(Gdx.files.internal("spriteSheets/buttons/pack.atlas"));
		Image instructions = new Image(buttons.findRegion("gamerule"));
		instructions.setSize(1000 * scaleX, 500 * scaleY);
		instructions.setPosition((Gdx.graphics.getWidth() - instructions.getWidth()) / 2, 950 * scaleY);
		startScreen.addActor(instructions);

		endscreen = false;

		gamestage = false;
		gameStage = new game_stage(new ScreenViewport(), this);
		gameStage.setTouchesToZero();

		batch = new SpriteBatch();
		img = new Texture("spritesMisc/better_background.png");
		background = new Sprite(img);
		background.setOrigin(0f, 0f);
		background.setScale(gameStage.getWidth() / 640f, gameStage.getHeight() / 961);
		background.setPosition(0f, 0f);

		endScreen = new end_screen(new ScreenViewport(), this);

		gameMultiplexer = new InputMultiplexer( gameStage, backStage, this);
		startMultiplexer = new InputMultiplexer(startScreen, backStage);
		endMultiplexer = new InputMultiplexer(endScreen, backStage);

		Gdx.input.setInputProcessor(startMultiplexer);
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.draw(batch);
		pixelFont.draw(batch, Integer.toString(gameStage.getTouches()), 30f*scaleX, Gdx.graphics.getHeight()-pixelFont.getCapHeight()+20*scaleY);
		batch.end();

		backStage.draw();

		if(startscreen){

			startScreen.act(Gdx.graphics.getDeltaTime());
			startScreen.draw();

		}
		else if(gamestage){
			if(frameBuffer > 120){
				gameStage.updateValues();
				if(gameStage.getCurrentFrame() % gameStage.getIntervalOfObjects() == 0) {
					lens = gameStage.getAmountOfObjects();
					for(int i = 0; i<lens; i++){
						falling_objects_temp = new falling_object(gameStage, man, this);
						gameStage.addActor(falling_objects_temp);
						falling_objects_temp.setName(Integer.toString(i + 1));
					}
				}
			}
			else if(gameStage.getTimeTried() > 0){
				endScreen.act(Gdx.graphics.getDeltaTime());
			}

			gameStage.act(Gdx.graphics.getDeltaTime());
			gameStage.draw();

			frameBuffer++;
		}
		else if(endscreen){
			endScreen.act(Gdx.graphics.getDeltaTime());
			endScreen.draw();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 temp = new Vector2(screenX, screenY);
		Vector2 coords = gameStage.screenToStageCoordinates(temp);
		Actor hitActor = gameStage.hit(coords.x, coords.y, false);
		if(hitActor != null){
			if(hitActor.getName()!="man") {
				while (hitActor != null) {
					clickedItem.play();
					gameStage.touchesAddOne();
					man.setSprite(1);
					Gdx.app.log("Hit", "Something");
					hitActor.remove();
					hitActor = gameStage.hit(coords.x, coords.y, true);
				}
			}
		}
		return true;
	}

	public Random getRand() {
		return rand;
	}

	public TextureAtlas getObjects() {
		return objects;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	public boolean isShowBack() {
		return showBack;
	}

	public void setShowBack(boolean showBack) {
		this.showBack = showBack;
	}

	public Preferences getPrefs() {
		return prefs;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public Stage getBackStage() {
		return backStage;
	}

	public void setBackStage(Stage backStage) {
		this.backStage = backStage;
	}

	public Boolean getGamestage() {
		return gamestage;
	}

	public void setGamestage(Boolean gamestage) {
		this.gamestage = gamestage;
	}

	public Boolean getStartscreen() {
		return startscreen;
	}

	public void setStartscreen(Boolean startscreen) {
		this.startscreen = startscreen;
	}

	public Boolean getEndscreen() {
		return endscreen;
	}

	public void setEndscreen(Boolean endscreen) {
		this.endscreen = endscreen;
	}

	public game_stage getGameStage() {
		return gameStage;
	}

	public void setGameStage(game_stage gameStage) {
		this.gameStage = gameStage;
	}

	public Stage getStartScreen() {
		return startScreen;
	}

	public void setStartScreen(Stage startScreen) {
		this.startScreen = startScreen;
	}

	public end_screen getEndScreen() {
		return endScreen;
	}

	public void setEndScreen(end_screen endScreen) {
		this.endScreen = endScreen;
	}

	public com.phoenixstudios.busstop.man getMan() {
		return man;
	}

	public BitmapFont getPixelFont() { return pixelFont; }

	public void setFrameBuffer(Integer frameBuffer) {
		this.frameBuffer = frameBuffer;
	}

	public Music getBgm() {
		return bgm;
	}

	public Sound getLose() {
		return lose;
	}
}
