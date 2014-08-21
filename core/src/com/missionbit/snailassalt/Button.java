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
    protected Texture image, imageNope, watergun, Shade;
    protected Rectangle bound;
    protected Vector2 position;
    protected Sprite sprite, spriteNope, spriteShade, watergunSprite;
    public Button(float x, float y, String picture, String nope) {
        this(x, y, picture, nope, "images/buttons/bw buttons/bw backButton.png");
    }

    public Button(float x, float y, String picture, String nope, String shade) {
        spriteShade = new Sprite(new Texture(shade));
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        image = new Texture(picture);
        imageNope = new Texture(nope);
        xPos = x;
        yPos = y;
        watergun = new Texture("images/buttons/watergunIcon.png");
        watergunSprite = new Sprite(watergun);
        watergunSprite.setSize(watergunSprite.getWidth() / 2, watergunSprite.getHeight() / 2);
        position = new Vector2();
        sprite = new Sprite(new Texture(picture));
        sprite.setPosition(getXPos(),getYPos());
        sprite.setSize((width / 1196) * image.getWidth(), (height / 720) * image.getHeight());
        spriteNope = new Sprite(new Texture(nope));
        spriteNope.setPosition(getXPos(), getYPos());
        spriteNope.setSize((width / 1196) * image.getWidth(), (height / 720) * image.getHeight());
        spriteShade.setPosition(getXPos(), getYPos());
        spriteShade.setSize((width / 1196) * spriteShade.getWidth(), (height / 720) * spriteShade.getHeight());
        //sprite.setBounds(getXPos(),getYPos(),this.buttonGetWidth(),this.buttonGetHeight());
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), this.buttonGetWidth(), this.buttonGetHeight());
    }

    public float getXPos() {return xPos;}

    public float getYPos() {return yPos;}

    public float buttonGetWidth() {return sprite.getWidth();}

    public float buttonGetHeight() {return sprite.getHeight();}

    public boolean isPressed() {
        return (Gdx.input.isTouched() && this.bound.contains(SnailAssalt.getTapPosition().x, SnailAssalt.getTapPosition().y));
    }

    public boolean touchup() {
        if (!Gdx.input.isTouched() && this.bound.contains(SnailAssalt.getTapPosition().x, SnailAssalt.getTapPosition().y)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean pressable() {return true;} //bro can you even press

    public void pressedAction() {}

    public void draw(SpriteBatch batch) {
        if (pressable()) {
            batch.draw(this.image, this.position.x, this.position.y);
        } else {
            batch.draw(this.imageNope, this.position.x, this.position.y);
        }
    }
}