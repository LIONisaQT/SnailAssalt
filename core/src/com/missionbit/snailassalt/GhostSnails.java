package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.security.spec.PSSParameterSpec;
/**
 * Created by douglas on 6/24/14.
 */
public class GhostSnails {
    public Texture image1, image2;
    public Sprite sprite,sprite2;
    public Rectangle bounds;
    public Vector2 speed;
    protected float height, width;
    private Animation animation;
    public GhostSnails(int a, int b) {
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        image1 = new Texture("images/enemies/ghostSnail.png");
        image2 = new Texture("images/enemies/ghostSnail2.png");
        sprite = new Sprite(image1);
        sprite.setSize(width/1920 * image1.getWidth(),height/1080* image2.getHeight());
        sprite.setPosition(a, b);
        bounds = new Rectangle();
        bounds.set(a, b, sprite.getWidth(), sprite.getHeight());
        speed = new Vector2();
        speed.set(0, 5);
        animation = new Animation(0.5f, sprite, sprite2);
    }
    /*public void draw(SpriteBatch batch,float time){
        batch.draw(animation.getKeyFrame(time),bounds.x,bounds.y,width/1920 * sprite.getWidth(),height/1080 * sprite2.getHeight());
    }*/
    public void dispose() {sprite.getTexture().dispose();}

    public void Update() {
        this.bounds.y += speed.y;
    }
}
