package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by douglas on 7/8/14.
 */
public class ThingsInStore {
    protected float xPos, yPos;
    protected Rectangle bound;
    protected Vector2 position;
    protected float width, height;
    protected Sprite sprite, spriteNope;
    protected int price;
    public boolean on;

    public ThingsInStore(float x, float y, String picture, String bought, int cost) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        price = cost;
        xPos = x;
        yPos = y;
        sprite = new Sprite(new Texture(picture));
        sprite.setPosition(getXPos(), getYPos());
        sprite.setSize(width / 1196 * width / 9, (height / 720) * height / 10 );
        spriteNope = new Sprite(new Texture(bought));
        spriteNope.setPosition(getXPos(), getYPos());
        spriteNope.setSize(width/1196*width/9,(height/720)*height/10);
        position = new Vector2();
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), sprite.getWidth(), sprite.getHeight());
        on = false;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.sprite, this.position.x, this.position.y);
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public float buttonGetWidth() {
        return sprite.getWidth();
    }

    public float buttonGetHeight() {
        return sprite.getHeight();
    }

    public boolean isPressed() {
        return (Gdx.input.justTouched() && this.bound.contains(SnailAssalt.getTapPosition().x, SnailAssalt.getTapPosition().y));
    }

    public boolean IsPressable() {
        return true;
    }

    public void pressedAction() {
    }
}
