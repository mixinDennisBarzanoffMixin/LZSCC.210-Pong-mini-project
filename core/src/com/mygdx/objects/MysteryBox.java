package com.mygdx.objects;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.helpers.BodyHelper;
import com.mygdx.helpers.Constants;
import com.mygdx.helpers.ContactType;
import com.mygdx.pong.PongGame;
import com.mygdx.screens.GameScreen;

import java.util.Random;

public class MysteryBox {
//    private Body body;
    protected float x, y;
    protected int size;
    protected Texture texture;

    protected Rectangle hitbox;

    public MysteryBox(GameScreen gameScreen, Random seeder) {
        this.size = Constants.MYSTERY_BOX_SIZE;
        final int maxWidth = PongGame.getInstance().getWindowWidth();
        final int maxHeight = PongGame.getInstance().getWindowHeight();
        final int padding = Constants.MYSTERY_BOX_SPAWN_PADDING;
        this.x = seeder.nextInt(maxWidth - size - padding * 2) + padding;
        this.y = seeder.nextInt(maxHeight - size);
//        this.x = 0;
//        this.y = 0;
        final int hitboxPadding = Constants.MYSTERY_BOX_HITBOX_PADDING;
        hitbox = new Rectangle(x + hitboxPadding, y + hitboxPadding, size - hitboxPadding * 2, size - hitboxPadding * 2);

        texture = new Texture("assets/mystery_box.png");

        // The ball is the only dynamic body of the game
//        this.body = BodyHelper.createRectangularBody(x, y, size, size, BodyDef.BodyType.StaticBody, 0.0f, gameScreen.getWorld(), ContactType.BOX);

        // Applies an initial force to start the ball movement
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, size, size);
    }

    // Three getter methods for x, y and radius
    public float getY() {
        return this.y;
    }

    public float getX() {
        return this.x;
    }

    public float getSize() {
        return this.size;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MysteryBox &&
                ((MysteryBox) obj).x == this.x &&
                ((MysteryBox) obj).y == this.y &&
                ((MysteryBox) obj).hitbox.equals(this.hitbox);
    }
}
