package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by douglas on 6/18/14.
 */
public class Weapon {

    public Sprite standard;
    public Rectangle gunBounds;
    public Vector2 gunSpeed;

    public Weapon(){
        standard = new Sprite(new Texture("waterGun.png"));
        standard.setPosition(30,30);
        gunBounds = new Rectangle();
        gunSpeed = new Vector2();
        gunBounds.set(300,300, standard.getWidth(), standard.getHeight());
        gunSpeed.set(0,5);

    }
    public void GunPosition(float a, float b){
        gunBounds.x = a;
        gunBounds.y = b;
        standard.setX(a);
        standard.setY(b);
    }
}
