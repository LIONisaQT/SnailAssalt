package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.security.spec.PSSParameterSpec;
/**
 * Created by douglas on 6/24/14.
 */
public class Snailshell {
    public Texture image;
    public Sprite sprite;
    public Rectangle bounds;
    public Vector2 speed;
    protected float height, width;
    public Snailshell(int a,int b) {
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        image = new Texture("ghostsnail.png");
        sprite = new Sprite(image);
        sprite.setSize(1920/width * image.getWidth(),1080/height* image.getHeight());
        sprite.setPosition(a, b);
        bounds = new Rectangle();
        bounds.set(a, b, sprite.getWidth(), sprite.getHeight());
        speed = new Vector2();
        speed.set(0, 5);
    }
    public void Update() {
        this.bounds.y += speed.y;
    }
}
