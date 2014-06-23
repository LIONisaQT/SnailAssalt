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
    public int hp;
    protected Texture frame1;
    protected Texture frame2;
    protected Animation animation;
    public Enemy(float x, float y, float xSpeed, float ySpeed) {
        this(x,y,xSpeed,ySpeed,"snail.png","standardsnail2.png");
    }
    public Enemy(float x, float y, float xSpeed, float ySpeed,String name, String name2) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        frame1 = new Texture(name);
        frame2 = new Texture(name2);
        animation = new Animation(0.4f, new TextureRegion(frame1), new TextureRegion(frame2));
        animation.setPlayMode(Animation.PlayMode.LOOP);
        speed = new Vector2();
        bound = new Rectangle(x, y, frame1.getWidth(), frame1.getHeight());
        speed.set(xSpeed, ySpeed);
    }
    public void Update() {
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
            //yas
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