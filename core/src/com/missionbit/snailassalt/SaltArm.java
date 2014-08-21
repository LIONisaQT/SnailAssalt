package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by vivianlam on 8/4/14.
 */
public class SaltArm extends Weapon {
    public SaltArm() {
        super("images/weapons/saltArm.png");
    }

    public void Update2(ArrayList<SaltProjectile> shakers){
        if(!enableSalt){return;}
        if (Gdx.input.justTouched() && currentSalt!=0 && SnailAssalt.bulletType == SnailAssalt.BulletType.SALT && !SnailAssalt.saltButton.isPressed() && !SnailAssalt.hydraButton.isPressed()) {
            sprite.setSize(width/1196, height/720);
            touch = SnailAssalt.getTapPosition().x;
            touchY = SnailAssalt.getTapPosition().y;
            deltaX = touch - sprite.getX();
            deltaY = touchY - sprite.getY();
            rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
            sprite.setRotation(rot);
            SaltProjectile bullet= new SaltProjectile();
            shakers.add(bullet);
            bullet.bound.setPosition(this.bound.x, this.bound.y);
            bullet.speed.setAngleRad(MathUtils.degreesToRadians * rot);
            currentSalt--;
        }
    }
}