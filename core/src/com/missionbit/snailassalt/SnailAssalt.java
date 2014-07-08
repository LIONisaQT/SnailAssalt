package com.missionbit.snailassalt;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public static OrthographicCamera camera;
    protected static int numberOfLevels = 10;
    private int width, height;
    private static Vector3 tap;
    private BitmapFont font;
    private Player jimmy;
    private House house;
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
    private ArrayList<ThrowyThingy> water; //holds watergun shots
    private ArrayList<Snailshell> shell;
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
    private ArrayList<Droppings> droppings;
    private ArrayList<BombDrop> bombs;
    //enemies start
    //game states start
    protected static enum GameState {MAINMENU, INGAME, GAMEOVER, SHOP, LEVELSELECT}
    protected static GameState gameState;
    protected static enum WeaponState {REGWEAPON, HYDRA}
    protected static WeaponState weaponState;
    //game states end
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGame();
        drawGame();
    }
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        mainMenuBackground = new Texture("sidewaysmenu.png");
        lawn = new Texture("lawn.jpeg");
        gameOverBackground = new Texture("gameover.png");
        shell = new ArrayList<Snailshell>();
        jimmy = new Player();
        tap = new Vector3(); //location of tap
        house = new House();
        //weapwns start
        waterGun = new Weapon();
        hydra = new Hydra();
        water = new ArrayList<ThrowyThingy>();
        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        //weapwns end
        //buttons start
        startButtonMenu = new StartButton(width / 2 - 215, height / 2 - 200);
        shopButtonMenu = new ShopButton(width - 150, height - 100);
        backButtonGameOver = new BackButton(width - 210, 10);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        loseButton = new LoseButton(width - 210, height - 210);
        hydraButton = new HydraButton(width - 210, height - 500);
        //buttons end
        //levels start
        levelButtons = new ArrayList<LevelButton>();
        enemies = new ArrayList<Enemy>();
        levels = new ArrayList<Level>();
        for (int a = 0; a < numberOfLevels; a++) {
            if (a < 5) {levelButtons.add(new LevelButton(10 + a * 210, 410));}
            else {levelButtons.add(new LevelButton(10 + (a - 5) * 210, 200));}
            levels.add(new Level(a + 1));
        }
        currentLevel = new Level(0);
        //levels end
        resetGame();
    }
    public void resetGame() {
        camera.position.set(width / 2, height / 2, 0);
        gameState = GameState.MAINMENU;
        weaponState = WeaponState.REGWEAPON;
        House.hp = House.MaxHP;
        Weapon.currentWater = Weapon.waterSupply;
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
        jimmy.curency = 0;
        weaponState = WeaponState.REGWEAPON;
    }
    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(), 0);
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
            if (backButtonLevelSelect.isPressed()) {gameState = GameState.MAINMENU;}
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
                ThrowyThingy proj = water.get(i);
                proj.Update();
                if (proj.bound.y >= height) {water.remove(i);}
                if (proj.bound.y < 0) {water.remove(i);}
                boolean projectileHit = false;
                for (int a = 0; a < enemies.size(); a++) {
                    if (proj.bound.overlaps(enemies.get(a).bound)) {
                        projectileHit = true;
                        enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                        if (enemies.get(a).hp <= 0) {
                            shell.add(new Snailshell(enemies.get(a).bound.x,enemies.get(a).bound.y));
                            enemies.remove(a);
                            a--;
                            jimmy.curency += 10;
                            Weapon.currentWater +=10;
                            if(Weapon.currentWater >=Weapon.waterSupply){
                                Weapon.currentWater =100;
                            }
                        }
                    }
                }
                if (projectileHit) {water.remove(i);}
            }
            for(int b=0;b<shell.size();b++){
                shell.get(b).Update();
                if(shell.get(b).bounds.y>height){
                    shell.remove(b);
                    b--;

                }
            }
            for (Enemy enemy : enemies) {
                enemy.Update(deltaTime, this);
                for (int i = 0; i < bombs.size(); i++) {
                    BombDrop bomb = bombs.get(i);
                    if (enemy.bound.overlaps(bomb.bound) && !(enemy instanceof FlyingSnail)) {
                        enemy.speed.x = enemy.speed.x + 2;
                        bombs.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < droppings.size(); i++) {
                    Droppings droppies = droppings.get(i);
                    if (enemy.bound.overlaps(droppies.bound) && !(enemy instanceof AcidSnail)) {
                        enemy.speed.x++;
                        droppings.remove(i);
                        i--;
                    }
                    if (enemy.bound.overlaps(House.Housebounds))
                        House.hp -= enemy.Attack * Gdx.graphics.getDeltaTime();
                }
            }
            if (House.hp <= 0 || loseButton.isPressed()) {gameState = GameState.GAMEOVER;}
        }
        /*
        *** game over currently contains ***
         - disposes enemies arraylist
         - game over --> main menu
        */




        else if (gameState == GameState.GAMEOVER) { //in game over
            if (backButtonGameOver.isPressed()) {
                backButtonGameOver.pressedAction(); //go to main menu
                for (Enemy enemy : enemies) {enemy.dispose();}
                shell.clear();
                water.clear();
                bombs.clear();
                droppings.clear();
                House.hp = House.MaxHP;
                Weapon.currentWater = Weapon.waterSupply;
            }
        }
    }
    public void drawGame() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        font.setScale(2);
        font.setColor(0, 0, 0, 1);
        /*
        *** main menu currently contains ***
         - shop button
         - level select button (play)
        */
        if (gameState == GameState.MAINMENU) { //in main menu
            batch.begin();
            batch.draw(mainMenuBackground, 0, 0);
            startButtonMenu.draw(batch);
            batch.draw(shopButtonMenu.image, shopButtonMenu.position.x, shopButtonMenu.position.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
            batch.end();
        }
        /* level select currently contains
         - back button
         - levels buttons
        */
        else if (gameState == GameState.LEVELSELECT) { //in level select
            batch.begin();
            for (int a = 0; a < numberOfLevels; a++) {
                LevelButton lb = levelButtons.get(a);
                batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
            }
            batch.draw(backButtonLevelSelect.image, backButtonLevelSelect.position.x, backButtonLevelSelect.position.y);
            font.draw(batch, "Current state: level select", 10, height - 50);
            batch.end();
        }
        /*
        *** shop currently contains ***
         - back button
        */
        else if (gameState == GameState.SHOP) { //in shop
            batch.begin();
            batch.draw(backButtonShop.image, backButtonShop.position.x, backButtonShop.position.y);
            font.draw(batch, "Current state: shop", 10, height - 50);
            batch.end();
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
            batch.begin();
            batch.draw(lawn, 0, 0);
            house.draw(batch, House.Housebounds.x, House.Housebounds.y);
            batch.draw(loseButton.image, loseButton.position.x, loseButton.position.y);

            batch.draw(jimmy.sprite,jimmy.bound.x,jimmy.bound.y);
            if (weaponState == WeaponState.REGWEAPON) {waterGun.sprite.draw(batch);}
            if (weaponState == WeaponState.HYDRA) {hydra.sprite.draw(batch);}
            batch.draw(hydraButton.image, hydraButton.position.x, hydraButton.position.y);
            for (ThrowyThingy proj : water) {proj.shot.draw(batch);}
            for (Droppings droppies : droppings) {droppies.draw(batch);}
            for (BombDrop bomb : bombs) {bomb.draw(batch);}
            for (Enemy enemy : enemies) { //draws and animates enemies
                enemy.draw(batch, time);
            }

            for(Snailshell snailshell: shell) {
               // snailshell.sprite.setPosition(snailshell.bounds.x,snailshell.bounds.y);
                batch.draw(snailshell.image,snailshell.bounds.x,snailshell.bounds.y);

            }


            font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);
            font.draw(batch, "Number of snails: " + enemies.size(), 10, 50);
            font.draw(batch, "Current state: in-game", 10, height - 50);
            font.draw(batch, "Water Amount: " + Weapon.currentWater, 10, height - 100);
            font.draw(batch, "Snailshells: " + jimmy.curency, 10, height - 200);
            font.draw(batch, "HP: " + (int) House.hp, 10, height - 400);
            font.draw(batch, "num of shells: " + shell.size(), 500, height - 400);
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(3, 50, House.hp * House.healthScale, 30);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(3, 70, Weapon.currentWater * Weapon.waterScale, 40);
            shapeRenderer.end();
        }
        /* game over screen currently contains ***
         - back button
        */
        else if (gameState == GameState.GAMEOVER) { //in game over
            batch.begin();
            batch.draw(gameOverBackground, 0, 0);
            batch.draw(backButtonGameOver.image, backButtonGameOver.position.x, backButtonGameOver.position.y);
            font.draw(batch, "Current state: game over", 10, height - 50);
            batch.end();
        }
    }
    public void addSlime(Droppings dropping) {
        droppings.add(dropping);
    }
    public void addBomb(BombDrop bomb) {
        bombs.add(bomb);
    }
}