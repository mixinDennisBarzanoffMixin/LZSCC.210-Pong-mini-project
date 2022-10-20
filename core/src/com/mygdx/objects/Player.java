package com.mygdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.helpers.BodyHelper;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.ContactType;
import com.mygdx.screens.GameScreen;

public class Player extends PlayerPaddle {
	
	public Player(float x, float y, GameScreen gameScreen) {
		
		super(x,y,gameScreen);
		
		Pixmap pixmap = new Pixmap(Constants.PLAYER_PADDLE_WIDTH, Constants.PLAYER_PADDLE_HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        
		this.texture = new Texture(pixmap);
		
		pixmap.dispose();
		
		// Body creation: paddles are kinematic bodies
		this.body = BodyHelper.createRectangularBody(x, y, Constants.PLAYER_PADDLE_WIDTH, Constants.PLAYER_PADDLE_HEIGHT, BodyType.KinematicBody, 1f, gameScreen.getWorld(), ContactType.PLAYER);
	}
	
	public void update() {
		//super.update();
		
		// Direction depends on user input
		int direction = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			direction = 1;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			direction = -1;
		
		// Computation of potential new velocity, it depends on the distance to borders
		this.velY = getNewVelocity(direction, Constants.PLAYER_PADDLE_MAX_SPEED);
		
		setNewVelocity(Constants.PLAYER_PADDLE_HEIGHT);
		
		x = body.getPosition().x * Constants.PPM - (Constants.PLAYER_PADDLE_WIDTH/2);
		y = body.getPosition().y * Constants.PPM - (Constants.PLAYER_PADDLE_HEIGHT/2);
					
	}
	
	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(texture, x, y, Constants.PLAYER_PADDLE_WIDTH, Constants.PLAYER_PADDLE_HEIGHT);
	}

}
