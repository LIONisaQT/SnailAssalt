package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    public Button(float x, float y) {
        image = new Texture("badlogic.jpg");
        xPos = x;
        yPos = y;
        position = new Vector2();
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), buttonGetWidth(), buttonGetHeight());
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
    }
    public void draw(SpriteBatch batch) {
        batch.draw(this.image, this.position.x, this.position.y);
    }
    public float getXPos() {return xPos;}
    public float getYPos() {return yPos;}
    public float buttonGetWidth() {return image.getWidth();}
    public float buttonGetHeight() {return image.getHeight();}
}