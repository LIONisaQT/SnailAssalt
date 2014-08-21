package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class LevelButton {
    protected Texture tempPic;
    protected float xPos, yPos;
    protected Rectangle bound;
    protected Vector2 position;
    protected float width, height;
    protected Sprite sprite;
    public boolean on;
    ArrayList<TextureRegion> levelButtonImages;
    public LevelButton(float x, float y) {
        tempPic= new Texture("images/buttons/level10.png");
        sprite=new Sprite(new TextureRegion(new Texture("images/buttons/levels1-10.png")));
        xPos = x+(Gdx.graphics.getWidth() - sprite.getWidth())/2;
        yPos = y+(Gdx.graphics.getHeight() - sprite.getHeight())/2;
        position = new Vector2();
        bound = new Rectangle();
        bound.set(getXPos(), getYPos(), this.buttonGetWidth(), this.buttonGetHeight());
        levelButtonImages = new ArrayList<TextureRegion>();
        for (int a = 0; a < SnailAssalt.numberOfLevels; a++) {
            if (a < 5) {levelButtonImages.add(new TextureRegion(sprite, a * 200, 0, tempPic.getWidth(), tempPic.getHeight()));}
            else if (a >= 5) {levelButtonImages.add(new TextureRegion(sprite, (a - 5) * 200, 200, tempPic.getWidth(), tempPic.getHeight()));}
        }
    }

    public TextureRegion getButtonImage(int level) {return levelButtonImages.get(level - 1);}

    public float getXPos() {return xPos;}

    public float getYPos() {return yPos;}

    public float buttonGetWidth() {return tempPic.getWidth();}

    public float buttonGetHeight() {return tempPic.getHeight();}

    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.INGAME;}

    public boolean isPressed() {
        return (Gdx.input.justTouched() && this.bound.contains(SnailAssalt.getTapPosition().x,SnailAssalt.getTapPosition().y));
    }
}