package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
public class Player {
    public Texture texture,textureRachel;
    public Sprite sprite, rachel;
    public Rectangle bound;
    public boolean enable;
    public Player () {
        enable = false;
        float width, height;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        texture = new Texture("images/player/jimmy.png");
        textureRachel = new Texture("images/player/rachel.png");
        rachel = new Sprite(textureRachel,textureRachel.getWidth(),textureRachel.getHeight());
        rachel.setPosition((int)width - texture.getWidth(), (int)(height / 2 - texture.getHeight() / 3));
        sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        sprite.setPosition((int)width - texture.getWidth(), height/2 - (height/720)*sprite.getHeight()/2);
        bound = new Rectangle((int)width - texture.getWidth(), (int)(height / 2 - texture.getHeight() / 3), sprite.getWidth(), sprite.getHeight());
    }
}
