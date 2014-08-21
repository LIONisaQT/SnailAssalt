package com.missionbit.snailassalt;
/**
 * Created by vivianlam on 6/23/14.
 */
public class HealerSnail extends Enemy {
    public HealerSnail(float x, float y, float xSpeed, float ySpeed,float attack,float hp) {
        super(x, y, xSpeed, ySpeed, attack,hp, "images/enemies/healerSnail.png", "images/enemies/healerSnail2.png");
    }
}
