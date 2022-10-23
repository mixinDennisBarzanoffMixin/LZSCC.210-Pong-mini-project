package com.mygdx.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.helpers.BodyHelper;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.ContactType;
import com.mygdx.screens.GameScreen;



public class Player2 extends PlayerPaddle {
	private int height;
	private GameScreen gameScreen;
	public Player2(float x, float y, GameScreen gameScreen) {
		super(x,y,gameScreen);
		this.height = 64;
		this.gameScreen = gameScreen;

		Pixmap pixmap = new Pixmap(Constants.PLAYER_PADDLE_WIDTH, height, Pixmap.Format.RGBA8888);
		pixmap.setBlending(Pixmap.Blending.None);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();

		this.texture = new Texture(pixmap);

		pixmap.dispose();

		// Body creation: paddles are kinematic bodies
		CreateBody(gameScreen, height, ContactType.PLAYER2);
	}
	
	public void update() {
		super.update();
		
		// Direction depends on user input
		int direction = 0;
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			direction = 1;
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			direction = -1;
		
		// Computation of potential new velocity, it depends on the distance to borders
		this.velY = getNewVelocity(direction, Constants.PLAYER_PADDLE_MAX_SPEED);
		
		setNewVelocity(height);

		x = body.getPosition().x * Constants.PPM - (Constants.PLAYER_PADDLE_WIDTH/2);
		y = body.getPosition().y * Constants.PPM - (height/2);

	}
}
