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
    protected float width, height;
    static public float touch, touchY, deltaX, deltaY, rot, waterScale, waterLimit, waterSupply;;
    static public int str;
    public Weapon(){
        this("watergun arm.png");
    }
    public Weapon(String image) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        sprite = new Sprite(new Texture(image));
        sprite.setPosition(width - 150, height / 2 - 70);
        bound = new Rectangle();
        bound.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        speed = new Vector2();
        speed.set(0, 5);
        touch = 0;
        touchY = 0;
        rot = 0;
        str = 2;
        waterSupply = 200.0f;
        waterLimit = waterSupply;
        waterScale = (waterSupply / waterLimit) * 3;
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

