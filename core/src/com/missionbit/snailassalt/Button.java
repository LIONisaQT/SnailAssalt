package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class Button {
    protected float xPos, yPos;
    protected Texture image;
    protected Rectangle bound;
    protected Vector2 position;
    protected float width, height;
    public boolean on;
    public Button(float x, float y, String picture) {
        image = new Texture(picture);
        xPos = x;
        yPos = y;
        position = new Vector2();
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), this.buttonGetWidth(), this.buttonGetHeight());
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        on = false;
    }
    public void draw(SpriteBatch batch) {
        batch.draw(this.image, this.position.x, this.position.y);
    }
    public float getXPos() {return xPos;}
    public float getYPos() {return yPos;}
    public float buttonGetWidth() {return image.getWidth();}
    public float buttonGetHeight() {return image.getHeight();}
    public boolean isPressed() {
        return (Gdx.input.justTouched() && this.bound.contains(SnailAssalt.getTapPosition().x,SnailAssalt.getTapPosition().y));
    }
    public void pressedAction() {}
}