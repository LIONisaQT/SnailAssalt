package com.missionbit.snailassalt;

/**
 * Created by douglas on 7/31/14.
 */
public class HoseButton extends Button {
    public HoseButton(float x, float y) {
        super(x, y, "images/buttons/hoseIcon.png", "images/buttons/hoseIcon.png");
        sprite.setSize(buttonGetWidth() / 2, buttonGetHeight() / 2);
        bound.setSize(buttonGetWidth() , buttonGetHeight() );

    }
}

