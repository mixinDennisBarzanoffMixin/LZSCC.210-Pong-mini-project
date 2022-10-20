package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.FancyFontHelper;
import com.mygdx.helpers.GameContactListener;
import com.mygdx.helpers.ScreenType;
import com.mygdx.objects.*;
import com.mygdx.pong.PongGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

// This is the main game screen
public class GameScreen extends ScreenAdapter{

	private OrthographicCamera camera;
	private SpriteBatch batch;
	// World is needed within the Box2D library 
	private World world;
	// Uncomment for debugging
	//private Box2DDebugRenderer debugRenderer;
	private BitmapFont font;
	
	//objects
	private Player player;
	private PlayerAI ai;
	private Ball ball;
	private Wall upper;
	private Wall lower;

	private List<MysteryBox> boxes;

	public List<MysteryBox> getBoxes() {
		return boxes;
	}

	private Random boxRandomness;
	
	public GameScreen(OrthographicCamera camera) {
		Box2D.init();
		
		this.camera = camera;
		this.camera.position.set(new Vector3(PongGame.getInstance().getWindowWidth()/2, PongGame.getInstance().getWindowHeight()/2, 0));
		this.batch = new SpriteBatch();
		this.world = new World(new Vector2(0, 0), false);
		
		// Uncomment for debugging
		//this.debugRenderer = new Box2DDebugRenderer();
		
		this.world.setContactListener(new GameContactListener(this)); // Contact listener initialisation

		// Creation of all the required entities
		this.player = new Player(16, PongGame.getInstance().getWindowHeight() / 2, this);
	
		this.ai = new PlayerAI(PongGame.getInstance().getWindowWidth() - 16, PongGame.getInstance().getWindowHeight() / 2, this);
		
		this.ball = new Ball(this);
		
		this.upper = new Wall(PongGame.getInstance().getWindowHeight() - (Constants.UPPER_WALL_SIZE/2), Constants.UPPER_WALL_SIZE, this);
		
		this.lower = new Wall((Constants.LOWER_WALL_SIZE/2), Constants.LOWER_WALL_SIZE, this);
		
		this.font = FancyFontHelper.getInstance().getFont(Color.WHITE, 40);

		this.boxRandomness = new Random(12);

		this.boxes = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			this.boxes.add(new MysteryBox(this, boxRandomness));
		}
	}
	
	public void update() {
		this.world.step(1/60f, 6, 2);
		
		// updating all required bodies
		
		this.camera.update();
		
		this.player.update();
		
		this.ai.update();
		
		this.ball.update();

		final List<MysteryBox> intersecting = boxes.stream()
				.filter(box -> Intersector.overlaps(this.getBall().getHitbox(), box.getHitbox()))
				.collect(Collectors.toList());
		intersecting.forEach(box -> {
			this.boxes.remove(box);
			onPowerUpShouldActivate(box);
		});

		this.batch.setProjectionMatrix(this.camera.combined);
		
		// Reset button in case the ball gets stuck horizontally 
		if(Gdx.input.isKeyPressed(Input.Keys.R))
			this.ball.reset();
		
		// To return to the menu screen
		if(Gdx.input.isKeyPressed(Input.Keys.M))
			PongGame.getInstance().changeScreen(this, ScreenType.MENU);

		// The ball is reset to the centre if it goes our of the screen
		// player or AI scores are updated accordingly
		if(this.ball.getX() + 3*this.ball.getRadius() < 0) {
			this.ai.updateScrore();
			this.ball.reset();
		}
		
		if(this.ball.getX() - 3*this.ball.getRadius() > PongGame.getInstance().getWindowWidth()) {
			this.player.updateScrore();
			this.ball.reset();
		}
		
		// Checks if the game is over, and transitions to the end game screen
		if(hasPlayerWon() || hasAIWon())
			PongGame.getInstance().changeScreen(this, ScreenType.END_GAME, getWinnerMessage());
		
	}

	void onPowerUpShouldActivate(MysteryBox box) {
		// TODO: implement
		System.out.println("On powerup should activate");
	}

	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Simple rendering of bodies and scores


		this.batch.begin();

		for (final MysteryBox box : boxes) {
			box.render(batch);
		}

		this.player.render(batch);
		
		this.ai.render(batch);
		
		this.ball.render(batch);
		
		this.upper.render(batch);
		
		this.lower.render(batch);
		
		this.font.draw(batch, ""+this.player.getScore(), 50, PongGame.getInstance().getWindowHeight() - Constants.UPPER_WALL_SIZE/2 + 15);
		
		this.font.draw(batch, ""+this.ai.getScore(), PongGame.getInstance().getWindowWidth() - 50, PongGame.getInstance().getWindowHeight() - Constants.UPPER_WALL_SIZE/2 + 15);
		
		this.batch.end();
		
		// Uncomment for debugging
		//this.debugRenderer.render(world, camera.combined.scl(Constants.PPM));
	}

	// Getter methods for world and ball
	public World getWorld() {
		return world;
	}
	
	public Ball getBall() {
		return this.ball;
	}

	// Private auxiliary method to check winning conditions and create the end game message
	private boolean hasPlayerWon() {
		return this.player.getScore() == Constants.END_SCORE;
	}
	
	private boolean hasAIWon() {
		return this.ai.getScore() == Constants.END_SCORE;
	}
	
	private String getWinnerMessage() {
		return hasPlayerWon() ? 
				"You Won!\n  " + this.player.getScore() + " - " + this.ai.getScore() :
				"You Lost!\n  " + this.player.getScore() + " - " + this.ai.getScore();
	}
}
