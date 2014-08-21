package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by vivianlam on 8/5/14.
 */
public class SnailInfoButtons {
    protected Texture tempPic;
    protected float xPos, yPos;
    protected Rectangle bound;
    protected Vector2 position;
    protected float width, height;
    protected Sprite sprite;
    public boolean on;
    ArrayList<TextureRegion> snailImages;
    public SnailInfoButtons(float x, float y) {
        tempPic= new Texture("images/buttons/level10.png");
        sprite=new Sprite(new TextureRegion(new Texture("images/buttons/snailInfo.png")));
        xPos = x+(Gdx.graphics.getWidth() - sprite.getWidth())/2;
        yPos = y+(Gdx.graphics.getHeight() - sprite.getHeight())/2;
        position = new Vector2();
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), this.buttonGetWidth(), this.buttonGetHeight());
        snailImages = new ArrayList<TextureRegion>();
        for (int b = 0; b < SnailAssalt.numberOfTypes; b++) {
            if (b < 5) {snailImages.add(new TextureRegion(sprite, b * 200, 0, tempPic.getWidth(), tempPic.getHeight()));}
            else if (b >= 5) {snailImages.add(new TextureRegion(sprite, (b - 5) * 200, 200, tempPic.getWidth(), tempPic.getHeight()));}
        }
    }

    public TextureRegion getButtonImage(int type) {return snailImages.get(type - 1);}

    public float getXPos() {return xPos;}

    public float getYPos() {return yPos;}

    public float buttonGetWidth() {return tempPic.getWidth();}

    public float buttonGetHeight() {return tempPic.getHeight();}

    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.INFO;}

    public boolean isPressed() {
        return (Gdx.input.justTouched() && this.bound.contains(SnailAssalt.getTapPosition().x,SnailAssalt.getTapPosition().y));
    }
}