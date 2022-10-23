package com.mygdx.screens;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.objects.Player2;
import com.mygdx.objects.PlayerAI;
import com.mygdx.objects.PlayerPaddle;
import com.mygdx.pong.PongGame;

public class PlayerGame extends GameScreen {
	public PlayerGame(OrthographicCamera camera) {
		super(camera);
	}

	@Override
	protected PlayerPaddle initOtherPlayer() {
		return  new Player2(PongGame.getInstance().getWindowWidth() - 16, PongGame.getInstance().getWindowHeight() / 2, this);		
	}
}