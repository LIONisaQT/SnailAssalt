package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by douglas on 6/23/14.
 */
public class Hydra extends Weapon {
    public Hydra()
    {
        super("hydra.png");
    }
    public boolean on(){
        return true;
    }



    public void Update(ArrayList<Projectile> water) {
            if (Gdx.input.justTouched()) {
                touch = SnailAssalt.getTapPosition().x;
                touchY = SnailAssalt.getTapPosition().y;
                deltaX = touch - sprite.getX();
                deltaY = touchY - sprite.getY();
                rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
                sprite.setRotation(rot);
                Projectile proj = new Projectile();
                water.add(proj);
                proj.bound.setPosition(this.bound.x, this.bound.y);
                proj.speed.setAngleRad(MathUtils.degreesToRadians * rot);
                Projectile proj2=new Projectile();
                water.add(proj2);
                proj2.bound.setPosition(this.bound.x, this.bound.y);
                proj2.speed.setAngleRad(MathUtils.degreesToRadians * (rot-15));
                Projectile proj3=new Projectile();
                water.add(proj3);
                proj3.bound.setPosition(this.bound.x, this.bound.y);
                proj3.speed.setAngleRad(MathUtils.degreesToRadians * (rot+15));
            }
    }
}



