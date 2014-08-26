package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by ryanisaqt on 8/24/14.
 */
public abstract class GameStates {
    protected float width, height;
    protected SpriteBatch batch;
    protected Sprite background;
    public GameStates() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        background = new Sprite(new Texture("badlogic.jpg"));
    }

    public abstract void create();

    public abstract void update();

    public abstract void draw();
}
