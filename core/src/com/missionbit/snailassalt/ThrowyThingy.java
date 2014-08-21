package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by douglas on 6/18/14.
 */
public class ThrowyThingy {
    public Texture image;
    public Sprite sprite;
    public Rectangle bound;
    public Vector2 speed;
    public boolean enable;
    public ThrowyThingy() {
        image = new Texture("images/weapons/water.png");
        sprite = new Sprite(image,0,0, image.getWidth(), image.getHeight());
        bound = new Rectangle();
        bound.set(30, 300, image.getWidth(), image.getHeight());
        sprite.setPosition(bound.x, bound.y);
        speed = new Vector2();
        speed.set(6, 6);
    }

    public void move(float x,float y) {
        bound.x = x;
        bound.y = y;
        sprite.setX(x);
        sprite.setY(y);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void Update() {
        this.move(this.bound.x + this.speed.x, this.bound.y + this.speed.y);
    }
}