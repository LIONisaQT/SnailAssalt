package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by vivianlam on 7/2/14.
 */
public class BombDrop {
    public Texture bomb;
    public Rectangle bound;
    public BombDrop(float x, float y) {
        this.bomb = new Texture("images/enemies/bomb.png");
        bound = new Rectangle();
        bound.set(x, y, this.bomb.getWidth(), this.bomb.getHeight());
    }

    public void dispose() {bomb.dispose();}

    public void draw(SpriteBatch batch){
        batch.draw(bomb, bound.x, bound.y);
    }
}


