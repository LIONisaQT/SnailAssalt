package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by douglas on 6/23/14.
 */
public class Hydra extends Weapon {

    public Hydra()
    {
        super("hydra arm.png");
    }
    public boolean on(int x ) {
        if (x == 1) {
            return true;
        } else {
            return false;
        }
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
                waterLimit-=3;
            }
    }

}



