package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by douglas on 6/17/14.
 */
public class Player {
    public Texture jim;
    public Sprite jimmy;
    public Rectangle jimBounds;
    public int currentWaterGun, squirtGun, superSoaker;
    public Player () {
        jim = new Texture("jimmy.png");
        jimmy = new Sprite(jim,60,60,jim.getWidth(),jim.getHeight());
        jimBounds = new Rectangle(60,60,jimmy.getWidth(),jimmy.getHeight());
        squirtGun = 0;
        superSoaker = 1;
        currentWaterGun = squirtGun;
    }
    public void playerPosition(float c,float d){
        jimBounds.setX(c);
        jimBounds.setY(d);
        jimmy.setX(c);
        jimmy.setY(d);
    }
}

