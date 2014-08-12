package com.missionbit.snailassalt;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
public class SnailAssalt extends ApplicationAdapter  {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public static OrthographicCamera camera;
    protected static int numberOfLevels = 10;
    private int width, height;
    private static Vector3 tap;
    private FreeTypeFontGenerator fontGenerator;
    private BitmapFont font;
    private Player jimmy;
    private House house;
    private int wavebar;
    private float time = 0;
    protected static Preferences preferences;
    //UI start
    private Sprite hpBar;
    //UI end
    //tutorial and credits start
    private Sprite credits, special, hurshalsface1, tutor1, tutor2, tutor3, tutor4, tutor5, tutor6, tutor7, tutor8;
    //tutorial and credits end
    //backgrounds start
    private Sprite menu, gameover, win, levelSelect, shop, laun;
    //backgrounds end
    //weapwns start
    private Weapon waterGun;
    private Hydra hydra;
    private Hose hose;
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
    protected static HoseBut hosebut;
    private RedoButton redoLevelButton;
    protected static SaltButton saltButton;
    private SpHydraBut spHydraBut;
    private SpSaltBut spSaltBut;
    private SpHosebutton spHoseBut;
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
    private SaltArm saltarm;

    //enemies start
    //game states start
    protected static enum GameState {
        MAINMENU, TUTORIAL, INGAME, GAMEOVER, WIN, SHOP, LEVELSELECT, CREDITS, CREDITShurshal
    }

    protected static GameState gameState, prevGameState;

    protected static enum TutorialState {PAGE1, PAGE2, PAGE3, PAGE4, PAGE5, PAGE6, PAGE7, PAGE8}

    protected static TutorialState tutState;

    protected static enum WeaponState {REGWEAPON, HYDRA, HOSE}

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

