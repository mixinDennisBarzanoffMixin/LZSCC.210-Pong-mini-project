package com.mygdx.helpers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.objects.Ball;
import com.mygdx.screens.GameScreen;

// This class takes care of behavioural changes in case of contacts between bodies
// The class exists to provide an example of the use of the listener
// In this particular case, all that is happening is that the ball speeds up
// whenever it touches a paddle
public class GameContactListener implements ContactListener {

	private GameScreen gameScreen;
	
	
	public GameContactListener(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	//** Set up for GameScreen2

	
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContact(Contact contact) {
		
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if (a == null || b == null)
			return;
		
		if (a.getUserData() == null || b.getUserData() == null)
			return;
		
		//** pick which game screen to play
		
		if (ballContact(a, b) && (playerContact(a, b) || aiContact(a, b))) {
			updateVelocities();
			// Apply an impulse to the ball so that the ball gets faster 
			// whenever it gets in contact with a paddle
		}
		
		//Add player1 and player2 contact
		if (ballContact(a,b) && (playerContact(a,b) || player2Contact(a,b))) {
			updateVelocities();
		}
	}

	void updateVelocities() {
		{
			Ball ball = this.gameScreen.getBall(ContactType.BALL);
			if (ball != null) {
				Vector2 ballVelocity = ball.getLinearVelocity();
				ball.applyImpulse(ballVelocity.setLength(.025f));
			}
		}
		{
			Ball ball = this.gameScreen.getBall(ContactType.BALL_2);
			if (ball != null) {
				Vector2 ballVelocity = ball.getLinearVelocity();
				ball.applyImpulse(ballVelocity.setLength(.025f));
			}
		}
		{
			Ball ball = this.gameScreen.getBall(ContactType.BALL_3);
			if (ball != null) {
				Vector2 ballVelocity = ball.getLinearVelocity();
				ball.applyImpulse(ballVelocity.setLength(.025f));
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
	
	// Auxiliary methods to simplify the if statement in the endContact method
	// This method checks whether the ball is involved
	private boolean ballContact(Fixture a, Fixture b) {
		return a.getUserData() == ContactType.BALL || b.getUserData() == ContactType.BALL || a.getUserData() == ContactType.BALL_2
				|| b.getUserData() == ContactType.BALL_2 || a.getUserData() == ContactType.BALL_3 || b.getUserData() == ContactType.BALL_3;
	}
	
	// This method checks whether the player paddle is involved
	private boolean playerContact(Fixture a, Fixture b) {
		return a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER;
	}
	
	// This method checks whether the AI paddle is involved
	private boolean aiContact(Fixture a, Fixture b) {
		return a.getUserData() == ContactType.AI || b.getUserData() == ContactType.AI;
	}
	//** This method checks whether the Player2 paddle is involved
	private boolean player2Contact(Fixture a, Fixture b) {
		return a.getUserData() == ContactType.PLAYER2 || b.getUserData() == ContactType.PLAYER2;
	}
}
