package com.missionbit.snailassalt;

/**
 * Created by douglas on 7/31/14.
 */
public class RaygunButton extends Button {
    public RaygunButton(float x, float y) {
        super(x, y, "raygun icon.png", "raygun icon.png");
        sprite.setSize(buttonGetWidth() / 2, buttonGetHeight() / 2);
        bound.setSize(buttonGetWidth() , buttonGetHeight() );

    }
}
