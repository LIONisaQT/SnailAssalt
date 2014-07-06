package com.missionbit.snailassalt;

public class AcidSnail extends Enemy {
    public AcidSnail(float x, float y, float xSpeed, float ySpeed,float attack,float hp) {
        super(x, y, xSpeed, ySpeed,attack,hp, "acid snail.png", "acid snail2.png");
    }
    @Override
    public void Update(float dt,SnailAssalt game) {
    super.Update(dt,game);
        if(Math.random()>0.95){
          game.addSlime(new Droppings(this.bound.x, this.bound.y));
        }
    }
}
