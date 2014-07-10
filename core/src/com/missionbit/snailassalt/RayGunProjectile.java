package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by mkahney on 7/7/14.
 */
public class RayGunProjectile extends ThrowyThingy {
    public  RayGunProjectile(){
        image=new Texture("raygunprojectile.png");
        sprite = new Sprite(image,image.getWidth(),image.getHeight());
        bound = new Rectangle();
        bound.set(30, 300, image.getWidth(), image.getHeight());
        sprite.setPosition(bound.x, bound.y);
        speed = new Vector2();
        speed.set(6, 6);
        sprite.rotate(Weapon.rot*-1);


    }

}
