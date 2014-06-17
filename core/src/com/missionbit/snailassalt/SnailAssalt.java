package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private int width;
    private int height;
    private Player jimmy;
    private Enemy snail;
	@Override
    public void render () {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateGame();
        drawGame();
    }

    public void create () {
    batch = new SpriteBatch();
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    jimmy = new Player();
    snail = new Enemy();
    resetGame();


}


    public  void resetGame(){
        snail.snailBound.x = 400;
        snail.snailBound.y = 400;


    }

    public void updateGame(){
        if(snail.snailBound.contains(Gdx.input.getX(),Gdx.input.getY())){
            snail.snailBound.x = 400;


        }

    }
    public void drawGame(){
        batch.begin();
        batch.draw(jimmy.jimmy,0,0);
        batch.draw(snail.snail,snail.snailBound.x,snail.snailBound.y);
        if(snail.snailBound.contains(Gdx.input.getX(),Gdx.input.getY())){
            batch.draw(snail.snail,-400,-400);

        }

        batch.end();
    }


}
