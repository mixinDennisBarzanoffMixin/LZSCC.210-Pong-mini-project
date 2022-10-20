package com.mygdx.screens;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.objects.PlayerAI;
import com.mygdx.objects.PlayerPaddle;
import com.mygdx.pong.PongGame;

public class AIGame extends GameScreen {
	public AIGame(OrthographicCamera camera) {
		super(camera);
	}

	@Override
	protected PlayerPaddle initOtherPlayer() {
		return  new PlayerAI(PongGame.getInstance().getWindowWidth() - 16, PongGame.getInstance().getWindowHeight() / 2, this);		
	}
}