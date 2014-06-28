package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {
    public Rectangle bound;
    public Vector2 speed;
    float width, height;
    public static int  hp;
    protected Texture frame1;
    protected Texture frame2;
    protected Animation animation;
    protected float Attack;
    public Enemy(float x, float y, float xSpeed, float ySpeed,float attack) {
        this(x,y,xSpeed,ySpeed,attack,"snail.png","standardsnail2.png");
    }
    public Enemy(float x, float y, float xSpeed, float ySpeed,float attack,String name, String name2) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        frame1 = new Texture(name);
        frame2 = new Texture(name2);
        animation = new Animation(0.5f, new TextureRegion(frame1), new TextureRegion(frame2));
        animation.setPlayMode(Animation.PlayMode.LOOP);
        speed = new Vector2();
        bound = new Rectangle(x, y, frame1.getWidth(), frame1.getHeight());
        speed.set(xSpeed, ySpeed);
        hp= 10;
        Attack=attack;
    }
    public void Update(float dt) {
        this.bound.x = this.bound.x + this.speed.x;
        this.bound.y = this.bound.y + this.speed.y;
        if (this.bound.x >= SnailAssalt.camera.position.x + width / 2) {
            this.speed.x = -this.speed.x;
        }
        if (this.bound.x <= SnailAssalt.camera.position.x - width / 2) {
            this.speed.x = -this.speed.x;
        }
        if (this.bound.y >= SnailAssalt.camera.position.y + height / 2) {
            this.speed.y = -this.speed.y;
        }
        if (this.bound.y <= SnailAssalt.camera.position.y - height / 2) {
            this.speed.y = -this.speed.y;
        }
        if(this.bound.overlaps(House.Housebounds)){
            this.bound.setX(House.Housebounds.x-(this.bound.getWidth()));
            House.hp -= this.Attack * dt;
        }
    }
    public void move() {

    }
    public void draw(SpriteBatch batch,float time){
        batch.draw(animation.getKeyFrame(time),bound.x,bound.y);
    }
    public void dispose(){
        frame1.dispose();
        frame2.dispose();

    }
}