package com.missionbit.snailassalt;
/**
 * Created by vivianlam on 6/23/14.
 */
public class FlyingSnail extends Enemy {
    public FlyingSnail(float x, float y, float xSpeed, float ySpeed, float attack, float hp) {
        super(x, y, xSpeed, ySpeed, attack, hp, "images/enemies/flyingSnail.png", "images/enemies/flyingSnail2.png");
    }

    public void Update(float dt, SnailAssalt game) {
        seconds = Math.max(seconds - dt, 0);
        flash = seconds > 0;
        this.bound.x = this.bound.x + this.speed.x;
        this.bound.y = this.bound.y + this.speed.y;
        if (this.bound.overlaps(House.Housebounds)) {
            this.bound.setX(House.Housebounds.x - (this.bound.getWidth()));
            House.hp -= this.Attack * dt;
            this.speed.y = 0;
        }
        if (this.bound.y >= height) {
            this.speed.y = this.speed.y * -1;
        }
        if (this.bound.y <= 0) {
            this.speed.y = this.speed.y * -1;
        }
        if (Math.random() > 0.995) {
            game.addBomb(new BombDrop(this.bound.x, this.bound.y));
        }
    }
}




