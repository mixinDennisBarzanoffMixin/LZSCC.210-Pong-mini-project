package com.mygdx.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.helpers.Constants;
import com.mygdx.pong.PongGame;
import com.mygdx.screens.GameScreen;


// This is the main PlayerPaddle class
// It represents the basis for both Player and PlayerAI classes
public abstract class PlayerPaddle {
	
	// protected variables so that they are accessible to subclasses
	protected Body body;
	protected float x, y;
	protected int velY, score;
	protected Texture texture;
	
	public PlayerPaddle(float x, float y, GameScreen gameScreen) {
		this.x = x;
		this.y = y;
		this.score = 0;
		
		// Pixmap used to create a texture without using a file
		Pixmap pixmap = new Pixmap(16, 64, Pixmap.Format.RGBA8888);
		pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 16, 64);
		this.texture = new Texture(pixmap);
		pixmap.dispose();
	}
	
	//** for gameScreen2
	
	
	
	
	public void update() {
		x = body.getPosition().x * Constants.PPM - (Constants.PLAYER_PADDLE_WIDTH/2);
		y = body.getPosition().y * Constants.PPM - (Constants.PLAYER_PADDLE_HEIGHT/2);
	}
	
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, x, y, Constants.PLAYER_PADDLE_WIDTH, Constants.PLAYER_PADDLE_HEIGHT);
	}
		
	
	// Setter and getter for score
	public void updateScrore() {
		this.score += 1;
	}

	public int getScore() {
		return this.score;
	}
	
	// Computes the potential new velocity based on where the paddle wants to move and its maximal velocity
	protected int getNewVelocity(int direction, int maxVelocity) {
		int velocity = this.velY;
		return Math.abs(velocity + direction) <= maxVelocity ? velocity + direction : velocity;
	}
	
	// Set new velocity based on the position of the paddle 
	protected void setNewVelocity(int paddleHeight) {
		
		if(tooCloseToBorder(paddleHeight)) {
			this.velY = 0;
			this.body.setLinearVelocity(0,0);
		} else
			body.setLinearVelocity(0, this.velY);
	}
	
	// Check if the paddle is too close to a border
	// This is required as kinematic objects (paddles) ignore collisions with static objects (walls)
	private boolean tooCloseToBorder(int paddleHeight) {
		boolean tooCloseToUpperWall = this.velY > 0 && (this.body.getWorldCenter().y * Constants.PPM ) + (paddleHeight / 2) > PongGame.getInstance().getWindowHeight() - Constants.UPPER_WALL_SIZE - 10;
		boolean tooCloseToLowerWall = this.velY < 0 && (this.body.getWorldCenter().y * Constants.PPM ) - (paddleHeight / 2) < Constants.LOWER_WALL_SIZE + 10;
		return tooCloseToUpperWall || tooCloseToLowerWall;
	}
}
