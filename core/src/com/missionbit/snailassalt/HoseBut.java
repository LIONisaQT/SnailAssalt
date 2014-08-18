package com.missionbit.snailassalt;

/**
 * Created by douglas on 7/31/14.
 */
public class HoseBut extends Button {
    public HoseBut(float x, float y) {
        super(x, y, "images/buttons/hose icon.png", "images/buttons/hose icon.png");
        sprite.setSize(buttonGetWidth() / 2, buttonGetHeight() / 2);
        bound.setSize(buttonGetWidth() , buttonGetHeight() );

    }
}

