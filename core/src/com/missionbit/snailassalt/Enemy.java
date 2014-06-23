package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {
    public Texture standardSnail;
    public Rectangle standardSnailBound;
    public Vector2 standardSnailSpeed;
    float width, height, xPos, yPos;
    public int hp;
    public Enemy(float x, float y) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        xPos = x;
        yPos = y;
        standardSnail = new Texture("snail.png");
        standardSnailSpeed = new Vector2();
        standardSnailBound = new Rectangle((float)Math.random() * width, (float)Math.random() * height, standardSnail.getWidth(), standardSnail.getHeight());
        standardSnailSpeed.set(5, 5);
    }

}