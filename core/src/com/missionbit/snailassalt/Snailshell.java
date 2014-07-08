package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.security.spec.PSSParameterSpec;

/**
 * Created by douglas on 6/24/14.
 */
public class Snailshell {
    public Texture image;
    public Sprite  sprite;
    public Rectangle bounds;
    public Vector2 speed;
    public Vector2 position;
    protected float height,width;
    public Snailshell(float a,float b){
       image = new Texture("standardshell.png");
       bounds=new Rectangle();
       bounds.set(a,b,image.getWidth(),image.getHeight());
       height= Gdx.graphics.getHeight();
       width=Gdx.graphics.getWidth();
       speed=new Vector2();
       speed.set(0,5);
    }
    public void Update(){
       this.bounds.y+=speed.y;
    }


}
