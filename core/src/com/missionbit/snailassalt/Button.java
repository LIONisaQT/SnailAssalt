package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class Button {
    public Texture buttonImage;
    public Rectangle buttonBound;
    public Vector2 buttonPosition;
    public Button() {
        buttonImage = new Texture("badlogic.jpg");
        buttonPosition = new Vector2();
    }
}