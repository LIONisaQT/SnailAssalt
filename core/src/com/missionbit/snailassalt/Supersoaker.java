package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
/**
 * Created by mkahney on 6/24/14.
 */
public class Supersoaker extends Weapon {
    public Supersoaker() {
        super("supersoaker.png");
        sprite = new Sprite(new Texture("supersoaker.png"));
        sprite.setPosition(300, 30);
        bound = new Rectangle();
        speed = new Vector2();
        bound.set(300, 30, sprite.getWidth(), sprite.getHeight());
        speed.set(0, 20);
        touch = 0;
        touchY = 0;
        rot = 0;
    }
    public boolean on() {
        return true;
    }
    public void Update(ArrayList<ThrowyThingy> water) {
        if (Gdx.input.justTouched()) {
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
            ThrowyThingy proj2 = new ThrowyThingy();
            water.add(proj2);
            proj2.bound.setPosition(this.bound.x, this.bound.y);
            proj2.speed.setAngleRad(MathUtils.degreesToRadians * (rot - 15));
            ThrowyThingy proj3 = new ThrowyThingy();
            water.add(proj3);
            proj3.bound.setPosition(this.bound.x, this.bound.y);
            proj3.speed.setAngleRad(MathUtils.degreesToRadians * (rot + 15));
        }
    }
}
