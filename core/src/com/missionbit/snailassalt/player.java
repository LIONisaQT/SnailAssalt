package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by douglas on 6/17/14.
 */
public class Player {
    public Texture jimmy;
    public Rectangle jimBounds;
    public int currentWaterGun, squirtGun, superSoaker;
    public Player () {
        jimmy = new Texture("jimmy.png");
        jimBounds = new Rectangle(60,60,jimmy.getWidth(),jimmy.getHeight());
        squirtGun = 0;
        superSoaker = 1;
        currentWaterGun = squirtGun;
    }
    //public Weapon ()
}

