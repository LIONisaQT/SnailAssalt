package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by douglas on 6/18/14.
 */
public class Projectile {
    public Texture agua;
    public Sprite shot;
    public Rectangle waterBounds;
    public Vector2 waterSpeed;

    Projectile(){

        agua = new Texture("water.png");
        shot = new Sprite(agua,0,0,agua.getWidth(),agua.getHeight());

        waterBounds = new Rectangle();
        waterBounds.set(30, 300, agua.getWidth(), agua.getHeight());
        shot.setPosition(waterBounds.x,waterBounds.y);
        waterSpeed= new Vector2();
        waterSpeed.set(6,6);






    }
    public void move(float x,float y){
        waterBounds.x=x;
        waterBounds.y=y;
        shot.setX(x);
        shot.setY(y);


    }


}
