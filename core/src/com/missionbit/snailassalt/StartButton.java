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
        buttonPosition = new Vector2();
        buttonBound = new Rectangle();
        buttonImage = new Texture("startButton.png");
        buttonBound.set(getXPos(), getYPos(), buttonGetWidth(), buttonGetHeight());
    }
    public float getXPos() {return xPos;}
    public float getYPos() {return yPos;}
    public float buttonGetWidth() {return buttonImage.getWidth();}
    public float buttonGetHeight() {return buttonImage.getHeight();}
}
