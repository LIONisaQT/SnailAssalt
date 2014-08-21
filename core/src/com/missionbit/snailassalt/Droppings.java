package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by vivianlam on 6/27/14.
 */
public class Droppings {
    public Texture slime;
    public Rectangle bound;
    public Droppings(float x, float y) {
        this.slime = new Texture("images/enemies/slime.png");
        bound = new Rectangle();
        bound.set(x, y, this.slime.getWidth(), this.slime.getHeight());
    }

    public void dispose() {
        slime.dispose();
    }

    public void draw(SpriteBatch batch){batch.draw(slime, bound.x, bound.y);}
}
