package com.mygdx.objects;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.helpers.BodyHelper;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.ContactType;
import com.mygdx.screens.GameScreen;

public class PlayerAI extends PlayerPaddle {
	
	// The AI needs to remember the game screen to keep track of the ball
	private GameScreen gameScreen;
	
	public PlayerAI(float x, float y, GameScreen gameScreen) {
		super(x, y, gameScreen);
		
		this.gameScreen = gameScreen;
		
		// Body creation: paddles are kinematic bodies
		this.body = BodyHelper.createRectangularBody(x, y, Constants.AI_PADDLE_WIDTH, Constants.AI_PADDLE_HEIGHT, BodyType.KinematicBody, 1f, gameScreen.getWorld(), ContactType.AI);
	}
	
	@Override
	public void update() {
		super.update();
		
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
		
		setNewVelocity(Constants.AI_PADDLE_HEIGHT);
	}
	
	
}
