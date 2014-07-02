package com.missionbit.snailassalt;

/**
 * Created by vivianlam on 6/23/14.
 */
public class FlyingSnail extends Enemy{
    public FlyingSnail(float x, float y, float xSpeed, float ySpeed) {
        super(x, y, xSpeed, ySpeed, "flying snail.png", "flying snail2.png");
    }
    @Override
    public void Update(SnailAssalt game){
    super.Update(game);
    if(Math.random()>0.995){
        game.addBomb(new BombDrop(this.bound.x, this.bound.y));
    }
}

}

