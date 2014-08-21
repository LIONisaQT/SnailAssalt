package com.missionbit.snailassalt;
/**
 * Created by vivianlam on 6/24/14.
 */
public class Zombie extends Enemy {
    public Zombie(float x, float y, float xSpeed, float ySpeed, float attack, float hp) {
        super(x, y, xSpeed, ySpeed,attack,hp, "images/enemies/zombie.png", "images/enemies/zombie2.png");
    }
}


