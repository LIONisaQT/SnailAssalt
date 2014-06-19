package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by douglas on 6/17/14.
 */
public class Enemy {
    public Texture standardSnail;
    public Rectangle standardSnailBound;
    public int hp;
    public Texture acidSnail;
    public Rectangle acidSnailBound;
    public Texture flyingSnail;
    public Rectangle flyingSnailBound;
    public Texture healerSnail;
    public Rectangle healerSnailBound;
    public Texture motherSnail;
    public Rectangle motherSnailBound;
    public Texture people;
    public Rectangle peopleBound;
    public Texture boss;
    public Rectangle bossBound;
    public Enemy() {
        standardSnail = new Texture("snail.png");
        standardSnailBound = new Rectangle(50,50,standardSnail.getWidth(),standardSnail.getHeight());
        acidSnail= new Texture("snail.png");
        acidSnailBound = new Rectangle(50,100,acidSnail.getWidth(),acidSnail.getHeight());
        flyingSnail = new Texture("snail.png");
        flyingSnailBound= new Rectangle(50,150,flyingSnail.getWidth(),flyingSnail.getHeight());
        healerSnail = new Texture("snail.png");
        healerSnailBound = new Rectangle(50,200,healerSnail.getWidth(),healerSnail.getHeight());
        motherSnail =new Texture("snail.png");
        motherSnailBound = new Rectangle(50,250,motherSnail.getWidth(),motherSnail.getHeight());
        people = new Texture("snail.png");
        peopleBound = new Rectangle(50,300, people.getWidth(),people.getHeight());
        boss = new Texture("snail.png");
        bossBound = new Rectangle(50,350,boss.getWidth(),boss.getHeight());
    }
}