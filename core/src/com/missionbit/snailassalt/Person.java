package com.missionbit.snailassalt;
/**
 * Created by vivianlam on 6/24/14.
 */
public class Person extends Enemy {
    public Person(float x, float y, float xSpeed, float ySpeed,float attack,float hp) {
        super(x, y, xSpeed, ySpeed,attack,hp, "images/enemies/person.png", "images/enemies/person2.png");
    }
}


