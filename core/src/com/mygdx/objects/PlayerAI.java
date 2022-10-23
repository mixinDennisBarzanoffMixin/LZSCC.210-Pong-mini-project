package com.mygdx.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.helpers.BodyHelper;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.ContactType;
import com.mygdx.screens.GameScreen;

public class PlayerAI extends PlayerPaddle {
	
	private int height;
	// The AI needs to remember the game screen to keep track of the ball
	private GameScreen gameScreen;
	
	public PlayerAI(float x, float y, GameScreen gameScreen) {
		super(x, y, gameScreen);
		this.height = 64;

		this.gameScreen = gameScreen;
		
		Pixmap pixmap = new Pixmap(Constants.AI_PADDLE_WIDTH, height, Pixmap.Format.RGBA8888);
		pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

		this.texture = new Texture(pixmap);

		pixmap.dispose();

		// Body creation: paddles are kinematic bodies
		this.body = BodyHelper.createRectangularBody(x, y, Constants.AI_PADDLE_WIDTH, height, BodyType.KinematicBody, 1f, gameScreen.getWorld(), ContactType.AI);
	}

	public void AIPaddleHeight(int newheight) {
		this.height = newheight;
	}
	

	@Override
	public void update() {
		//super.update();
		
		// Gets the ball to check its position
		Ball ball = gameScreen.getBall();
		
		// Movement direction depends on the difference in y coordinate with the ball
		int direction = 0;
		
		if(ball.getY() > this.y)
			direction = 1;
		if(ball.getY() < this.y)
			direction = -1;
		
		// Potentail new velocity if not too close to borders
		this.velY = getNewVelocity(direction, Constants.AI_PADDLE_MAX_SPEED);
		
		setNewVelocity(height);

		x = body.getPosition().x * Constants.PPM - (Constants.AI_PADDLE_WIDTH/2);
		y = body.getPosition().y * Constants.PPM - (height/2);
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, x, y, Constants.AI_PADDLE_WIDTH, height);
	}
	
	
}
