package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by vivianlam on 6/27/14.
 */
public class Droppings {
    public Texture slime;
    public Texture bomb;
    public Rectangle bound;



    public Droppings(float x, float y){
        slime= new Texture("slime.png");
        bound= new Rectangle();
        bound.set(x,y,slime.getWidth(),slime.getHeight());

    }
    public void draw(SpriteBatch batch){
        batch.draw(slime, bound.x, bound.y);
    }
    public void dispose(){
        slime.dispose();
    }
}
