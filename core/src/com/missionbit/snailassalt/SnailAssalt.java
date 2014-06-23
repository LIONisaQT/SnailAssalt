package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    public static OrthographicCamera camera;
    private int width, height;
    private static Vector3 tap;
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
    //levels start
    private Level1 level1;
    //levels end
    //game states
    private int gameState, stateMainMenu, stateInGame, stateGameOver, stateShop, stateLevelSelect;
    private ArrayList<Enemy> temp;
    private ArrayList<Projectile> water;
    private Weapon waterGun;
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
        waterGun = new Weapon();
        water = new ArrayList<Projectile>();
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
        startButtonMenu.position.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.position.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        backButtonGameOver.position.set(backButtonGameOver.getXPos(), backButtonGameOver.getYPos());
        backButtonLevelSelect.position.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonShop.position.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        level1button.position.set(level1button.getXPos(), level1button.getYPos());
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        //buttons end
        camera.position.set((float)width/2, (float)height/2, 0);
    }
    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(),0);
        return camera.unproject(tap);
    }
    public void updateGame() {
        if (gameState == stateMainMenu) { //in main menu
            if (Gdx.input.justTouched() && startButtonMenu.bound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateLevelSelect; //go to level select
            if (Gdx.input.justTouched() && shopButtonMenu.bound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateShop; //go to shop
        }
        else if (gameState == stateShop) { //in shop
            if (Gdx.input.justTouched() && backButtonShop.bound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu; //go to main menu
        }
        else if (gameState == stateLevelSelect) { //in level select
            if (Gdx.input.justTouched()) {
                if (level1button.bound.contains(getTapPosition().x, getTapPosition().y)) {
                    level1button.isPressed(); //level 1 returns true
                    temp = level1.getEnemies();
                    gameState = stateInGame; //go in-game
                }
            }
            if (Gdx.input.justTouched() && backButtonLevelSelect.bound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu; //go to main menu
        }
        else if (gameState == stateInGame) { //in-game
            waterGun.Update(water);
            for (int i = 0; i < water.size(); i++) { //projectiles hit detection
                Projectile proj = water.get(i);
                proj.Update();
                if (proj.bound.y >= height) {
                    water.remove(i);
                    i--;
                }
                if (proj.bound.y < 0) {
                    water.remove(i);
                    i--;
                }
                boolean projectileHit = false;
                for (int a = 0; a < temp.size(); a++) {

                    if (proj.bound.overlaps(temp.get(a).bound)) {
                        projectileHit = true;
                        temp.remove(a);
                    }
                }
                if (projectileHit) {
                    water.remove(i);
                    i--;
                }
            }
            for (int a = 0; a < temp.size(); a++) {
                temp.get(a).Update();
            }
            if (loseButton.bound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateGameOver; //go to game over
        }
        //some code here to determine loss condition
        else if (gameState == stateGameOver) { //in game over
            if (Gdx.input.justTouched() && backButtonGameOver.bound.contains(getTapPosition().x, getTapPosition().y))
                for (int a = 0; a < temp.size(); a++) {
                    temp.get(a).texture.dispose();
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
            startButtonMenu.draw(batch);
            batch.draw(shopButtonMenu.image, shopButtonMenu.position.x, shopButtonMenu.position.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
        }
        // *** level select currently contains
        // back button
        // level 1 button (goes in-game)
        // ***
        else if (gameState == stateLevelSelect) { //in level select
            batch.draw(level1button.image, level1button.position.x, level1button.position.y);
            batch.draw(backButtonLevelSelect.image, backButtonLevelSelect.position.x, backButtonLevelSelect.position.y);
            font.draw(batch, "Current state: level select", 10, height - 50);
        }
        // *** shop currently contains ***
        // back button
        // ***
        else if (gameState == stateShop) { //in shop
            batch.draw(backButtonShop.image, backButtonShop.position.x, backButtonShop.position.y);
            font.draw(batch, "Current state: shop", 10, height - 50);
        }
        // *** level 1 in-game state currently contains ***
        // lose button
        // sprite
        // failure to draw multiple snails at different positions
        // ***
        else if (gameState == stateInGame && level1button.isPressed()) { //in level 1
            batch.draw(loseButton.image, loseButton.position.x, loseButton.position.y);
            batch.draw(jimmy.sprite, 0, 0);
            waterGun.sprite.draw(batch);
            for (Projectile proj : water) {
                proj.shot.draw(batch);
            }
            for (int a = 0; a < temp.size(); a++) {
                batch.draw(temp.get(a).texture, temp.get(a).bound.x, temp.get(a).bound.y);
            }
            font.draw(batch, "Number of snails: " + temp.size(), 10, 50);
            font.draw(batch, "Current state: in-game", 10, height - 50);
        }
        // *** game over screen currently contains ***
        // back button
        // ***
        else if (gameState == stateGameOver) { //in game over
            batch.draw(backButtonGameOver.image, backButtonGameOver.position.x, backButtonGameOver.position.y);
            font.draw(batch, "Game Over", 10, 50);
            font.draw(batch, "Current state: game over", 10, height - 50);
        }
        font.draw(batch, "Resolution: " + width + ", " + height, 10, height); //we keep forgetting the screen resolution T___T
        batch.end();
    }
}