        //FONT STUFF
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("helvetica.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = fontGenerator.generateFont(parameter);

        shell = new ArrayList<Snailshell>();
        jimmy = new Player();
        tap = new Vector3(); //location of tap
        house = new House();

        //UI start
        Texture hpBarTexture = new Texture("hpbar.png");
        hpBar = new Sprite(hpBarTexture);
        hpBar.setSize(hpBarTexture.getWidth(), hpBarTexture.getHeight());
        hpBar.setPosition(30, height - 60);
        //UI end

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
        credits = new Sprite(new Texture("credits.png"));
        credits.setPosition(width / 2 - 305, 100);
        hurshalsface1 = new Sprite(new Texture("hurshal's face copy.png"));
        hurshalsface1.setPosition(width / 2 - 160, 0);
        hurshalsface1.setSize(2 * width / 3, 2 * height / 3);
        special = new Sprite(new Texture("hurshal.png"));
        special.setPosition(width / 2 - 428, height / 2 - 100);
        //credits end
        //tutorial start
        tutor1 = new Sprite(new Texture("tutorial 1.jpeg"));
        tutor1.setSize(width, height);
        tutor1.setPosition(0, 0);
        tutor2 = new Sprite(new Texture("tutorial 2.jpeg"));
        tutor2.setSize(width, height);
        tutor2.setPosition(0, 0);
        tutor3 = new Sprite(new Texture("tutorial 3.jpeg"));
        tutor3.setSize(width, height);
        tutor3.setPosition(0, 0);
        tutor4 = new Sprite(new Texture("tutorial4.jpeg"));
        tutor4.setSize(width, height);
        tutor4.setPosition(0, 0);
        tutor5 = new Sprite(new Texture("tutorial5.jpeg"));
        tutor5.setSize(width, height);
        tutor5.setPosition(0, 0);
        tutor6 = new Sprite(new Texture("tutorial6.jpeg"));
        tutor6.setSize(width, height);
        tutor6.setPosition(0, 0);
        tutor7 = new Sprite(new Texture("tutorial7.jpeg"));
        tutor7.setSize(width, height);
        tutor7.setPosition(0, 0);
        tutor8 = new Sprite(new Texture("tutorial8.jpeg"));
        tutor8.setSize(width, height);
        tutor8.setPosition(0, 0);

        //tutorial end
        //weapwns start
        currency = preferences.getInteger("currency", 0);
        waterGun = new Weapon();
        waterGun.enable = true;
        hose = new Hose();
        hydra = new Hydra();
        if (preferences.getInteger("hydra", 0) == 1) {
            hydra.enable = true;
        }
        water = new ArrayList<ThrowyThingy>();
        shakers = new ArrayList<Salt>();
        if (preferences.getInteger("salt", 0) == 1) {
            waterGun.enableSalt = true;
            hydra.enableSalt = true;
        }
        if (preferences.getInteger("hose", 0) == 1) {
            hose.enable = true;
        }
        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        saltarm=new SaltArm();
        //weapwns end
        //buttons start #iSuckAtCoding
        startButtonMenu = new StartButton(width / 2 - 325, height / 2 - 200);
        redoLevelButton = new RedoButton(width / 2 - 200, height / 2 - 200);
        redoLevelButton.sprite.setSize(redoLevelButton.image.getWidth() + (redoLevelButton.image.getWidth() / 3), startButtonMenu.buttonGetHeight());
        redoLevelButton.spriteNope.setSize(redoLevelButton.image.getWidth() + (redoLevelButton.image.getWidth() / 3), startButtonMenu.buttonGetHeight());

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
        spHydraBut = new SpHydraBut(0, height / 2);
        spSaltBut = new SpSaltBut(width / 2, height / 2);
        spHoseBut = new SpHosebutton(width / 3, height / 2);
        saltButton = new SaltButton(20, height - 600);
        hydraButton = new HydraButton(20, height - 200);
        hosebut = new HoseBut(20, height - 450);
        currency = 10000;
        //buttons end #iSuckAtCoding
        //levels start
        levelButtons = new ArrayList<LevelButton>();
        enemies = new ArrayList<Enemy>();
        levels = new ArrayList<Level>();
        for (int a = 0; a < numberOfLevels; a++) {
            if (a < 5) {
                levelButtons.add(new LevelButton(a * 210, 200));
            } else {
                levelButtons.add(new LevelButton((a - 5) * 210, 0));
            }
            levels.add(new Level(a + 1));
        }
        currentLevel = new Level(1);
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
        Weapon.currentSalt = Weapon.saltSupply;
        //buttons start
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        saltButton.position.set(saltButton.getXPos(), saltButton.getYPos());
        hosebut.position.set(hosebut.getXPos(), hosebut.getYPos());
        spHydraBut.position.set(spHydraBut.getXPos(), spHydraBut.getYPos());
        spSaltBut.position.set(spSaltBut.getXPos(), spSaltBut.getYPos());
        spHoseBut.position.set(spHoseBut.getXPos(), spHoseBut.getYPos());
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
        redoLevelButton.position.set(redoLevelButton.getXPos(), redoLevelButton.getYPos());
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
        if (gameState == GameState.MAINMENU) { //in main menu
            if (startButtonMenu.pressable() && startButtonMenu.isPressed()) {
                startButtonMenu.pressedAction();
            } //go to level select
            if (shopButtonMenu.pressable() && shopButtonMenu.isPressed()) {
                shopButtonMenu.pressedAction();
            } //go to shop
            if (creditsButton.isPressed()) {
                creditsButton.pressedAction();
            } //go to credits
            if (tutorialButton.isPressed()) {
                tutorialButton.pressedAction();
            } //go to tutorial
        } else if (gameState == GameState.SHOP) { //in shop
            if (backButtonShop.pressable() && backButtonShop.isPressed()) {
                backButtonShop.pressedAction();
            }
            if (currency > spHydraBut.price && spHydraBut.isPressed()) {
                currency -= spHydraBut.price;
                preferences.putInteger("hydra", 1);
                //preferences.flush();
            }
            if (preferences.getInteger("hydra", 0) == 1) {
                hydra.enable = true;
            }
            if (currency > spSaltBut.price && spSaltBut.isPressed()) {
                currency -= spSaltBut.price;
                preferences.putInteger("salt", 1);
                //preferences.flush();
            }
            if (currency >= spHoseBut.price && spHoseBut.isPressed()) {
                currency -= spHoseBut.price;
                preferences.putInteger("hose", 1);
                //preferences.flush();
            }
            if (preferences.getInteger("hose", 0) == 1) {
                hose.enable = true;
            }
            if (preferences.getInteger("salt", 0) == 1) {
                waterGun.enableSalt = true;
                hydra.enableSalt= true;
                hose.enableSalt= true;
            }
          

        } else if (gameState == GameState.TUTORIAL) {
            if (tutState == TutorialState.PAGE8) {
                if (backButtonTutorial.isPressed()) {
                    backButtonTutorial.pressedAction();
                } //go to next page of tutorial
            } else { //tutState == TutorialState.PAGE4
                if (nextTutorial.isPressed()) {
                    nextTutorial.pressedAction();
                } //go to next page of tutorial
            }
        } else if (gameState == GameState.LEVELSELECT) { //in level select
            for (int a = 0; a < numberOfLevels; a++) {
                if (levelButtons.get(a).isPressed()) {
                    currentLevel = levels.get(a); //current level is now whatever level that was pressed
                    enemies = currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
                    currentLevel.enemyCount = 0;
                    levelButtons.get(a).pressedAction(); //go in-game
                }
            }
            if (backButtonLevelSelect.pressable() && backButtonLevelSelect.isPressed()) {
                gameState = GameState.MAINMENU;
            }
        } else if (gameState == GameState.CREDITS) {
            if (Gdx.input.justTouched()) {
                gameState = GameState.CREDITShurshal;
            }
        } else if (gameState == GameState.CREDITShurshal) {
            if (backButtonCredits.pressable() && backButtonCredits.isPressed()) {
                backButtonCredits.pressedAction();
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
        else if (gameState == GameState.INGAME) {
            if (hydraButton.isPressed()) {
                bulletType = bulletType.WATER;//in-game
            } else if (saltButton.isPressed()) {
                bulletType = bulletType.SALT;
            }
            if (hydra.enable) {
                if (hydraButton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {
                        weaponState = WeaponState.HYDRA;
                    } //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {
                        weaponState = WeaponState.HOSE;
                    } //switch to regular gun
                    else if (weaponState == WeaponState.HOSE) {
                        weaponState = WeaponState.REGWEAPON;
                    }
                }
            }

            if (weaponState == WeaponState.REGWEAPON) {
                if (waterGun.enable) {
                    waterGun.Update(water);
                }
                if (waterGun.enableSalt) {
                    if (saltButton.isPressed()) {
                        bulletType = bulletType.SALT;
                    }
                    if (bulletType == BulletType.WATER) {
                        waterGun.Update(water);
                    }
                    if (bulletType == BulletType.SALT) {
                        saltarm.sprite.setRotation(waterGun.rot);
                        waterGun.Update2(shakers);
                        //saltarm.Update2(shakers);
                    }
                }
            }
            else if (weaponState == WeaponState.HYDRA) {

                if (bulletType == bulletType.WATER) {
                    if (hydra.enable) {
                        hydra.Update(water);
                    }
                }
                if (hydra.enableSalt) {
                    if (saltButton.isPressed()) {
                        bulletType = bulletType.SALT;
                    }

                    if (bulletType == BulletType.SALT) {
                        saltarm.sprite.setRotation(waterGun.rot);
                        hydra.Update2(shakers);
                        //saltarm.Update2(shakers);
                    }
                }
            }
            else if (weaponState == WeaponState.HOSE) {
                    if(bulletType==bulletType.WATER){
                        if(hose.enable){hose.Update(water);}
                    }
                    if(bulletType== bulletType.SALT){
                        if(hose.enableSalt){
                            saltarm.sprite.setRotation(waterGun.rot);
                            //hose.Update2(shakers);
                            saltarm.Update2(shakers);
                        }
                    }
                }
                for (int i = 0; i < water.size(); i++) { //projectiles
                    ThrowyThingy proj = water.get(i);
                    proj.Update();
                    if (proj.bound.y >= height) {
                        water.remove(i);
                    }
                    if (proj.bound.y < 0) {
                        water.remove(i);
                    }
                    boolean projectileHit = false;
                    for (int a = 0; a < enemies.size(); a++) {
                        if (proj.bound.overlaps(enemies.get(a).bound)) {
                            projectileHit = true;
                            enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                            if (enemies.get(a).hp <= 0) {
                                shell.add(new Snailshell((int) enemies.get(a).bound.x, (int) enemies.get(a).bound.y));
                                enemies.remove(a);
                                a--;
                                currentLevel.enemyCount++;
                                currency += 5;
                                Weapon.currentWater += 10;
                                if (Weapon.currentWater >= Weapon.waterSupply) {
                                    Weapon.currentWater = 50;
                                }
                            }
                        }
                    }
                    if (projectileHit) {
                        water.remove(i);
                    }
                }
                for (int c = 0; c < shakers.size(); c++) { //projectiles
                    Salt bullet = shakers.get(c);
                    bullet.Update();
                    if (bullet.bound.y >= height) {
                        shakers.remove(c);
                    }
                    if (bullet.bound.y < 0) {
                        shakers.remove(c);
                    }
                    boolean projectileHit = false;
                    for (int a = 0; a < enemies.size(); a++) {
                        if (bullet.bound.overlaps(enemies.get(a).bound)) {
                            projectileHit = true;
                            enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                            if (enemies.get(a).hp <= 0) {
                                shell.add(new Snailshell((int) enemies.get(a).bound.x, (int) enemies.get(a).bound.y));
                                enemies.remove(a);
                                a--;
                                currency += 5;//TODO:
                            }
                        }
                    }
                    if (projectileHit) {
                        shakers.remove(c);
                    }
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
                if (House.hp <= 0 || loseButton.isPressed()) {
                    gameState = GameState.GAMEOVER;
                }
                if (enemies.size() == 0) {
                    gameState = GameState.WIN;
                }
            }

        else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
            //in game over OR win
            preferences.putInteger("currency", currency);//TODO: place when level ends
            //preferences.flush();
            shell.clear();
            water.clear();
            shakers.clear();
            bombs.clear();
            droppings.clear();
            House.hp = House.maxHP;
            Weapon.currentWater = Weapon.waterSupply;
            Weapon.currentSalt=Weapon.saltSupply;
            currentLevel.enemyCount = 0;
            bulletType = BulletType.WATER;
            if (backButtonGameEnd.pressable() && backButtonGameEnd.isPressed()) {
                backButtonGameEnd.pressedAction();
            } //go to main menu
            if (shopButtonGameEnd.pressable() && shopButtonGameEnd.isPressed()) { //go to shop
                if (gameState == GameState.GAMEOVER) {
                    prevGameState = GameState.GAMEOVER;
                } else {
                    prevGameState = GameState.WIN;
                }
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
        if (gameState == GameState.MAINMENU) {
            batch.begin();
            menu.draw(batch);
            startButtonMenu.sprite.draw(batch);
            if (preferences.getInteger("tutorial", 0) == 2) {
                tutorialButton.sprite.draw(batch);
            }
            creditsButton.sprite.draw(batch);
            shopButtonMenu.sprite.draw(batch);
            //font.draw(batch, "Current state: main menu", 10, height);
            batch.end();
        } else if (gameState == GameState.CREDITS) {
            batch.begin();
            levelSelect.draw(batch);
            credits.draw(batch);
            batch.end();
        } else if (gameState == GameState.CREDITShurshal) {
            batch.begin();
            levelSelect.draw(batch);
            hurshalsface1.draw(batch);
            special.draw(batch);
            backButtonCredits.draw(batch);
            //if(backButtonCredits.isPressed()){
            //    batch.draw(backButtonCredits.Shade,backButtonCredits.getXPos(),backButtonCredits.getYPos());
            //}
            batch.end();
        }
        else if (gameState == GameState.LEVELSELECT) { //in level select
            batch.begin();
            levelSelect.draw(batch);
            for (int a = 0; a < numberOfLevels; a++) {
                LevelButton lb = levelButtons.get(a);
                batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
            }
            batch.draw(backButtonLevelSelect.image, backButtonLevelSelect.position.x, backButtonLevelSelect.position.y);
            font.draw(batch, "Current state: level select", 10, height);
            batch.end();
        }
        else if (gameState == GameState.SHOP) { //in shop
            batch.begin();
            //font.draw(batch, "Current state: shop", 10, height - 50);
            shop.draw(batch);
            spHydraBut.sprite.draw(batch);
            spSaltBut.sprite.draw(batch);
            spHoseBut.sprite.draw(batch);
            batch.draw(backButtonShop.sprite, backButtonShop.position.x, backButtonShop.position.y);
            font.draw(batch, "salt owned? " + waterGun.enableSalt, width / 2, height);
            font.draw(batch, "hydra owned? " + hydra.enable, 0, height);
            font.draw(batch, "Number of shells: " + currency, 10, 50);
            batch.end();
            //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            //shapeRenderer.setColor(Color.RED);
            //shapeRenderer.rect(spHydraBut.bound.x,spHydraBut.bound.y,spHydraBut.buttonGetWidth(),spHydraBut.buttonGetHeight());
            //shapeRenderer.setColor(Color.BLUE);
            //shapeRenderer.rect(spSaltBut.bound.x,spSaltBut.bound.y,spSaltBut.buttonGetWidth(),spSaltBut.buttonGetHeight());
            //shapeRenderer.end();
        }
        else if (gameState == GameState.TUTORIAL) {
            batch.begin();
            if (tutState == TutorialState.PAGE1) {
                tutor1.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE2) {
                tutor2.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE3) {
                tutor3.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE4) {
                tutor4.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE5) {
                tutor5.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE6) {
                tutor6.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE7) {
                tutor7.draw(batch);
                nextTutorial.draw(batch);
            } else if (tutState == TutorialState.PAGE8) {
                tutor8.draw(batch);
                nextTutorial.draw(batch);
            }
            batch.end();
        } else if (gameState == GameState.INGAME) { //in-game
            batch.begin();
            laun.draw(batch);
            house.draw(batch);
            batch.draw(loseButton.sprite, loseButton.position.x, loseButton.position.y);
            saltButton.sprite.draw(batch);
            font.draw(batch,"hydra salt "+hydra.enableSalt,300,400);
            batch.draw(jimmy.sprite, jimmy.bound.x, jimmy.bound.y);
            if (weaponState == WeaponState.REGWEAPON) {
                font.draw(batch, "current weap reg ", 350, 350);
                batch.draw(hydraButton.sprite, hydraButton.position.x, hydraButton.position.y);
                if(bulletType==bulletType.WATER){
                    waterGun.sprite.draw(batch);
                }else if(bulletType==bulletType.SALT){
                    saltarm.sprite.draw(batch);
                }

            } else if (weaponState == WeaponState.HYDRA) {
                font.draw(batch, "current weap hydra", 350, 350);
                batch.draw(hosebut.sprite, hydraButton.position.x, hydraButton.position.y);
                if(bulletType==bulletType.WATER) {
                    hydra.sprite.draw(batch);
                }else if(bulletType==bulletType.SALT){
                    saltarm.sprite.draw(batch);
                }

            } else if (weaponState == WeaponState.HOSE) {
                font.draw(batch, "current weap hose", 350, 350);
                batch.draw(hydraButton.watergun, hydraButton.getXPos(), hydraButton.getYPos());
                if(bulletType==bulletType.WATER){
                    hose.sprite.draw(batch);
                }else if(bulletType==bulletType.SALT){
                    saltarm.sprite.draw(batch);
                }

            }
            for (ThrowyThingy proj : water) {
                proj.sprite.draw(batch);
            }
            for (Salt bullet : shakers) {
                bullet.sprite.draw(batch);
            }
            for (Droppings droppies : droppings) {
                droppies.draw(batch);
            }
            for (BombDrop bomb : bombs) {
                bomb.draw(batch);
            }
            for (Enemy enemy : enemies) { //draws and animates enemies
                enemy.draw(batch, time);
            }
            for (Snailshell snailshell : shell) {
                batch.draw(snailshell.image, snailshell.bounds.x, snailshell.bounds.y);
                //snailshell.sprite.draw(batch);
            }
            //font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);
            font.draw(batch, "Current state: in-game", 10, height);
            font.draw(batch, "Snailshells: " + currency, 10, height - 200);
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(0, 0, width, 40);
            shapeRenderer.rect(3, height - 60, House.maxHP * House.healthScale, 50);
            shapeRenderer.rect(3, height - 110, width/5, 50);
            shapeRenderer.rect(3, height - 160, width / 5, 50);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(3, height - 60, House.hp * House.healthScale, 50);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(3, height - 110, (Weapon.currentWater/ Weapon.waterSupply)*(width/5), 50);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(0, 0, ((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width, 40);
            //TODO fix invisible barz
            shapeRenderer.setColor(Color.YELLOW);
            shapeRenderer.rect(3, height - 160, (Weapon.currentSalt/Weapon.saltSupply) *( width /5 ), 50);
            shapeRenderer.end();
            batch.begin();
            font.setColor(1, 1, 1, 1);
            font.draw(batch, (int) House.hp + "/" + (int) House.maxHP, 50, height - 10);
            font.draw(batch, (int) Weapon.currentWater + "/" + (int) Weapon.waterSupply, 50, height - 60);
            font.draw(batch, (int) Weapon.currentSalt + "/" +(int) Weapon.saltSupply,50,height-110);
            hpBar.draw(batch);
            batch.end();
        }
            else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) { //in game over/win
                batch.begin();
                if (gameState == GameState.GAMEOVER) {
                    gameover.draw(batch);
                    redoLevelButton.spriteNope.draw(batch);
                }
                else {
                    win.draw(batch);
                    redoLevelButton.sprite.draw(batch);

                }
                backButtonGameEnd.spriteNope.draw(batch);
                shopButtonGameEnd.sprite.draw(batch);
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
