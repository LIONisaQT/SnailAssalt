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
    public static OrthographicCamera camera;
    protected static int numberOfLevels = 10;
    private int width, height;
    private static Vector3 tap;
    private BitmapFont font;
    private Player jimmy;
    private House house;
    private Snailshell shell; //should be an arraylist of shells
    public boolean snaildead; //is this supposed to be here?
    private float time = 0;
    //backgrounds start
    private Texture mainMenuBackground;
    private Texture gameOverBackground;
    private Texture lawn;
    //backgrounds end
    //weapwns start
    private Weapon waterGun;
    private Hydra hydra;
    private ArrayList<Projectile> water; //holds watergun shots
    //weapwns end
    //buttons start
    private StartButton startButtonMenu;
    private ShopButton shopButtonMenu;
    private BackButton backButtonShop, backButtonGameOver, backButtonLevelSelect; //different back buttons because their position will most likely be different
    private LoseButton loseButton;
    private HydraButton hydraButton;
    //buttons end
    //levels start
    private ArrayList<Enemy> enemies; //temporarily holds level's enemy arraylist
    private ArrayList<LevelButton> levelButtons;
    private ArrayList<Level> levels;
    private Level currentLevel;
    //levels end
    //enemies start
    //game states start
    protected static enum GameState {MAINMENU, INGAME, GAMEOVER, SHOP, LEVELSELECT}
    protected static GameState gameState;
    protected static enum WeaponState {REGWEAPON, HYDRA}
    protected static WeaponState weaponState;
    //game states end
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
        mainMenuBackground = new Texture("sidewaysmenu.png");
        lawn = new Texture("lawn.jpeg");
        gameOverBackground = new Texture("gameover.png");
        shell = new Snailshell();
        jimmy = new Player();
        waterGun = new Weapon();
        hydra = new Hydra();
        water = new ArrayList<Projectile>();
        tap = new Vector3(); //location of tap
        house = new House();
        //game states start
        //buttons start
        startButtonMenu = new StartButton(width/2 - 215, height / 2 - 200);
        shopButtonMenu = new ShopButton(width - 150, height - 100);
        backButtonGameOver = new BackButton(width - 210, 10);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        loseButton = new LoseButton(width - 210, height - 210);
        hydraButton = new HydraButton(width - 210, height - 500);
        //buttons end
        //levels start
        levelButtons = new ArrayList<LevelButton>();
        levels = new ArrayList<Level>();
        for (int a = 0; a < numberOfLevels; a++) {
            if (a < 5)
                levelButtons.add(new LevelButton(10 + a * 210, 410));
            else //a >= 5
                levelButtons.add(new LevelButton(10 + (a - 5) * 210, 200));
            levels.add(new Level(a + 1));
        }
        currentLevel = new Level(0);
        //levels end
        resetGame();
    }
    public void resetGame() {
        camera.position.set(width/2, height/2, 0);
        gameState = GameState.MAINMENU;
        weaponState = WeaponState.REGWEAPON;
        House.hp = House.MaxHP;
        waterGun.waterLimit = 15;
        jimmy.curency = 0;
        //buttons start
        startButtonMenu.position.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.position.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        backButtonGameOver.position.set(backButtonGameOver.getXPos(), backButtonGameOver.getYPos());
        backButtonLevelSelect.position.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonShop.position.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        for (int a = 0; a < numberOfLevels; a++)
            levelButtons.get(a).position.set(levelButtons.get(a).getXPos(), levelButtons.get(a).getYPos());
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        //buttons end
        camera.position.set((float)width/2, (float)height/2, 0);
    }
    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(),0);
        return camera.unproject(tap);
    }
    public void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        /*
        *** main menu currently contains ***
         - main menu --> level select
         - main menu --> shop
        */
        if (gameState == GameState.MAINMENU) { //in main menu
            if (startButtonMenu.isPressed()) {startButtonMenu.pressedAction();} //go to level select
            if (shopButtonMenu.isPressed()) {shopButtonMenu.pressedAction();} //go to shop
        }
        /*
        *** shop currently contains ***
         - shop --> main menu
        */
        else if (gameState == GameState.SHOP) { //in shop
            if (backButtonShop.isPressed()) {backButtonShop.pressedAction();} //go to main menu
        }
        /*
        *** level select currently contains ***
         - level select --> in-game
         - level select --> main menu
        */
        else if (gameState == GameState.LEVELSELECT) { //in level select
            for (int a = 0; a < numberOfLevels; a++) {
                if (levelButtons.get(a).isPressed()) {
                    currentLevel = levels.get(a); //current level is now whatever level that was pressed
                    enemies = currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
                    levelButtons.get(a).pressedAction(); //go in-game
                }
            }
            if (backButtonLevelSelect.isPressed()) {backButtonLevelSelect.pressedAction();} //go to main menu
        }
        /*
        *** in-game currently contains ***
         - watergun logic
         - projectile logic
         - updates enemy arraylist
         - loss condition
         - in-game --> game over
        */
        else if (gameState == GameState.INGAME) { //in-game
            if (hydraButton.isPressed()) {
                if (weaponState == WeaponState.REGWEAPON) {weaponState = WeaponState.HYDRA;} //switch to hydra
                else if (weaponState == WeaponState.HYDRA) {weaponState = WeaponState.REGWEAPON;} //switch to regular gun
            }
            if (weaponState == WeaponState.REGWEAPON) {waterGun.Update(water);}
            else if (weaponState == WeaponState.HYDRA) {hydra.Update(water);}
            for (int i = 0; i < water.size(); i++) { //projectiles
                Projectile proj = water.get(i);
                proj.Update();
                if (proj.bound.y >= height) {water.remove(i);}
                if (proj.bound.y < 0) {water.remove(i);}
                boolean projectileHit = false;
                for (int a = 0; a < enemies.size(); a++) {
                    if (proj.bound.overlaps(enemies.get(a).bound)) {
                        projectileHit = true;
                        enemies.get(a).hp = enemies.get(a).hp - waterGun.str;
                        if (enemies.get(a).hp <= 0) {
                            shell.sprite.setPosition(enemies.get(a).bound.x, enemies.get(a).bound.y);
                            enemies.remove(a);
                            snaildead = true;
                            jimmy.curency += 10;
                        }
                    }
                }
                if (projectileHit) {water.remove(i);}
            }
            for (Enemy enemy : enemies) {
                enemy.Update(deltaTime);
                if (enemy.bound.overlaps(House.Housebounds)) {House.hp -= enemy.Attack * Gdx.graphics.getDeltaTime();}
            }
            if (House.hp <= 0 || (loseButton.bound.contains(getTapPosition().x, getTapPosition().y))) {gameState = GameState.GAMEOVER;} //loss condition
        }
        /*
        *** game over currently contains ***
         - disposes enemies arraylist
         - game over --> main menu
        */
        else if (gameState == GameState.GAMEOVER) { //in game over
            if (Gdx.input.justTouched() && backButtonGameOver.bound.contains(getTapPosition().x, getTapPosition().y)) {
                for (Enemy enemy : enemies) {enemy.dispose();} //need to dispose everything in the arraylist to save memory
                House.hp = House.MaxHP;
                waterGun.waterLimit = 15;
                backButtonGameOver.pressedAction(); //go to main menu
            }
        }
    }
    public void drawGame() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setScale(2);
        font.setColor(0, 0, 0, 1);
        /*
        *** main menu currently contains ***
         - shop button
         - level select button (play)
        */
        if (gameState == GameState.MAINMENU) { //in main menu
            batch.draw(mainMenuBackground, 0, 0);
            startButtonMenu.draw(batch);
            batch.draw(shopButtonMenu.image, shopButtonMenu.position.x, shopButtonMenu.position.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
        }
        /* level select currently contains
         - back button
         - levels buttons
        */
        else if (gameState == GameState.LEVELSELECT) { //in level select
            for (int a = 0; a < numberOfLevels; a++) {
                LevelButton lb = levelButtons.get(a);
                batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
            }
            batch.draw(backButtonLevelSelect.image, backButtonLevelSelect.position.x, backButtonLevelSelect.position.y);
            font.draw(batch, "Current state: level select", 10, height - 50);
        }
        /*
        *** shop currently contains ***
         - back button
        */
        else if (gameState == GameState.SHOP) { //in shop
            batch.draw(backButtonShop.image, backButtonShop.position.x, backButtonShop.position.y);
            font.draw(batch, "Current state: shop", 10, height - 50);
        }
        /* in-game currently contains ***
         - lose button
         - back button
         - jimmy
         - watergun
         - projectile
         - draws and animates enemies
        */
        else if (gameState == GameState.INGAME) { //in-game
            batch.draw(lawn, 0, 0);
            house.draw(batch, House.Housebounds.x, House.Housebounds.y);
            batch.draw(loseButton.image, loseButton.position.x, loseButton.position.y);
            if (weaponState == WeaponState.REGWEAPON) {waterGun.sprite.draw(batch);}
            if (weaponState == WeaponState.HYDRA) {hydra.sprite.draw(batch);}
            batch.draw(hydraButton.image, hydraButton.position.x, hydraButton.position.y);
            shell.sprite.draw(batch);
            for (Projectile proj : water) {proj.shot.draw(batch);}
            for (Enemy enemy : enemies) { //draws and animates enemies
                enemy.draw(batch, time);
                if (enemy.hp <= 0) {shell.sprite.draw(batch);}
            }
            font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);
            font.draw(batch, "Number of snails: " + enemies.size(), 10, 50);
            font.draw(batch, "Current state: in-game", 10, height - 50);
            font.draw(batch, "Water Amount: " + waterGun.waterLimit, 10, height - 100);
            font.draw(batch, "Snailshells: " + jimmy.curency, 10, height - 200);
            font.draw(batch, "HP: " + (int) House.hp, 10, height - 400);
        }
        /* game over screen currently contains ***
         - back button
        */
        else if (gameState == GameState.GAMEOVER) { //in game over
            batch.draw(gameOverBackground, 0, 0);
            batch.draw(backButtonGameOver.image, backButtonGameOver.position.x, backButtonGameOver.position.y);
            font.draw(batch, "Current state: game over", 10, height - 50);
        }
        font.draw(batch, "Resolution: " + width + ", " + height, 10, height); //we keep forgetting the screen resolution T___T
        batch.end();
    }
}