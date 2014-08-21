package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {
    public  Texture snail1, snail2;
    protected Rectangle bound;
    protected Vector2 speed;
    protected float width, height, hp;
    private Sprite frame1, frame2;
    protected boolean flash;
    float seconds = 0;
    private Animation animation;
    protected float Attack, SpawnOffset;
    public Enemy(float x, float y, float xSpeed, float ySpeed,float attack,float hp) {
        this(x, y, xSpeed, ySpeed, attack, hp, "images/enemies/standardSnail.png", "images/enemies/standardSnail2.png");
    }

    public Enemy(float x, float y, float xSpeed, float ySpeed, float attack, float hit, String name, String name2) {
        float maxHP;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        snail1 = new Texture(name);
        snail2 = new Texture(name2);
        frame1 = new Sprite(snail1);
        frame2 = new Sprite(snail2);
        animation = new Animation(0.5f, frame1, frame2);
        speed = new Vector2();
        bound = new Rectangle(x, y, frame1.getWidth(), frame1.getHeight());
        speed.set(xSpeed, ySpeed);
        maxHP = hit;
        hp = maxHP;
        Attack = attack;
        SpawnOffset = 15;
    }

    public void Update (float dt, SnailAssalt game) {
        seconds = Math.max(seconds - dt, 0);
        flash = seconds > 0;
        this.bound.x = this.bound.x + this.speed.x;
        this.bound.y = this.bound.y + this.speed.y;
        if (this.bound.overlaps(House.Housebounds)) {
            this.bound.setX(House.Housebounds.x - (this.bound.getWidth()));
            House.hp -= this.Attack * dt;
        }
    }

    public void draw(SpriteBatch batch,float time){
        if (flash) {
               batch.draw(animation.getKeyFrame(time),bound.x,bound.y,width/1920 * frame1.getWidth(),height/1080 * frame1.getHeight());
        } else {
            if(SnailAssalt.gameState == SnailAssalt.GameState.INFO) {
                batch.draw(animation.getKeyFrame(time), bound.x, bound.y, (float) (frame1.getWidth()*1.5), (float)(frame1.getHeight()*1.5));
            } else {
                batch.draw(animation.getKeyFrame(time),bound.x, bound.y, width/1920 * frame1.getWidth(),height/1080 * frame1.getHeight());
            }
        }
    }

    public void dispose() {
        frame1.getTexture().dispose();
        frame2.getTexture().dispose();
    }

    public void startFlash(float i) {
        flash = true;
        seconds = i;
    }
}