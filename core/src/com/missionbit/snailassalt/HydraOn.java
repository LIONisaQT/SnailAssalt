package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by douglas on 6/24/14.
 */
public class HydraOn extends Button {
    private float width, height;

    public HydraOn(){
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        position = new Vector2();
        bound = new Rectangle();
        image = new Texture("hydraIcon.jpg");
        bound.set(getXPos(), getYPos(), buttonGetWidth(), buttonGetHeight());
    }
    public float getXPos() {return 10;}
    public float getYPos() {return height - 510;}
    public float buttonGetWidth() {return image.getWidth();}
    public float buttonGetHeight() {return image.getHeight();}
    public boolean isPressed() {
        return true;
    }
}
