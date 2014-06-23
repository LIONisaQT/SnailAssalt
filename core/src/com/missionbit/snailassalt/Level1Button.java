package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class Level1Button extends Button {
    private float width, height;
    public Level1Button() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        position = new Vector2();
        bound = new Rectangle();
        image = new Texture("level1.png");
        bound.set(getXPos(), getYPos(), buttonGetWidth(), buttonGetHeight());
    }
    public float getXPos() {return 10;}
    public float getYPos() {return height - 410;}
    public float buttonGetWidth() {return image.getWidth();}
    public float buttonGetHeight() {return image.getHeight();}
    public boolean isPressed() {
        return true;
    }
    public int numberOfEnemies() {
        return 5;
    }
}
