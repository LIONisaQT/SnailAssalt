package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by douglas on 6/24/14.
 */
public class HydraOn extends Button {
    public boolean state;
    public HydraOn(float x, float y){
        super(x, y);
        image = new Texture("hydraIcon.jpg");
        state = false;


    }
    public boolean isPressed() {return state;}

}
