package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by douglas on 6/18/14.
 */
public class Projectile {
    public Texture agua;
    public Sprite shot;
    public Rectangle bound;
    public Vector2 speed;
    public Projectile() {
        agua = new Texture("water.png");
        shot = new Sprite(agua,0,0,agua.getWidth(),agua.getHeight());
        bound = new Rectangle();
        bound.set(30, 300, agua.getWidth(), agua.getHeight());
        shot.setPosition(bound.x, bound.y);
        speed = new Vector2();
        speed.set(6, 6);
    }
    public void move(float x,float y) {
        bound.x=x;
        bound.y=y;
        shot.setX(x);
        shot.setY(y);
    }
    public void Update() {
        this.move(this.bound.x + this.speed.x, this.bound.y + this.speed.y);
    }
}
