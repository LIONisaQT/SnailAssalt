package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

/**
 * Created by douglas on 7/30/14.
 */
public class Hose extends Weapon {
    public Hose() {
        super("images/weapons/hoseArm.png");
    }

    public void Update(ArrayList<ThrowyThingy> water) {
        if (Gdx.input.isTouched() && currentWater >0&& !SnailAssalt.saltButton.isPressed() && !SnailAssalt.hydraButton.isPressed()) {
            watergunsound.play(1.0f);
            touch = SnailAssalt.getTapPosition().x;
            touchY = SnailAssalt.getTapPosition().y;
            deltaX = touch - sprite.getX();
            deltaY = touchY - sprite.getY();
            rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
            sprite.setRotation(rot);
            ThrowyThingy proj = new ThrowyThingy();
            water.add(proj);
            proj.bound.setPosition(this.bound.x, this.bound.y);
            proj.speed.setAngleRad(MathUtils.degreesToRadians * rot);
            currentWater--;

        }
    }

    public void Update2(ArrayList<SaltProjectile> shakers) {
        if (!enableSalt) {
            return;
        }
        if (Gdx.input.isTouched() && currentSalt >0 && SnailAssalt.bulletType == SnailAssalt.BulletType.SALT && !SnailAssalt.saltButton.isPressed() && !SnailAssalt.hydraButton.isPressed()) {
            touch = SnailAssalt.getTapPosition().x;
            touchY = SnailAssalt.getTapPosition().y;
            deltaX = touch - sprite.getX();
            deltaY = touchY - sprite.getY();
            rot = MathUtils.atan2(deltaY, deltaX) * 180 / MathUtils.PI;
            sprite.setRotation(rot);
            SaltProjectile bullet = new SaltProjectile();
            shakers.add(bullet);
            bullet.bound.setPosition(this.bound.x, this.bound.y);
            bullet.speed.setAngleRad(MathUtils.degreesToRadians * rot);
            currentSalt --;
        }
    }
}

