package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class StartButton extends Button {
    private float xPos, yPos;
    public StartButton(float x, float y) {
        xPos = x;
        yPos = y;
        position = new Vector2();
        bound = new Rectangle();
        image = new Texture("start.png");
        bound.set(getXPos(), getYPos(), buttonGetWidth(), buttonGetHeight());
    }
    public float getXPos() {return xPos;}
    public float getYPos() {return yPos;}
    public float buttonGetWidth() {return image.getWidth();}
    public float buttonGetHeight() {return image.getHeight();}
}
