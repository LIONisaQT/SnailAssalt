package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private BitmapFont font;
    private int width;
    private int height;
    private Player jimmy;
    private Enemy snail;
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
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        camera = new OrthographicCamera(width, height);
        stateMainMenu = 0;
        stateInGame = 1;
        stateGameOver = 2;
        gameState = stateMainMenu;
        jimmy = new Player();
        snail = new Enemy();
        resetGame();
    }

    public void resetGame(){
        camera.position.set(width/2, height/2, 0);
        snail.snailBound.x = 400;
        snail.snailBound.y = 400;
    }

    public void updateGame(){
        if (gameState == stateMainMenu) {
            if (Gdx.input.justTouched())
                gameState = stateInGame;
        }
        else if (gameState == stateInGame) {
            if (snail.snailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                snail.snailBound.x = 400;
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
        font.setScale(2);
        font.setColor(0, 0, 0, 1);
        if (gameState == stateMainMenu) {
            font.draw(batch, "Tap to begin", 50, camera.position.y - (height / 4) - 50);
        }
        else if (gameState == stateInGame) {
            batch.draw(jimmy.jimmy, 0, 0);
            batch.draw(snail.snail, snail.snailBound.x, snail.snailBound.y);
            if (snail.snailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                batch.draw(snail.snail, snail.snailBound.x, snail.snailBound.y);
            }
        }
        else if (gameState == stateGameOver) { //currently there are no conditions for loss
            font.draw(batch, "Game Over", 50, camera.position.y - (height / 4));
            font.draw(batch, "Tap to restart", 50, camera.position.y - (height / 4) - 50);
        }
        batch.end();
    }
}
