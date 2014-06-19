package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int width;
    private int height;
    private Vector3 temp;
    private BitmapFont font;
    //buttons start
    private StartButton startButton;
    private ShopButton shopButton;
    private BackButton backButton;
    private LevelSelectButton levelSelectButton;
    private LoseButton loseButton;
    //buttons end
    private Player jimmy;
    //enemies start
    private Enemy standardSnail;
    private Enemy acidSnail;
    private Enemy flyingSnail;
    private Enemy healerSnail;
    private Enemy motherSnail;
    private Enemy people;
    private Enemy boss;
    //enemies end
    //game states
    private int gameState, stateMainMenu, stateInGame, stateGameOver, stateShop, stateLevelSelect;
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
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        //game states start
        stateMainMenu = 0;
        stateInGame = 1;
        stateGameOver = 2;
        stateShop = 3;
        stateLevelSelect = 4;
        gameState = stateMainMenu;
        //game states end
        temp = new Vector3();
        //buttons start
        startButton = new StartButton(10, 10);
        shopButton = new ShopButton(10, startButton.buttonGetHeight() + 20);
        backButton = new BackButton(width - 210, 10);
        levelSelectButton = new LevelSelectButton(10, height - 410);
        loseButton = new LoseButton(width - 210, height - 210);
        //buttons end
        jimmy = new Player();
        //enemies start
        standardSnail = new Enemy();
        acidSnail = new Enemy();
        flyingSnail=new Enemy();
        healerSnail = new Enemy();
        motherSnail=new Enemy();
        people=new Enemy();
        boss=new Enemy();
        //enemies end
        resetGame();
    }
    public void resetGame(){
        camera.position.set(width/2, height/2, 0);
        gameState = stateMainMenu;
        //buttons start
        startButton.buttonPosition.set(startButton.getXPos(), startButton.getYPos());
        shopButton.buttonPosition.set(shopButton.getXPos(), shopButton.getYPos());
        backButton.buttonPosition.set(backButton.getXPos(), backButton.getYPos());
        levelSelectButton.buttonPosition.set(levelSelectButton.getXPos(), levelSelectButton.getYPos());
        loseButton.buttonPosition.set(loseButton.getXPos(), loseButton.getYPos());
        //buttons end
        //enemies start
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
        //enemies end
    }
    public Vector3 getTapPosition() {
        temp.set(Gdx.input.getX(), Gdx.input.getY(),0);
        return camera.unproject(temp);
    }
    public void updateGame(){
        if (gameState == stateMainMenu) {
            if (Gdx.input.justTouched() && startButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateLevelSelect;
            if (Gdx.input.justTouched() && shopButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateShop;
        }
        else if (gameState == stateLevelSelect) {
            if (Gdx.input.justTouched() && levelSelectButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateInGame;
            if (Gdx.input.justTouched() && backButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu;
        }
        else if (gameState == stateShop) {
            if (Gdx.input.justTouched() && backButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu;
        }
        else if (gameState == stateInGame) {
            if (Gdx.input.justTouched() && backButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu;
            if (Gdx.input.justTouched() && loseButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateGameOver;
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY()))
                standardSnail.standardSnailBound.x = 400;
            //some code here to determine loss condition
        }
        else if (gameState == stateGameOver) {
            if (Gdx.input.justTouched() && backButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
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
            batch.draw(startButton.buttonImage, startButton.buttonPosition.x, startButton.buttonPosition.y);
            batch.draw(shopButton.buttonImage, shopButton.buttonPosition.x, shopButton.buttonPosition.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
        }
        else if (gameState == stateLevelSelect) {
            batch.draw(levelSelectButton.buttonImage, levelSelectButton.buttonPosition.x, levelSelectButton.buttonPosition.y);
            batch.draw(backButton.buttonImage, backButton.buttonPosition.x, backButton.buttonPosition.y);
            font.draw(batch, "Current state: level select", 10, height - 50);
        }
        else if (gameState == stateShop) {
            batch.draw(backButton.buttonImage, backButton.buttonPosition.x, backButton.buttonPosition.y);
            font.draw(batch, "Current state: shop", 10, height - 50);
        }
        else if (gameState == stateInGame) {
            batch.draw(backButton.buttonImage, backButton.buttonPosition.x, backButton.buttonPosition.y);
            batch.draw(loseButton.buttonImage, loseButton.buttonPosition.x, loseButton.buttonPosition.y);
            batch.draw(jimmy.jimmy, 0, 0);
            batch.draw(standardSnail.standardSnail, standardSnail.standardSnailBound.x, standardSnail.standardSnailBound.y);
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                batch.draw(standardSnail.standardSnail, standardSnail.standardSnailBound.x, standardSnail.standardSnailBound.y);
            }
            font.draw(batch, "Current state: in-game", 10, height - 50);
        }
        else if (gameState == stateGameOver) {
            batch.draw(backButton.buttonImage, backButton.buttonPosition.x, backButton.buttonPosition.y);
            font.draw(batch, "Game Over", 10, 50);
            font.draw(batch, "Current state: game over", 10, height - 50);
        }
        batch.end();
    }
}