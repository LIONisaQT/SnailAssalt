package com.missionbit.snailassalt;

public class AcidSnail extends Enemy {
    public AcidSnail(float x, float y, float xSpeed, float ySpeed) {
        super(x, y, xSpeed, ySpeed, "acid snail.png", "acid snail2.png");
    }
    @Override
    public void Update(SnailAssalt game) {
    super.Update(game);
        if(Math.random()>0.99){
          game.addSlime(new Droppings(this.bound.x, this.bound.y));
        }
    }
}
