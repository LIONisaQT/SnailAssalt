package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {

    public Texture snail;
    public Rectangle snailBound;

    public Enemy(){
        snail = new Texture("snail.png");
        snailBound = new Rectangle(50,50,snail.getWidth(),snail.getHeight());
    }


}
