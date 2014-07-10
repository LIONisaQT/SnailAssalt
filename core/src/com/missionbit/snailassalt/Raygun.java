package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by mkahney on 7/9/14.
 */
public class Raygun extends Weapon {
    public Raygun() {
        super("raygun.png");
        str = 15;
    }

    public void Update(ArrayList<ThrowyThingy> water) {
        if (Gdx.input.justTouched()) {
            touch = SnailAssalt.getTapPosition().x;
            touchY = SnailAssalt.getTapPosition().y;
            deltaX = touch - sprite.getX();
            deltaY = touchY - sprite.getY();
            rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
            sprite.setRotation(rot);
            Gdx.app.log("rot",""+rot);
            ThrowyThingy proj = new ThrowyThingy();
            water.add(proj);
            proj.bound.setPosition(this.bound.x, this.bound.y);
            proj.speed.setAngleRad(MathUtils.degreesToRadians * rot);
            waterLimit--;
        }
    }

}


