package com.missionbit.snailassalt;
/**
 * Created by douglas on 6/24/14.
 */
public class HydraButton extends Button {
    public HydraButton(float x, float y) {
        super(x, y, "images/buttons/weaponIcon.png", "images/buttons/weaponIcon.png");
        sprite.setSize(getWidth() / 2, getHeight() / 2);
        bound.setSize(getWidth(), getHeight());
    }
}
