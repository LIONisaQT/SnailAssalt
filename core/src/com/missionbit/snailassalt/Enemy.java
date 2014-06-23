package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {
    public Texture texture;
    public Rectangle bound;
    public Vector2 speed;
    float width, height;
    public int hp;
    public Enemy(float x, float y, float xSpeed, float ySpeed) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        texture = new Texture("snail.png");
        speed = new Vector2();
        bound = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
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
}