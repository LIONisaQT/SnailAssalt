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
        super("images/weapons/hydraArm.png");
    }

    public boolean on(int x) {
        if (x == 1)
            return true;
        else
            return false;
    }

    public void Update(ArrayList<ThrowyThingy> water) {
        if (Gdx.input.justTouched() && currentWater>= 3 &&  !SnailAssalt.hydraButton.isPressed() && !SnailAssalt.saltButton.isPressed()) {
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
            ThrowyThingy proj2=new ThrowyThingy();
            water.add(proj2);
            proj2.bound.setPosition(this.bound.x, this.bound.y);
            proj2.speed.setAngleRad(MathUtils.degreesToRadians * (rot-15));
            ThrowyThingy proj3=new ThrowyThingy();
            water.add(proj3);
            proj3.bound.setPosition(this.bound.x, this.bound.y);
            proj3.speed.setAngleRad(MathUtils.degreesToRadians * (rot+15));
            currentWater -= 3;
        }
    }

    public void Update2(ArrayList<SaltProjectile> shakers){
        if(!enableSalt) {return;}
        if (Gdx.input.justTouched() && currentSalt > 0 && SnailAssalt.bulletType==SnailAssalt.BulletType.SALT && !SnailAssalt.saltButton.isPressed() && !SnailAssalt.hydraButton.isPressed()) {
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
            currentSalt--;
        }
    }
}