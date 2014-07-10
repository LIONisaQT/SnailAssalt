package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
public class Player {
    protected Texture texture;
    protected Sprite sprite;
    protected Rectangle bound;
    protected float width, height;
    public Player () {
        texture = new Texture("jimmy.png");
        sprite = new Sprite(texture, 0, 0, texture.getWidth(), texture.getHeight());
        bound = new Rectangle(0, 0, sprite.getWidth(), sprite.getHeight());

    }
    public void playerPosition(float c,float d){
        bound.setX(c);
        bound.setY(d);
        sprite.setX(c);
        sprite.setY(d);
    }
}


