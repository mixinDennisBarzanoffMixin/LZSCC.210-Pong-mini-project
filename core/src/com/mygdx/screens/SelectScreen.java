package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.helpers.FancyFontHelper;
import com.mygdx.helpers.ScreenType;
import com.mygdx.pong.PongGame;

public class SelectScreen extends ScreenAdapter {
	
	private SpriteBatch batch;
	private BitmapFont menu;
	private BitmapFont title;
	
	
	public SelectScreen() {
		
		this.batch = new SpriteBatch();
		this.title = FancyFontHelper.getInstance().getFont(Color.RED, 80);
		this.menu = FancyFontHelper.getInstance().getFont(Color.WHITE, 40);
		
		
	}
	
	// Takes care of user input and screen transitions
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.M))
			PongGame.getInstance().changeScreen(this, ScreenType.MENU);
		
		if(Gdx.input.isKeyPressed(Input.Keys.P))
			PongGame.getInstance().changeScreen(this, ScreenType.GAME);
			
		if(Gdx.input.isKeyPressed(Input.Keys.A))
			PongGame.getInstance().changeScreen(this, ScreenType.GAME2);
					
		
	}
	
	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.batch.begin();
		
		this.title.draw(batch, "Select Game Mode", 10, PongGame.getInstance().getWindowHeight() - 50);
		
		this.menu.draw(batch, getMenuText(), 10, PongGame.getInstance().getWindowHeight() / 2);
		
		//this.menu.draw(batch, getMenuText(), 10, PongGame.getInstance().getWindowHeight() / 2 + 50);
		
		this.batch.end();
		
	}
	
	private String getMenuText() {
		return "Press:\n"
				+ "   P - to play against a player\n"
				+ "   A - to play against AI\n"
				+ "   M - to return to the main menu";
				
	}

}
