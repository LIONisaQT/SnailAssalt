package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by douglas on 6/24/14.
 */
public class Snailshell {
    public Texture image;
    public Sprite  sprite;
    public Rectangle bounds;
    public Snailshell(){
       image = new Texture("standardshell.png");
       sprite = new Sprite(image, 0,0,image.getWidth(),image.getHeight());
       bounds = new Rectangle(0,0,sprite.getWidth(),sprite.getHeight());
       sprite.setPosition(7,70);



    }

}
