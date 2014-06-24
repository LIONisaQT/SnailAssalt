package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by douglas on 6/18/14.
 */
public class Weapon {

    public Sprite sprite;
    public Rectangle bound;
    public Vector2 speed;
    public float touch, touchY, deltaX, deltaY, rot;
    public Weapon(){
        this("waterGun.png");
    }
    public Weapon (String image) {
        sprite = new Sprite(new Texture(image));
        sprite.setPosition(300, 30);
        bound = new Rectangle();
        speed = new Vector2();
        bound.set(300, 30, sprite.getWidth(), sprite.getHeight());
        speed.set(0, 5);
        touch = 0;
        touchY = 0;
        rot = 0;
    }

    public void GunPosition(float a, float b){
        bound.x = a;
        bound.y = b;
        sprite.setX(a);
        sprite.setY(b);
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
        }
    }
}
