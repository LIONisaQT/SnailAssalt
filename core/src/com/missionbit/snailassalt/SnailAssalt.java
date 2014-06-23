package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int width;
    private int height;
    private Vector3 tap;
    private Texture background;
    private BitmapFont font;
    private Player jimmy;
    //buttons start
    private StartButton startButtonMenu;
    private ShopButton shopButtonMenu;
    private BackButton backButtonShop, backButtonGameOver, backButtonLevelSelect; //different back buttons because their position will most likely be different
    private LoseButton loseButton;
    //level buttons start
    private Level1Button level1button;
    //level buttons end
    //buttons end
    //enemies start
    private Enemy standardSnail;
    //enemies end
    //levels start
    private Level1 level1;
    //levels end
    //game states
    private int gameState, stateMainMenu, stateInGame, stateGameOver, stateShop, stateLevelSelect;
    private ArrayList<Enemy> temp;
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
        background = new Texture("sidewaysmenu.png");
        jimmy = new Player();
        tap = new Vector3(); //location of tap
        //game states start
        stateMainMenu = 0;
        stateInGame = 1;
        stateGameOver = 2;
        stateShop = 3;
        stateLevelSelect = 4;
        gameState = stateMainMenu;
        //buttons start
        startButtonMenu = new StartButton(10, 10);
        shopButtonMenu = new ShopButton(10, startButtonMenu.buttonGetHeight() + 20);
        backButtonGameOver = new BackButton(width - 210, 10);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        loseButton = new LoseButton(width - 210, height - 210);
        //level buttons start
        level1button = new Level1Button();
        //level buttons end
        //buttons end
        //levels start
        level1 = new Level1(level1button);
        //levels end
        resetGame();
    }
    public void resetGame(){
        camera.position.set(width/2, height/2, 0);
        gameState = stateMainMenu; //game starts at main menu
        //buttons start
        startButtonMenu.buttonPosition.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.buttonPosition.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        backButtonGameOver.buttonPosition.set(backButtonGameOver.getXPos(), backButtonGameOver.getYPos());
        backButtonLevelSelect.buttonPosition.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonShop.buttonPosition.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        level1button.buttonPosition.set(level1button.getXPos(), level1button.getYPos());
        loseButton.buttonPosition.set(loseButton.getXPos(), loseButton.getYPos());
        //buttons end
        //enemies start
        //for (int a = 0; a < level1.getEnemies().size(); a++) {
        //    level1.getEnemies().get(a).standardSnailBound.x = (float)Math.random() * width;
        //    level1.getEnemies().get(a).standardSnailBound.y = (float)Math.random() * height;
        //}
        //enemies end
    }
    public Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(),0);
        return camera.unproject(tap);
    }
    public void updateGame(){
        if (gameState == stateMainMenu) { //in main menu
            if (Gdx.input.justTouched() && startButtonMenu.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateLevelSelect; //go to level select
            if (Gdx.input.justTouched() && shopButtonMenu.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateShop; //go to shop
        }
        else if (gameState == stateShop) { //in shop
            if (Gdx.input.justTouched() && backButtonShop.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu; //go to main menu
        }
        else if (gameState == stateLevelSelect) { //in level select
            if (Gdx.input.justTouched() && level1button.buttonBound.contains(getTapPosition().x, getTapPosition().y)) {
                level1button.isPressed(); //level 1 returns true
                temp = level1.getEnemies();
                gameState = stateInGame; //go in-game
            }
            if (Gdx.input.justTouched() && backButtonLevelSelect.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu; //go to main menu
        }
        else if (gameState == stateInGame) { //in-game
            if (Gdx.input.justTouched() && loseButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateGameOver; //go to game over
            //some code here to determine loss condition
        }
        else if (gameState == stateGameOver) { //in game over
            if (Gdx.input.justTouched() && backButtonGameOver.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                for (int a = 0; a < temp.size(); a++) {
                    temp.get(a).standardSnail.dispose();
                }
                gameState = stateMainMenu; //go to main menu
        }
    }
    public void drawGame(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setScale(2);
        font.setColor(0, 0, 0, 1);
        // *** main menu currently contains ***
        // shop button
        // level select button (play)
        // ***
        if (gameState == stateMainMenu) { //in main menu
            batch.draw(background, 0, 0);
            batch.draw(startButtonMenu.buttonImage, startButtonMenu.buttonPosition.x, startButtonMenu.buttonPosition.y);
            batch.draw(shopButtonMenu.buttonImage, shopButtonMenu.buttonPosition.x, shopButtonMenu.buttonPosition.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
        }
        // *** level select currently contains
        // back button
        // level 1 button (goes in-game)
        // ***
        else if (gameState == stateLevelSelect) { //in level select
            batch.draw(level1button.buttonImage, level1button.buttonPosition.x, level1button.buttonPosition.y);
            batch.draw(backButtonLevelSelect.buttonImage, backButtonLevelSelect.buttonPosition.x, backButtonLevelSelect.buttonPosition.y);
            font.draw(batch, "Current state: level select", 10, height - 50);
        }
        // *** shop currently contains ***
        // back button
        // ***
        else if (gameState == stateShop) { //in shop
            batch.draw(backButtonShop.buttonImage, backButtonShop.buttonPosition.x, backButtonShop.buttonPosition.y);
            font.draw(batch, "Current state: shop", 10, height - 50);
        }
        // *** level 1 in-game state currently contains ***
        // lose button
        // jimmy
        // failure to draw multiple snails at different positions
        // ***
        else if (gameState == stateInGame && level1button.isPressed()) { //in level 1
            batch.draw(loseButton.buttonImage, loseButton.buttonPosition.x, loseButton.buttonPosition.y);
            batch.draw(jimmy.jimmy, 0, 0);

            for (int a = 0; a < temp.size(); a++) {
                batch.draw(temp.get(a).standardSnail, temp.get(a).xPos, temp.get(a).yPos);
            }
            font.draw(batch, "Number of snails: " + level1.getEnemies().size(), 10, 50);
            font.draw(batch, "Current state: in-game", 10, height - 50);
        }
        // *** game over screen currently contains ***
        // back button
        // ***
        else if (gameState == stateGameOver) { //in game over
            batch.draw(backButtonGameOver.buttonImage, backButtonGameOver.buttonPosition.x, backButtonGameOver.buttonPosition.y);
            font.draw(batch, "Game Over", 10, 50);
            font.draw(batch, "Current state: game over", 10, height - 50);
        }
        font.draw(batch, "Resolution: " + width + ", " + height, 10, height); //we keep forgetting the screen resolution T___T
        batch.end();
    }
}