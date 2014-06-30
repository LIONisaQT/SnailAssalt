package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Weapon waterGun;
    private Hydra hydra;
    private Snailshell shell;
    public boolean snaildead;
    float time = 0;

    //buttons start
    private StartButton startButtonMenu;
    private ShopButton shopButtonMenu;
    private BackButton backButtonShop, backButtonGameOver, backButtonLevelSelect; //different back buttons because their position will most likely be different
    private LoseButton loseButton;
    private HydraOn hydraOn;
    //level buttons start
    private Level1Button level1button;
    //level buttons end
    //buttons end
    //enemies start
    //levels start
    private Level1 level1;
    //levels end
    private int gameState, stateMainMenu, stateInGame, stateGameOver, stateShop, stateLevelSelect;
    public enum WeaponState{
        REGWEAPON,HRYDRA
    }
    private WeaponState weaponState;
    private ArrayList<Enemy> temp; //holds level's enemy arraylist
    private ArrayList<Projectile> water; //holds watergun shots
    //house
    private House house;

    //TEMP SHIT
    private int houseHp;
    private Texture housepic;
    private Texture houseBroken;
    private Texture houseGameOver;
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
        hydra = new Hydra();
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
        hydraOn = new HydraOn(width-210, height-500);
        //level buttons start
        level1button = new Level1Button(10, height - 410); //values given to level 1 button, eventually overridden
        //level buttons end
        //buttons end
        jimmy = new Player();
        shell = new Snailshell();
        //enemies start
        //enemies end
        //levels start
        level1 = new Level1(level1button);
        //levels end

        //TEMP SHIT
        house =new House();
        housepic = new Texture("house.png");
        houseBroken=new Texture("housebroken.png");
        houseGameOver=new Texture("housegameover.png");
        houseHp = 50;
        weaponState = WeaponState.REGWEAPON;

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
        hydraOn.position.set(hydraOn.getXPos(),hydraOn.getYPos());
        jimmy.curency=0;
        hydra.on(0);
        weaponState = WeaponState.REGWEAPON;
        house.Housebounds.setPosition(900,0);

        house.hp=1000;


        //buttons end
        camera.position.set((float)width/2, (float)height/2, 0);
        waterGun.waterLimit=15;

    }
    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(),0);
        return camera.unproject(tap);
    }
    public void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        // *** main menu currently contains ***
        // main menu --> level select
        // main menu --> shop
        // ***
        if (gameState == stateMainMenu) { //in main menu
            if (startButtonMenu.isPressed())
                gameState = stateLevelSelect; //go to level select
            if (shopButtonMenu.isPressed())
                gameState = stateShop; //go to shop
        }
        // *** shop currently contains ***
        // shop --> main menu
        // ***
        else if (gameState == stateShop) { //in shop
            if (backButtonShop.isPressed())
                gameState = stateMainMenu; //go to main menu
        }
        // *** level select currently contains ***
        // level select --> in-game
        // level select --> main menu
        // ***
        else if (gameState == stateLevelSelect) { //in level select

            if (level1button.isPressed()) { //play level 1

                temp = level1.getEnemies(); //temp arraylist now holds level 1's enemies
                gameState = stateInGame; //go in-game
            }
            if (backButtonLevelSelect.isPressed()) {
                gameState = stateMainMenu;
            }
        } //go to main menu

        // *** in-game currently contains ***
        // watergun logic
        // projectile logic
        // updates enemy arraylist
        // loss condition
        // in-game --> game over
        // ***
        else if (gameState == stateInGame) { //in-game
            waterGun.Update(water);
            if (hydraOn.isPressed()) {
                if (weaponState == weaponState.REGWEAPON) {
                    weaponState = weaponState.HRYDRA;
                } else if (weaponState == WeaponState.HRYDRA) {
                    weaponState = WeaponState.REGWEAPON;
                }
                Gdx.app.log("weaponstate is", "" + weaponState);
            }
            if (weaponState == weaponState.HRYDRA) {
                hydra.Update(water);

            }
            for (int i = 0; i < water.size(); i++) { //projectiles
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
                        temp.get(a).hp = temp.get(a).hp - waterGun.str;
                        if (temp.get(a).hp <= 0) {
                            shell.sprite.setPosition(temp.get(a).bound.x, temp.get(a).bound.y);
                            temp.remove(a);
                            snaildead = true;
                            jimmy.curency += 10;
                        }
                    }


                }
                if (projectileHit) {
                    water.remove(i);
                    i--;
                }

            }
            for (int a = 0; a < temp.size(); a++) {

                temp.get(a).Update(deltaTime);
                if (temp.get(a).bound.overlaps(House.Housebounds)) {
                    house.hp -= temp.get(a).Attack * Gdx.graphics.getDeltaTime();
                }
                if (loseButton.bound.contains(getTapPosition().x, getTapPosition().y))
                    gameState = stateGameOver; //go to game over

            }
            if (house.hp <= 0) { //loss condition
                gameState = stateGameOver; //go to game over
            }
        }
        // *** game over currently contains ***
        // disposes temp arraylist
        // game over --> main menu
        // ***
        else if (gameState == stateGameOver) { //in game over
            waterGun.waterLimit = 15;
            if (Gdx.input.justTouched() && backButtonGameOver.bound.contains(getTapPosition().x, getTapPosition().y)) {
                for (int a = 0; a < temp.size(); a++) {
                    temp.get(a).dispose(); //need to dispose everything in the arraylist to save memory
                }
                resetGame();
                gameState = stateMainMenu; //go to main menu
            }
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
        // *** in-game currently contains ***
        // lose button
        // back button
        // jimmy
        // watergun
        // projectile
        // draws and animates enemies
        // [TEMP] house code
        // ***
        else if (gameState == stateInGame) { //in-game
            house.draw(batch,house.Housebounds.x,house.Housebounds.y);
            Gdx.app.log("Housebounds x", "" + house.Housebounds.x);
            Gdx.app.log("size of house pic",""+house.house1.getWidth());
            batch.draw(loseButton.image, loseButton.position.x, loseButton.position.y);
            batch.draw(jimmy.sprite, 0, 0);
            waterGun.sprite.draw(batch);

            if (weaponState==WeaponState.HRYDRA){
                hydra.sprite.draw(batch);
            }

            batch.draw(hydraOn.image, hydraOn.position.x,hydraOn.position.y);
            shell.sprite.draw(batch);
            for (Projectile proj : water) {
                proj.shot.draw(batch);
            }
            //move house code to House.java

            for (int a = 0; a < temp.size(); a++) { //draws and animates enemies
                temp.get(a).draw(batch, time);
                if(temp.get(a).hp<=0){
                    //shell.sprite.draw(batch);



                }
            }
            if (level1button.isPressed()) { //in level 1
                font.draw(batch, "Current level: " + level1.getLevelName(), 10, 90);
            }
            font.draw(batch, "Number of snails: " + temp.size(), 10, 50);
            font.draw(batch, "Current state: in-game", 10, height - 50);
            font.draw(batch,"water Limit"+waterGun.waterLimit,10,height-75);
            font.draw(batch, "Snailshells:" + jimmy.curency, 10, height - 250);
            font.draw(batch,"househp"+(int)house.hp,10,height-400);
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