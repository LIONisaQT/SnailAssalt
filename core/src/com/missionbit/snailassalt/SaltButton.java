package com.missionbit.snailassalt;
/**
 * Created by douglas on 7/8/14.
 */
public class SaltButton extends Button {
    public SaltButton(float x ,float y) {
        super(x, y, "images/buttons/saltIcon.png", "images/buttons/saltIcon.png");
        sprite.setSize(buttonGetWidth() / 2, buttonGetHeight() / 2);
        bound.setSize(buttonGetWidth() , buttonGetHeight() );
    }
}


