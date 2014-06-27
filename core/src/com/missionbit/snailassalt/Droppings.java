package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by vivianlam on 6/26/14.
 */
public class Droppings {
    public Texture slime;
    public Rectangle bound;

        public  Droppings(){
            slime = new Texture("slime.png");
            bound = new Rectangle();
        }

}
