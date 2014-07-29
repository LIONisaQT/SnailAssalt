package com.missionbit.snailassalt;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
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
    private float time = 0;
    protected static Preferences preferences;
    //tutorial and credits start
    private Sprite text1, hurshal1, hurshalsface1, tutor1, tutor2, tutor3, tutor4;
    //tutorial and credits end
    //backgrounds start
    private Sprite menu, gameover, win, levelSelect, shop, laun;
    //backgrounds end
    //weapwns start
    private Weapon waterGun;
    private Hydra hydra;
    private ArrayList<ThrowyThingy> water; //holds watergun shots
    private ArrayList<Salt> shakers;
    private ArrayList<Snailshell> shell;
    protected static int currency;
    //weapwns end
    //buttons start
    private StartButton startButtonMenu;
    private CreditsButton creditsButton;
    private ShopButton shopButtonMenu, shopButtonGameEnd;
    private BackButton backButtonShop, backButtonGameEnd, backButtonLevelSelect, backButtonCredits, backButtonTutorial; //different back buttons because their position will most likely be different
    private NextButton nextTutorial;
    private LoseButton loseButton;
    protected static HydraButton hydraButton;
    private RedoButton redoLevelButton;
    protected static SaltButton saltButton;
    private SpHydraBut spHydraBut;
    private SpSaltBut spSaltBut;
    private TutorialButton tutorialButton;
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
    protected static enum GameState {MAINMENU, TUTORIAL, INGAME, GAMEOVER, WIN, SHOP, LEVELSELECT, CREDITS, CREDITShurshal}
    protected static GameState gameState, prevGameState;
    protected static enum TutorialState {PAGE1, PAGE2, PAGE3, PAGE4}
    protected static TutorialState tutState;
    protected static enum WeaponState {REGWEAPON, HYDRA}
    protected static enum BulletType {SALT, WATER}
    protected static BulletType bulletType;
    protected static WeaponState weaponState;
    private Sound victorysound;
    private Sound defeatsound;
    private Sound snaildeadsound;
    private Sound currencysound;
    private Sound waterlimitsound;
    private Sound gameoversound;
    private Sound househpsound;
    private Music music;
    //game states end
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGame();
        drawGame();
    }
    public void create() {
        batch = new SpriteBatch();
        preferences = new Preferences("Preferences");
        shapeRenderer = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        shell = new ArrayList<Snailshell>();
        jimmy = new Player();
        tap = new Vector3(); //location of tap
        house = new House();
        //background start
        menu = new Sprite(new Texture("sidewaysmenu.png"));
        menu.setSize(width, height);
        levelSelect = new Sprite(new Texture("levelscreen.png"));
        levelSelect.setSize(width, height);
        shop = new Sprite(new Texture("levelscreen.png"));
        shop.setSize(width, height);
        gameover = new Sprite(new Texture("gameover.png"));
        gameover.setSize(width, height);
        win = new Sprite(new Texture("win screen.png"));
        win.setSize(width, height);
        laun = new Sprite(new Texture("lawn.jpeg"));
        laun.setSize(width, height);
        //background end
        //credits start
        text1 = new Sprite(new Texture("credits.png"));
        text1.setPosition(width / 2 - 305, 100);
        hurshalsface1 = new Sprite(new Texture("hurshal's face copy.png"));
        hurshalsface1.setPosition(width / 2 - 160, 0);
        hurshalsface1.setSize(2 * width / 3, 2 * height / 3);
        hurshal1 = new Sprite(new Texture("hurshal.png"));
        hurshal1.setPosition(width / 2 - 428, height / 2 - 100);
        //credits end
        //tutorial start
        tutor1 = new Sprite(new Texture("tutorial1.jpeg"));
        tutor1.setSize(width, height);
        tutor1.setPosition(0,0);
        tutor2 = new Sprite(new Texture("tutorial2.jpeg"));
        tutor2.setSize(width, height);
        tutor2.setPosition(0,0);
        tutor3 = new Sprite(new Texture("tutorial3.png"));
        tutor3.setSize(width, height);
        tutor3.setPosition(0,0);
        tutor4 = new Sprite(new Texture("tutorial4.jpeg"));
        tutor4.setSize(width, height);
        tutor4.setPosition(0,0);
        //tutorial end
        //weapwns start
        currency = preferences.getInteger("currency", 0);
        waterGun = new Weapon();
        waterGun.enable = true;
        hydra = new Hydra();
        if (preferences.getInteger("hydra", 0) == 1) {
            hydra.enable = true;
        }
        water = new ArrayList<ThrowyThingy>();
        shakers = new ArrayList<Salt>();
        if (preferences.getInteger("salt", 0) == 1) {
            waterGun.enableSalt = true;
            hydra.enableSalt=true;
        }
        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        //weapwns end
        //buttons start #iSuckAtCoding
        startButtonMenu = new StartButton(width / 2 - 325, height / 2 - 200);
        redoLevelButton = new RedoButton(width / 2 - 200, height / 2 - 200);
        redoLevelButton.sprite.setSize(redoLevelButton.image.getWidth() + (redoLevelButton.image.getWidth()/3), startButtonMenu.buttonGetHeight());
        shopButtonMenu = new ShopButton(startButtonMenu.getXPos() + startButtonMenu.image.getWidth() + 10, startButtonMenu.getYPos());
        shopButtonGameEnd = new ShopButton(redoLevelButton.getXPos() + redoLevelButton.sprite.getWidth() + 10, redoLevelButton.getYPos());
        backButtonGameEnd = new BackButton(width - 210, 10);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        backButtonTutorial = new BackButton(width - 210, height - backButtonShop.buttonGetHeight());
        backButtonCredits = new BackButton(width - 210, 10);
        creditsButton = new CreditsButton(width / 2 - 100, startButtonMenu.getYPos() - startButtonMenu.buttonGetHeight() - 10);
        tutorialButton = new TutorialButton(width / 2 - 90 + creditsButton.sprite.getWidth(), creditsButton.getYPos());
        nextTutorial = new NextButton(width - 200, height - 200);
        loseButton = new LoseButton(width - 210, height - 210);
        spHydraBut = new SpHydraBut(0, 0);
        spSaltBut = new SpSaltBut(width / 2, 0);
        saltButton = new SaltButton(20, height - 600);
        hydraButton = new HydraButton(20, height - 200);
        //buttons end #iSuckAtCoding
        //levels start
        levelButtons = new ArrayList<LevelButton>();
        enemies = new ArrayList<Enemy>();
        levels = new ArrayList<Level>();
        for (int a = 0; a < numberOfLevels; a++) {
            if (a < 5) {levelButtons.add(new LevelButton(a * 210, 200));}
            else {levelButtons.add(new LevelButton((a - 5) * 210, 0));}
            levels.add(new Level(a + 1));
        }
        currentLevel = new Level(0);
        //levels end
       /* victorysound = Gdx.audio.newSound(Gdx.files.internal("victorysound.mp3"));
        defeatsound = Gdx.audio.newSound(Gdx.files.internal("defeatsound.mp3"));
        hydrasound = Gdx.audio.newSound(Gdx.files.internal("hydra.mp3"));
        househpsound = Gdx.audio.newSound(Gdx.files.internal("househp.mp3"));
        gameoversound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        currencysound = Gdx.audio.newSound(Gdx.files.internal("money.mp3"));
        waterlimitsound = Gdx.audio.newSound(Gdx.files.internal ("waterlimit.mp3"));
        snaildeadsound = Gdx.audio.newSound(Gdx.files.internal("snaildead.mp3"));*/
        music = Gdx.audio.newMusic(Gdx.files.internal("background music.mp3"));
        resetGame();
    }
    public void resetGame() {
        camera.position.set(width / 2, height / 2, 0);
        gameState = GameState.MAINMENU;
        tutState = TutorialState.PAGE1;
        prevGameState = null;
        weaponState = WeaponState.REGWEAPON;
        bulletType = BulletType.WATER;
        House.hp = House.maxHP;
        Weapon.currentWater = Weapon.waterSupply;
        //buttons start
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        saltButton.position.set(saltButton.getXPos(), saltButton.getYPos());
        spHydraBut.position.set(spHydraBut.getXPos(), spHydraBut.getYPos());
        spSaltBut.position.set(spSaltBut.getXPos(), spSaltBut.getYPos());
        startButtonMenu.position.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        //startButtonMenu.sprite.setSize(height,width);
        shopButtonMenu.position.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        shopButtonGameEnd.position.set(shopButtonGameEnd.getXPos(), shopButtonGameEnd.getYPos());
        backButtonGameEnd.position.set(backButtonGameEnd.getXPos(), backButtonGameEnd.getYPos());
        backButtonLevelSelect.position.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonCredits.position.set(backButtonCredits.getXPos(), backButtonCredits.getYPos());
        backButtonShop.position.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        backButtonTutorial.position.set(backButtonTutorial.getXPos(), backButtonTutorial.getYPos());
        nextTutorial.position.set(nextTutorial.getXPos(), nextTutorial.getYPos());
        creditsButton.position.set(creditsButton.getXPos(), creditsButton.getYPos());
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        redoLevelButton.position.set(redoLevelButton.getXPos()   , redoLevelButton.getYPos());
        for (int a = 0; a < numberOfLevels; a++)
            levelButtons.get(a).position.set(levelButtons.get(a).getXPos(), levelButtons.get(a).getYPos());
    }
    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(tap);
    }
    public void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        music.play();
        /*
        *** main menu currently contains ***
         - main menu --> level select
         - main menu --> shop
         - main menu --> tutorial
         - main menu --> credits
        */
        if (gameState == GameState.MAINMENU) { //in main menu
            if (startButtonMenu.pressable() && startButtonMenu.isPressed()) {startButtonMenu.pressedAction();} //go to level select
            if (shopButtonMenu.pressable() && shopButtonMenu.isPressed()) {shopButtonMenu.pressedAction();} //go to shop
            if (creditsButton.isPressed()) {creditsButton.pressedAction();} //go to credits
            if (tutorialButton.isPressed()) {tutorialButton.pressedAction();} //go to tutorial
        }
        /*
        *** shop currently contains ***
         - shop --> whatever the previous game state was
         - purchasable merch (hydra, salt)
         - saves purchases
        */
        else if (gameState == GameState.SHOP) { //in shop
            if (backButtonShop.pressable() && backButtonShop.isPressed()) {backButtonShop.pressedAction();}
            if (getTapPosition().x < width/2 && Gdx.input.justTouched() && currency > spHydraBut.price) {
                currency -= spHydraBut.price;
                preferences.putInteger("hydra", 1);
                preferences.flush();
            }
            if (preferences.getInteger("hydra", 0) == 1) {hydra.enable = true;}
            if (getTapPosition().x > width/2 && Gdx.input.justTouched() && currency > spSaltBut.price) {
                currency -= spSaltBut.price;
                preferences.putInteger("salt", 1);
                preferences.flush();
            }
            if (preferences.getInteger("salt", 0) == 1) {
                waterGun.enableSalt = true;
                hydra.enable = true;
            }
        }
        /*
        *** tutorial currently contains ***
         - 4 pages
         */
        else if (gameState == GameState.TUTORIAL) {
            if (tutState == TutorialState.PAGE1 || tutState == TutorialState.PAGE2 || tutState == TutorialState.PAGE3) {
                if (nextTutorial.isPressed()) {nextTutorial.pressedAction();} //go to next page of tutorial
            }
            else { //tutState == TutorialState.PAGE4
                if (backButtonTutorial.isPressed()) {backButtonTutorial.pressedAction();} //go to next page of tutorial
            }
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
            if (backButtonLevelSelect.pressable() && backButtonLevelSelect.isPressed()) {gameState = GameState.MAINMENU;}
        }
        else if (gameState == GameState.CREDITS) {
            if (Gdx.input.justTouched()) {gameState = GameState.CREDITShurshal;}
        }
        else if (gameState == GameState.CREDITShurshal) {
            if (backButtonCredits.pressable() && backButtonCredits.isPressed()) {
                backButtonCredits.pressedAction();
                gameState = GameState.MAINMENU;
                if (backButtonLevelSelect.pressable() && backButtonLevelSelect.isPressed()) {gameState = GameState.MAINMENU;}
            }
        }
        /*
        *** in-game currently contains ***
         - watergun and salt shaker logic
         - projectile logic
         - droppings logic
         - updates enemy arraylist
         - loss condition
         - win condition
         - in-game --> game over
         - in-game --> win
        */
        else if (gameState == GameState.INGAME) { //in-game
            if (hydra.enable) {
                if (hydraButton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {weaponState = WeaponState.HYDRA;} //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {weaponState = WeaponState.REGWEAPON;} //switch to regular gun
                }
            }
            if (weaponState == WeaponState.REGWEAPON) {
                if (waterGun.enable) {waterGun.Update(water);}
                if (waterGun.enableSalt) {
                    if (saltButton.isPressed()) {
                        if (bulletType == BulletType.WATER) {bulletType = BulletType.SALT;}
                        else if (bulletType == BulletType.SALT) {bulletType = BulletType.WATER;}
                    }
                    if (bulletType == BulletType.WATER) {waterGun.Update(water);}
                    if (bulletType == BulletType.SALT) {waterGun.Update2(shakers);
                    }
                }
            }
            else if (weaponState == WeaponState.HYDRA) {
                if (hydra.enable) {hydra.Update(water);}
            }
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
                            shell.add(new Snailshell((int) enemies.get(a).bound.x, (int) enemies.get(a).bound.y));
                            enemies.remove(a);
                            a--;
                            currency += 5;
                            Weapon.currentWater += 10;
                            if (Weapon.currentWater >= Weapon.waterSupply) {Weapon.currentWater = 100;}
                        }
                    }
                }
                if (projectileHit) {water.remove(i);}
            }
            for (int c = 0; c < shakers.size(); c++) { //projectiles
                Salt bullet = shakers.get(c);
                bullet.Update();
                if (bullet.bound.y >= height) {shakers.remove(c);}
                if (bullet.bound.y < 0) {shakers.remove(c);}
                boolean projectileHit = false;
                for (int a = 0; a < enemies.size(); a++) {
                    if (bullet.bound.overlaps(enemies.get(a).bound)) {
                        projectileHit = true;
                        enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                        if (enemies.get(a).hp <= 0) {
                            shell.add(new Snailshell((int) enemies.get(a).bound.x, (int) enemies.get(a).bound.y));
                            enemies.remove(a);
                            a--;
                            currency += 10;
                            Weapon.currentWater += 10;
                            if (Weapon.currentWater >= Weapon.waterSupply) {
                                Weapon.currentWater = 100;
                            }
                        }
                    }
                }
                if (projectileHit) {shakers.remove(c);}
            }
            for (int b = 0; b < shell.size(); b++) {
                shell.get(b).Update();
                if (shell.get(b).bounds.y > height) {
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
            if(enemies.size()==0){gameState= GameState.WIN;}
        }
        /*
        *** game over currently contains ***
         - disposes enemies arraylist
         - game over --> main menu
        */
        else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
            //in game over OR win
            preferences.putInteger("currency", currency);//TODO: place when level ends
            preferences.flush();
            shell.clear();
            water.clear();
            shakers.clear();
            bombs.clear();
            droppings.clear();
            House.hp = House.maxHP;
            Weapon.currentWater = Weapon.waterSupply;
            bulletType = BulletType.WATER;
            if (backButtonGameEnd.pressable() && backButtonGameEnd.isPressed()) {backButtonGameEnd.pressedAction();} //go to main menu
            if (shopButtonGameEnd.pressable() && shopButtonGameEnd.isPressed()) { //go to shop
                if (gameState == GameState.GAMEOVER) {prevGameState = GameState.GAMEOVER;}
                else {prevGameState = GameState.WIN;}
                shopButtonGameEnd.pressedAction();
            }
            if (redoLevelButton.pressable() && redoLevelButton.isPressed()) {
                enemies = currentLevel.getEnemies(); //reloads level's enemies
                redoLevelButton.pressedAction(); //go to in-game
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
        if (gameState == GameState.MAINMENU) {
            batch.begin();
            menu.draw(batch);
            startButtonMenu.sprite.draw(batch);
            if (preferences.getInteger("tutorial", 0) == 1) {
                tutorialButton.sprite.draw(batch);
            }
            creditsButton.sprite.draw(batch);
            shopButtonMenu.sprite.draw(batch);
            //font.draw(batch, "Current state: main menu", 10, height);
            batch.end();
        }
        else if(gameState == GameState.CREDITS){
            batch.begin();
            levelSelect.draw(batch);
            text1.draw(batch);
            batch.end();
        }
        else if(gameState == GameState.CREDITShurshal){
            batch.begin();
            levelSelect.draw(batch);
            hurshalsface1.draw(batch);
            hurshal1.draw(batch);
            backButtonCredits.draw(batch);
            batch.end();
        }
        /* level select currently contains
         - back button
         - levels buttons
        */
        else if (gameState == GameState.LEVELSELECT) { //in level select
            batch.begin();
            levelSelect.draw(batch);
            for (int a = 0; a < numberOfLevels; a++) {
                LevelButton lb = levelButtons.get(a);
                batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
            }

            batch.draw(backButtonLevelSelect.image, backButtonLevelSelect.position.x, backButtonLevelSelect.position.y);
            //font.draw(batch, "Current state: level select", 10, height);
            batch.end();
        }
        /*
        *** shop currently contains ***
         - back button
         - buy hydra
         - buy salt
        */
        else if (gameState == GameState.SHOP) { //in shop
            batch.begin();
            //font.draw(batch, "Current state: shop", 10, height - 50);
            shop.draw(batch);
            spHydraBut.sprite.draw(batch);
            spSaltBut.sprite.draw(batch);
            batch.draw(backButtonShop.sprite, backButtonShop.position.x, backButtonShop.position.y);
            font.draw(batch,"salt owned? " + waterGun.enableSalt, width / 2, height);
            font.draw(batch,"hydra owned? " + hydra.enable, 0, height);
            font.draw(batch, "Number of shells: " + currency, 10, 50);
            batch.end();
        }
        /*
        *** tutorial currently contains ***
         - back button
         */
        else if (gameState == GameState.TUTORIAL) {
            batch.begin();
            if (tutState == TutorialState.PAGE1) {
                tutor1.draw(batch);
                nextTutorial.draw(batch);
            }
            else if (tutState == TutorialState.PAGE2) {
                tutor2.draw(batch);
                nextTutorial.draw(batch);
            }
            else if (tutState == TutorialState.PAGE3) {
                tutor3.draw(batch);
                nextTutorial.draw(batch);
            }
            else {
                tutor4.draw(batch);
                backButtonTutorial.draw(batch);
            }
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
            laun.draw(batch);
            house.draw(batch);
            batch.draw(loseButton.sprite, loseButton.position.x, loseButton.position.y);
            saltButton.sprite.draw(batch);
            batch.draw(jimmy.sprite,jimmy.bound.x,jimmy.bound.y);
            if (weaponState == WeaponState.REGWEAPON) {waterGun.sprite.draw(batch);}
            if (weaponState == WeaponState.HYDRA) {hydra.sprite.draw(batch);}
            batch.draw(hydraButton.sprite, hydraButton.position.x, hydraButton.position.y);
            for (ThrowyThingy proj : water) {proj.sprite.draw(batch);}
            for (Salt bullet:shakers){bullet.sprite.draw(batch);}
            for (Droppings droppies : droppings) {droppies.draw(batch);}
            for (BombDrop bomb : bombs) {bomb.draw(batch);}
            for (Enemy enemy : enemies) { //draws and animates enemies
                enemy.draw(batch, time);
            }
            for(Snailshell snailshell: shell) {
                batch.draw(snailshell.image,snailshell.bounds.x,snailshell.bounds.y);
            }
            //font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);

            font.draw(batch, "Current state: in-game", 10, height - 50);
            font.draw(batch, "Water Amount: " + Weapon.currentWater, 10, height - 100);
            font.draw(batch, "Snailshells: " +currency, 10, height - 200);
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
         - redo level button
         - shop button
        */
        else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) { //in game over/win
            batch.begin();
            if (gameState == GameState.GAMEOVER) {gameover.draw(batch);}
            else {win.draw(batch);}
            backButtonGameEnd.spriteNope.draw(batch);
            redoLevelButton.sprite.draw(batch);
            shopButtonGameEnd.sprite.draw(batch);
            batch.end();
        }
        batch.begin();
        font.draw(batch, "lin sucks", 10, height - 50);
        batch.end();
    }
    public void addSlime(Droppings dropping) {
        droppings.add(dropping);
    }
    public void addBomb(BombDrop bomb) {
        bombs.add(bomb);
    }
}