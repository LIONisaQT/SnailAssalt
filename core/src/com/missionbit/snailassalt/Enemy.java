package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {
    protected Rectangle bound;
    protected Vector2 speed;
    protected float width, height, hp;
    //private Texture frame1, frame2;
    private Sprite frame1, frame2;
    private Animation animation;
    protected float Attack, SpawnOffset;
    public Enemy(float x, float y, float xSpeed, float ySpeed,float attack,float hp) {
        this(x, y, xSpeed, ySpeed, attack, hp, "snail.png", "standardsnail2.png");
    }
    public Enemy(float x, float y, float xSpeed, float ySpeed, float attack, float hit, String name, String name2) {
        //Sprite spriteFrame1;
        //Sprite spriteFrame2;
        float maxHP;
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        //frame1 = new Texture(name);
        //frame2 = new Texture(name2);
        //spriteFrame1 = new Sprite(frame1);
        //spriteFrame1.setSize(frame1.getWidth(), frame1.getHeight());
        //spriteFrame2 = new Sprite(frame2);
        //spriteFrame1.setSize(frame1.getWidth(), frame1.getHeight());
        //animation = new Animation(0.5f, new TextureRegion(spriteFrame1), new TextureRegion(spriteFrame2));
        frame1=new Sprite(new Texture(name));
        frame2=new Sprite(new Texture(name2));
        frame1.setSize(frame1.getWidth(), frame1.getHeight());
        frame2.setSize(frame1.getWidth(), frame1.getHeight());

        animation=new Animation(0.5f, new TextureRegion(frame1), new TextureRegion(frame2));
        animation.setPlayMode(Animation.PlayMode.LOOP);
        speed = new Vector2();
        bound = new Rectangle(x, y, frame1.getWidth(), frame1.getHeight());
        speed.set(xSpeed, ySpeed);
        maxHP = hit;
        hp = maxHP;
        Attack = attack;
        SpawnOffset=15;
    }
    public void Update (float dt, SnailAssalt game) {
        this.bound.x = this.bound.x + this.speed.x;
        this.bound.y = this.bound.y + this.speed.y;
        if (this.bound.overlaps(House.Housebounds)) {
            this.bound.setX(House.Housebounds.x - (this.bound.getWidth()));
            House.hp -= this.Attack * dt;
        }
    }
    public void draw(SpriteBatch batch,float time){
        batch.draw(animation.getKeyFrame(time),bound.x,bound.y);
    }
   /* public void dispose() {
        frame1.dispose();
        frame2.dispose();
    }*/
    public boolean enemyDead(){return hp < 0 ;}
}