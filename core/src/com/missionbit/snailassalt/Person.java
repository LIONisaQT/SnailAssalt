package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 * Created by vivianlam on 6/24/14.
 */
public class Person extends Enemy {
    public Person(float x, float y, float xSpeed, float ySpeed,float attack,float hp) {
        super(x, y, xSpeed, ySpeed,attack,hp, "person.png", "person2.png");
    }
}


