package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by vivianlam on 8/12/14.
 */
public class Animation {
    private float dt;
    private ArrayList<Sprite> sprites;
    public Animation(float v, Sprite img1, Sprite img2) {
        this.dt = v;
        this.sprites = new ArrayList<Sprite>();
        this.sprites.add(img1);
        this.sprites.add(img2);
    }

    public Sprite getKeyFrame(float elapsedTime) {
        int idx = (int)((elapsedTime % (sprites.size() * dt)) / dt);
        return sprites.get(idx);
    }
}