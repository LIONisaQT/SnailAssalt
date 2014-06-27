package com.missionbit.snailassalt;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by vivianlam on 6/24/14.
 */
public class Person extends Enemy {

    public Person(float x, float y, float xSpeed, float ySpeed) {
        super(x, y, xSpeed, ySpeed, "person.png", "person2.png");
        animation = new Animation(0.8f, new TextureRegion(frame1), new TextureRegion(frame2));
        animation.setPlayMode(Animation.PlayMode.LOOP);
    }
}


