package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
public class Player {
<<<<<<< HEAD
    public Texture texture;
    public Sprite sprite;
    public Rectangle bound;
    public Player () {
        float width, height;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        texture = new Texture("jimmy.png");
        sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        sprite.setPosition((int)width - texture.getWidth(), (int)(height / 2 - texture.getHeight() / 3));
        bound = new Rectangle((int)width - texture.getWidth(), (int)(height / 2 - texture.getHeight() / 3), sprite.getWidth(), sprite.getHeight());
=======
        public Texture texture;
        public Sprite sprite;
        public Rectangle bound;
        public int currentWaterGun, squirtGun, superSoaker;

        public Player () {
            float width, height;
            width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();
            texture = new Texture("jimmy.png");
            sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
            sprite.setPosition((int)width - texture.getWidth(), (int)(height / 2 - texture.getHeight() / 3));
            bound = new Rectangle((int)width - texture.getWidth(), (int)(height / 2 - texture.getHeight() / 3), sprite.getWidth(), sprite.getHeight());
        }
        public void playerPosition(float c,float d){
            bound.setX(c);
            bound.setY(d);
            sprite.setX(c);
            sprite.setY(d);
        }

>>>>>>> SOUNDSandSHOP
    }
}

