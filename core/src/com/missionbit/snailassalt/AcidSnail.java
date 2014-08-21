package com.missionbit.snailassalt;

public class AcidSnail extends Enemy {
    public AcidSnail(float x, float y, float xSpeed, float ySpeed, float attack, float hp) {
        super(x, y, xSpeed, ySpeed, attack, hp, "images/enemies/acidSnail.png", "images/enemies/acidSnail2.png");
    }

    public void Update(float dt, SnailAssalt game) {
        super.Update(dt,game);
        if (Math.random() > 0.95) {
            game.addSlime(new Droppings(this.bound.x,this.bound.y));
        }
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
    }
}