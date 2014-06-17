package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int width;
    private int height;
    private Player jimmy;
    private Enemy standardSnail;
    private Enemy acidSnail;
    private Enemy flyingSnail;
    private Enemy healerSnail;
    private Enemy motherSnail;
    private Enemy people;
    private Enemy boss;
    private int gameState, stateMainMenu, stateInGame, stateGameOver;
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
        camera = new OrthographicCamera(width, height);
        stateMainMenu = 0;
        stateInGame = 1;
        stateGameOver = 2;
        gameState = stateMainMenu;
        jimmy = new Player();
        standardSnail = new Enemy();
        acidSnail = new Enemy();
        flyingSnail=new Enemy();
        healerSnail = new Enemy();
        motherSnail=new Enemy();
        people=new Enemy();
        boss=new Enemy();
        resetGame();
    }

    public void resetGame(){
        camera.position.set(width/2, height/2, 0);
        standardSnail.standardSnailBound.x = 400;
        standardSnail.standardSnailBound.y = 400;
        standardSnail.hp=20;
        acidSnail.acidSnailBound.x = 0;
        acidSnail.acidSnailBound.y = 0;
        acidSnail.hp=60;
        flyingSnail.flyingSnailBound.x = 0;
        flyingSnail.flyingSnailBound.y = 0;
        flyingSnail.hp=20;
        healerSnail.healerSnailBound.x = 0;
        healerSnail.healerSnailBound.y = 0;
        healerSnail.hp=40;
        motherSnail.motherSnailBound.x = 0;
        motherSnail.motherSnailBound.y = 0;
        motherSnail.hp=60;
        people.peopleBound.x = 0;
        people.peopleBound.y = 0;
        people.hp=40;
        boss.bossBound.x = 0;
        boss.bossBound.y = 0;
        boss.hp=100;

    }

    public void updateGame(){
        if (gameState == stateMainMenu) {
            if (Gdx.input.justTouched())
                gameState = stateInGame;
        }
        else if (gameState == stateInGame) {
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                standardSnail.standardSnailBound.x = 400;
            }
            //some code here to determine loss condition
        }
        else if (gameState == stateGameOver) {
            if (Gdx.input.justTouched())
                gameState = stateMainMenu;
        }
    }

    public void drawGame(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (gameState == stateMainMenu) {

        }
        batch.draw(jimmy.jimmy,0,0);
        batch.draw(standardSnail.standardSnail,standardSnail.standardSnailBound.x,standardSnail.standardSnailBound.y);
        if(standardSnail.standardSnailBound.contains(Gdx.input.getX(),Gdx.input.getY())){
            batch.draw(standardSnail.standardSnail,standardSnail.standardSnailBound.x,standardSnail.standardSnailBound.y);
        }
        batch.end();
    }
}
