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
    protected float width, height, xPos, yPos;
    protected Texture image, imageNope;
    protected Rectangle bound;
    protected Vector2 position;
    protected Sprite sprite, spriteNope;
    public Button(float x, float y, String picture, String nope) {
        this(x, y, picture, nope, "missionbit.png");
    }
    public Button(float x, float y, String picture, String nope, String shape) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        image = new Texture(picture);
        imageNope = new Texture(nope);
        xPos = x;
        yPos = y;
        position = new Vector2();
        sprite = new Sprite(new Texture(picture));
        sprite.setPosition(getXPos(),getYPos());
        sprite.setSize(image.getWidth(), image.getHeight());
        spriteNope = new Sprite(new Texture(nope));
        spriteNope.setPosition(getXPos(), getYPos());
        spriteNope.setSize(imageNope.getWidth(), imageNope.getHeight());
        //sprite.setBounds(getXPos(),getYPos(),this.buttonGetWidth(),this.buttonGetHeight());
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), this.buttonGetWidth(), this.buttonGetHeight());
    }
    public float getXPos() {return xPos;}
    public float getYPos() {return yPos;}
    public float buttonGetWidth() {return sprite.getWidth();}
    public float buttonGetHeight() {return sprite.getHeight();}
    public boolean isPressed() {return (Gdx.input.justTouched() && this.bound.contains(SnailAssalt.getTapPosition().x,SnailAssalt.getTapPosition().y));}
    public boolean pressable() {return true;} //bro can you even press
    public void pressedAction() {}
    public void draw(SpriteBatch batch) {
        if (pressable()) {batch.draw(this.image, this.position.x, this.position.y);}
        else {batch.draw(this.imageNope, this.position.x, this.position.y);}
    }
}