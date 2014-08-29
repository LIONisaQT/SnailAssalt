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

public class SnailAssalt extends ApplicationAdapter {
    private int buttonstate = 0; //this may be able to be moved
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer; //allows shapes to be drawn (e.g. rect)
    public static OrthographicCamera camera; //currently locked to width and height, doesn't move
    private float width, height; //screen resolution
    private static Vector3 tap; //holds the position of tap location
    protected static float time = 0; //used with deltaTime

    protected static Preferences preferences; //saving3hard5me
    private FreeTypeFontGenerator fontGenerator; //handles .ttf --> .fnt
    protected static BitmapFont font;
    private MyInputProcessor inputProcessor; //handles touchUp and touchDown

    private Player jimmy, rachel;
    protected static int currency;
    private House house;

    //SPRITES
    private Sprite
            /* credit pages */ credits, specialThanks, hurshalsface1,
            /* backgrounds */ gameover, win, laun,
            /* UI */ hpBar, waterBar, saltBar, progBarSnail, sign;

    //WEAPONS
    protected static Weapon waterGun; //default watergun
    protected static Hydra hydra; //hydra
    protected static Hose hose; //hose
    protected SaltArm saltarm; //salt
    private ArrayList<ThrowyThingy> water; //holds watergun shots
    private ArrayList<SaltProjectile> shakers; //holds salt shakers thrown
    protected boolean projectileHit;

    //LOADERS
    private static MainMenu mainMenu;
    private static Shop shop;
    private static Tutorial tutorial;
    private static LevelSelect levelSelect;
    private static SnailInfo snailInfo;

    //BUTTONS
    private RachelButton rachelButton; //select rachel to play as
    private JimmyButton jimmyButton; //select jimmy to play as

    private ShopButton shopButtonGameEnd;

    private QuitButton quitButton;
    private RedoButton redoLevelButton;
    private NextLevelButton nextLevelButton;
    private BackButton backButtonGameEnd, backButtonCredits;

    //WEAPON BUTTONS
    protected static HydraButton hydraButton;
    protected static HoseButton hoseButton;
    protected static SaltButton saltButton;

    //ENEMY-RELATED
    private ArrayList<Droppings> droppings; //acid snail droppings
    private ArrayList<BombDrop> bombs; //flying snail droppings
    private ArrayList<GhostSnails> deadSnails; //holds dead snails that go to heaven

    //ENUMS AND STATES
    protected static enum GameState {CHARACTERSELECT, MAINMENU, TUTORIAL, INGAME, GAMEOVER, WIN, SHOP, LEVELSELECT, CREDITS, CREDITS_HURSHAL, INFO}
    protected static GameState gameState, prevGameState;
    protected static enum WeaponState {REGWEAPON, HYDRA, HOSE}

    protected static WeaponState weaponState;
    protected static enum BulletType {SALT, WATER}
    protected static BulletType bulletType;

    //SOUNDS/MUSIC
    private Sound victorysound, defeatsound, snaildeadsound, currencysound, waterlimitsound, gameoversound, househpsound;
    private Music music;

    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGame();
        drawGame();
    }

    public void create() {
        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);
        batch = new SpriteBatch();
        preferences = new Preferences("Preferences");
        shapeRenderer = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);

        deadSnails = new ArrayList<GhostSnails>();
        jimmy = new Player();
        rachel = new Player();
        tap = new Vector3(); //location of tap
        house = new House();
        currency = preferences.getInteger("currency", 0);
        currency = 100000000; //temporarily set for debugging purposes

        //FONT STUFF
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Chicken Butt.ttf")); //replace font with whatever
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(26 * (width / 1196)); //lin did the math and i guess it works
        font = fontGenerator.generateFont(parameter);

        //HP BAR
        Texture hpBarTexture = new Texture("images/house/hpBar.png");
        hpBar = new Sprite(hpBarTexture);
        hpBar.setSize(width / 4, hpBarTexture.getHeight());
        hpBar.setPosition(width - hpBar.getWidth() - 5, height - 50);

        //WATER BAR
        Texture waterBarTexture = new Texture("images/house/waterGauge.png");
        waterBar = new Sprite(waterBarTexture);
        waterBar.setSize(width / 4, waterBarTexture.getHeight());
        waterBar.setPosition(width - 2 * hpBar.getWidth() - 10, height - 50);

        //SALT BAR
        Texture saltBarTexture = new Texture("images/house/saltBar.png");
        saltBar = new Sprite(saltBarTexture);
        saltBar.setSize(width / 4, saltBarTexture.getHeight());
        saltBar.setPosition(width - 3 * hpBar.getWidth() - 15, height - 50);

        //PROGRESSION BAR
        Texture progBar = new Texture("images/enemies/snailMeh.png");
        progBarSnail = new Sprite(progBar);
        progBarSnail.setSize(progBar.getWidth(), progBar.getHeight());
        progBarSnail.setPosition(0, 0);

        //BACKGROUNDS
        gameover = new Sprite(new Texture("images/backgrounds/gameover.png"));
        gameover.setSize(width, height);
        win = new Sprite(new Texture("images/backgrounds/win.png"));
        win.setSize(width, height);
        laun = new Sprite(new Texture("images/backgrounds/lawn.jpeg"));
        laun.setSize(width, height);


        //CREDITS
        credits = new Sprite(new Texture("images/backgrounds/credits.png"));
        credits.setSize(width / 1196 * credits.getWidth(), height / 720 * credits.getHeight());
        credits.setPosition(width / 2 - credits.getWidth()/2, height / 2 - credits.getHeight()/2);
        hurshalsface1 = new Sprite(new Texture("images/backgrounds/hurshalsFace.png"));
        hurshalsface1.setSize(width / 1196 * hurshalsface1.getWidth(), height / 720 * hurshalsface1.getHeight());
        hurshalsface1.setPosition(width - hurshalsface1.getWidth(), 0);
        specialThanks = new Sprite(new Texture("images/backgrounds/specialThanks.png"));
        specialThanks.setSize(width / 1196 * specialThanks.getWidth(), height / 720 * specialThanks.getHeight());
        specialThanks.setPosition(width / 2 - specialThanks.getWidth()/2, height / 2 - 100);

        //LOADERS
        mainMenu = new MainMenu(this);
        mainMenu.create();
        shop = new Shop(this);
        shop.create();
        tutorial = new Tutorial(this);
        tutorial.create();
        levelSelect = new LevelSelect(this);
        levelSelect.create();
        snailInfo = new SnailInfo(this);
        snailInfo.create();

        //WEAPONS
        Weapon.saltSupply = preferences.getInteger("saltsupply", 0);
        waterGun = new Weapon();
        waterGun.enable = true;
        hose = new Hose();
        hydra = new Hydra();
        saltarm = new SaltArm();
        water = new ArrayList<ThrowyThingy>();
        shakers = new ArrayList<SaltProjectile>();

        //PREFERENCES CHECK
        if (preferences.getInteger("hydra", 0) == 1)
            hydra.enable = true;
        if (preferences.getInteger("hose", 0) == 1)
            hose.enable = true;
        if (preferences.getInteger("salt", 0) == 1) {
            waterGun.enableSalt = true;
            hydra.enableSalt = true;
            hose.enableSalt = true;
        }

        //ENEMY-RELATED

        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        deadSnails = new ArrayList<GhostSnails>();

        //BACK BUTTONS
        backButtonGameEnd = new BackButton(0, 0);
        backButtonCredits = new BackButton(width - backButtonGameEnd.sprite.getWidth(), height - shop.backButton.getHeight());

        //JIMMY AND RACHEL BUTTONS
        rachelButton = new RachelButton(width / 2 + 100 , 50);
        rachelButton.bound.set(rachelButton.getXPos(), rachelButton.getYPos(), width / 2 - width / 8, height - height / 8);
        jimmyButton = new JimmyButton(100, 50);
        jimmyButton.bound.set(jimmyButton.getXPos(), jimmyButton.getYPos(), width / 2 - width / 8, height - height / 8);

        //IN-GAME / GAME OVER / WIN BUTTONS
        QuitButton tempQuitButton = new QuitButton(0, 0); //temporary quit button to get the sprite's dimensions
        quitButton = new QuitButton(0, height - tempQuitButton.sprite.getHeight());
        nextLevelButton = new NextLevelButton(width - width / (6.39f), 0);
        redoLevelButton = new RedoButton(width / 2 - width / 6, height / 2 - width / 6);
        redoLevelButton.sprite.setSize(redoLevelButton.sprite.getWidth() + redoLevelButton.sprite.getWidth() / 8, mainMenu.startButton.getHeight());
        redoLevelButton.spriteNope.setSize(redoLevelButton.sprite.getWidth()+ redoLevelButton.sprite.getWidth() / 8 , mainMenu.startButton.getHeight());
        shopButtonGameEnd = new ShopButton(redoLevelButton.getXPos() + redoLevelButton.sprite.getWidth() + width / 36, redoLevelButton.getYPos());

        //IN-GAME WEAPON BUTTONS
        SaltButton tempSaltButton = new SaltButton(0, 0);
        saltButton = new SaltButton(width - tempSaltButton.sprite.getWidth(), height/5);
        HydraButton tempHydraButton = new HydraButton(0, 0);
        hydraButton = new HydraButton(width - tempHydraButton.sprite.getWidth(), (4*height)/(5));
        HoseButton tempHoseButton = new HoseButton(0, 0);
        hoseButton = new HoseButton(width - tempHoseButton.sprite.getWidth(), hydraButton.getYPos());

        //SOUNDS
        /* victorysound = Gdx.audio.newSound(Gdx.files.internal("victorysound.mp3"));
        defeatsound = Gdx.audio.newSound(Gdx.files.internal("defeatsound.mp3"));
        hydrasound = Gdx.audio.newSound(Gdx.files.internal("hydra.mp3"));
        househpsound = Gdx.audio.newSound(Gdx.files.internal("househp.mp3"));
        gameoversound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        currencysound = Gdx.audio.newSound(Gdx.files.internal("money.mp3"));
        waterlimitsound = Gdx.audio.newSound(Gdx.files.internal ("waterlimit.mp3"));
        snaildeadsound = Gdx.audio.newSound(Gdx.files.internal("snaildead.mp3"));*/

        //MUSIC
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/bgm/menu_bgm.mp3"));

        resetGame();
    }

    public void resetGame() {
        camera.position.set(width / 2, height / 2, 0);
        gameState = GameState.MAINMENU; //set game state
        prevGameState = null; //set previous game state
        weaponState = WeaponState.REGWEAPON; //set first weapon
        bulletType = BulletType.WATER; //set first bullet type

        House.hp = House.maxHP; //set current hp to max hp
        Weapon.currentWater = Weapon.waterSupply; //set current water to max water
        Weapon.saltSupply = Weapon.currentSalt; //sets max salt to salt bought; dynamically changes depending on how much used in a level

        //WEAPON BUTTONS
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        saltButton.position.set(saltButton.getXPos(), saltButton.getYPos());
        hoseButton.position.set(hoseButton.getXPos(), hoseButton.getYPos());

        //JIMMY AND RACHEL BUTTONS
        rachelButton.position.set(rachelButton.getXPos(), rachelButton.getYPos());
        jimmyButton.position.set(jimmyButton.getXPos(), jimmyButton.getYPos());

        //SHOP BUTTONS
        shopButtonGameEnd.position.set(shopButtonGameEnd.getXPos(), shopButtonGameEnd.getYPos());

        //BACK BUTTONS
        backButtonGameEnd.position.set(backButtonGameEnd.getXPos(), backButtonGameEnd.getYPos());
        backButtonCredits.position.set(backButtonCredits.getXPos(), backButtonCredits.getYPos());

        //IN-GAME / GAME OVER / WIN
        quitButton.position.set(quitButton.getXPos(), quitButton.getYPos());
        redoLevelButton.position.set(redoLevelButton.getXPos(), redoLevelButton.getYPos());
        nextLevelButton.position.set(nextLevelButton.getXPos(), nextLevelButton.getYPos());
    }

    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(tap);
    }

    public void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        music.play(); //TODO: move this to specific game states

        //GAME STATES
        if (gameState == GameState.CHARACTERSELECT){
            if (rachelButton.isPressed()) {
                buttonstate = 1;
            }
            if (rachelButton.touchup() && buttonstate == 1) {
                buttonstate = 0;
                rachel.enable = true;
                gameState = GameState.LEVELSELECT;
            }
            if (jimmyButton.isPressed()) {
                buttonstate = 1;
            }
            if (jimmyButton.touchup() && buttonstate == 1) {
                buttonstate = 0;
                rachel.enable = false;
                gameState = GameState.LEVELSELECT;
            }
        } else if (gameState == GameState.MAINMENU) {
            mainMenu.update();
        } else if (gameState == GameState.SHOP) {
            shop.update();
        } else if (gameState == GameState.TUTORIAL) {
            tutorial.update();
        } else if (gameState == GameState.LEVELSELECT) {
            levelSelect.update();
        } else if (gameState == GameState.INFO) {
            snailInfo.update();
        } else if (gameState == GameState.CREDITS) {
            if (Gdx.input.justTouched())
                gameState = GameState.CREDITS_HURSHAL;
        } else if (gameState == GameState.CREDITS_HURSHAL) {
            if (backButtonCredits.touchup()) {
                backButtonCredits.pressedAction();
            }
        } else if (gameState == GameState.INGAME) {
            progBarSnail.setPosition(((float)LevelSelect.currentLevel.enemyCount / LevelSelect.currentLevel.totalEnemies) * width - (progBarSnail.getWidth() / 2), 0);
            if (hydraButton.isPressed())
                bulletType = BulletType.WATER;
            else if (saltButton.isPressed())
                bulletType = BulletType.SALT;

            //this seems to only run if we have hydra, but not hose?
            if (hydra.enable) {
                if (hydraButton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON)
                        weaponState = WeaponState.HYDRA;
                    else if (weaponState == WeaponState.HYDRA)
                        weaponState = WeaponState.HOSE;
                    else if (weaponState == WeaponState.HOSE)
                        weaponState = WeaponState.REGWEAPON;
                }
            }

            //ACTUAL WATER GUN CODE
            if (weaponState == WeaponState.REGWEAPON) {
                if (waterGun.enable)
                    waterGun.Update(water);
                if (waterGun.enableSalt) {
                    if (saltButton.isPressed())
                        bulletType = BulletType.SALT;
                    if (bulletType == BulletType.SALT) {
                        saltarm.sprite.setRotation(Weapon.rot);
                        waterGun.Update2(shakers);
                        saltarm.Update2(shakers);
                    }
                }
            } else if (weaponState == WeaponState.HYDRA) {
                if (bulletType == BulletType.WATER) {
                    if (hydra.enable)
                        hydra.Update(water);
                }
                if (hydra.enableSalt) {
                    if (saltButton.isPressed())
                        bulletType = BulletType.SALT;
                    if (bulletType == BulletType.SALT) {
                        saltarm.sprite.setRotation(Weapon.rot);
                        hydra.Update2(shakers);
                        saltarm.Update2(shakers);
                    }
                }
            } else if (weaponState == WeaponState.HOSE) {
                if (bulletType == BulletType.WATER){
                    if (hose.enable)
                        hose.Update(water);
                }
                if (bulletType == BulletType.SALT) {
                    if (hose.enableSalt) {
                        saltarm.sprite.setRotation(Weapon.rot);
                        hose.Update2(shakers);
                        saltarm.Update2(shakers);
                    }
                }
            }

            //WATER PROJECTILE INTERACTION
            for (int i = 0; i < water.size(); i++) {
                ThrowyThingy proj = water.get(i);
                proj.Update();
                if (proj.bound.y >= height) {
                    water.remove(i);
                }
                if (proj.bound.y < 0) {
                    water.remove(i);
                }
                boolean projectileHit = false;
                for (int a = 0; a < LevelSelect.enemies.size(); a++) {
                    if (proj.bound.overlaps(LevelSelect.enemies.get(a).bound)) {
                        projectileHit = true;
                        LevelSelect.enemies.get(a).hp = LevelSelect.enemies.get(a).hp - Weapon.str;
                        LevelSelect.enemies.get(a).startFlash(.1f);
                        if (LevelSelect.enemies.get(a).hp <= 0) {
                            deadSnails.add(new GhostSnails((int)LevelSelect.enemies.get(a).bound.x, (int)LevelSelect.enemies.get(a).bound.y));
                            LevelSelect.enemies.remove(a);
                            a--;
                            LevelSelect.currentLevel.enemyCount++;
                            currency += 5;
                            Weapon.currentWater += 10;
                            if (Weapon.currentWater >= Weapon.waterSupply) {
                                Weapon.currentWater = 50;
                            }
                        }
                    }
                }
                if (projectileHit) {
                    water.get(i).dispose();
                    water.remove(i);
                }
            }

            //SALT SHAKER PROJECTILE INTERACTION
            for (int c = 0; c < shakers.size(); c++) {
                SaltProjectile bullet = shakers.get(c);
                bullet.Update();
                if (bullet.bound.y >= height) {
                    shakers.remove(c);
                }
                if (bullet.bound.y < 0) {
                    shakers.remove(c);
                }
                boolean projectileHit = false;
                for (int a = 0; a < LevelSelect.enemies.size(); a++) {
                    if (bullet.bound.overlaps(LevelSelect.enemies.get(a).bound)) {
                        projectileHit = true;
                        LevelSelect.enemies.get(a).hp = LevelSelect.enemies.get(a).hp - Weapon.str;
                        LevelSelect.enemies.get(a).startFlash(.1f);
                        if (LevelSelect.enemies.get(a).hp <= 0) {
                            deadSnails.add(new GhostSnails((int)LevelSelect.enemies.get(a).bound.x, (int)LevelSelect.enemies.get(a).bound.y));
                            LevelSelect.enemies.get(a).dispose();
                            LevelSelect.enemies.remove(a);
                            LevelSelect.currentLevel.enemyCount++;
                            a--;
                            currency += 5;
                        }
                    }
                }
                if (projectileHit){
                    shakers.remove(c);
                }
            }

            //GHOST SNAILS
            for (int b = 0; b < deadSnails.size(); b++) {
                deadSnails.get(b).Update();
                if (deadSnails.get(b).bounds.y > height) {
                    deadSnails.get(b).dispose();
                    deadSnails.remove(b);
                    b--;
                }
            }

            //ENEMY CODE
            for (Enemy enemy : LevelSelect.enemies) {
                enemy.Update(deltaTime, this);

                //FLYING SNAILS DROPPING BOMBS (can we move this?)
                for (int i = 0; i < bombs.size(); i++) {
                    BombDrop bomb = bombs.get(i);
                    if (enemy.bound.overlaps(bomb.bound) && !(enemy instanceof FlyingSnail)) {
                        enemy.speed.x = enemy.speed.x + 2;
                        bombs.remove(i);
                        i--;
                    }
                }

                //ACID SNAILS DROPPING SLIMES (can we move this?)
                for (int i = 0; i < droppings.size(); i++) {
                    Droppings droppies = droppings.get(i);
                    if (enemy.bound.overlaps(droppies.bound) && !(enemy instanceof AcidSnail)) {
                        enemy.speed.x++;
                        droppings.get(i).dispose();
                        droppings.remove(i);
                        i--;
                    }
                }

                //ENEMIES HITTING HOUSE
                if (enemy.bound.overlaps(House.Housebounds))
                    House.hp -= enemy.Attack * Gdx.graphics.getDeltaTime();
            }

            //PROGRESSION BAR
            progBarSnail.setPosition(((float)LevelSelect.currentLevel.enemyCount /LevelSelect.currentLevel.totalEnemies) * width - progBarSnail.getWidth() / 2, -progBarSnail.getHeight() / 2);

            //LOSE / VICTORY CONDITIONS
            if (House.hp <= 0 || quitButton.isPressed())
                gameState = GameState.GAMEOVER;
            if (LevelSelect.enemies.size() == 0)
                gameState = GameState.WIN;
        } else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
            preferences.putInteger("currency", currency);
            //preferences.flush();

            //DISPOSE
            for (GhostSnails shells : deadSnails) {shells.dispose();}
            for (ThrowyThingy waters : water) {waters.dispose();}
            for (Droppings derpies : droppings) {derpies.dispose();}
            for (Enemy enemy : LevelSelect.enemies) {enemy.dispose();}
            for (BombDrop booms : bombs) {booms.dispose();}

            //CLEAR
            deadSnails.clear();
            water.clear();
            shakers.clear();
            bombs.clear();
            droppings.clear();

            //RESET
            House.hp = House.maxHP;
            Weapon.currentWater = Weapon.waterSupply;
            Weapon.saltSupply = Weapon.currentSalt;
            LevelSelect.currentLevel.enemyCount = 0;
            bulletType = BulletType.WATER;
            weaponState = WeaponState.REGWEAPON;

            //BUTTONS
            if (backButtonGameEnd.isPressed()) {
                buttonstate = 1;
            }
            if (backButtonGameEnd.touchup() && buttonstate == 1) {
                backButtonGameEnd.pressedAction();
                buttonstate = 0;
            }
            if (shopButtonGameEnd.touchup()) { //go to shop
                if (gameState == GameState.GAMEOVER)
                    prevGameState = GameState.GAMEOVER;
                else
                    prevGameState = GameState.WIN;
                shopButtonGameEnd.pressedAction();
            }
            if (redoLevelButton.touchup()) {
                LevelSelect.enemies = LevelSelect.currentLevel.getEnemies(); //reloads level's enemies
                redoLevelButton.pressedAction(); //go to in-game
            }
            if (nextLevelButton.isPressed() && LevelSelect.currentLevelNumber<=8 && gameState != GameState.GAMEOVER){
                LevelSelect.enemies = LevelSelect.currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
                LevelSelect.currentLevelNumber++;
                LevelSelect.currentLevel = LevelSelect.levels.get(LevelSelect.currentLevelNumber);
                LevelSelect.currentLevel.enemyCount = 0;
                gameState = GameState.INGAME;
            }
        }
    }

    public void drawGame() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        font.setScale((float) ((width / 1196) * (1.4)));
        font.setColor(0, 0, 0, 1);
        if (gameState == GameState.CHARACTERSELECT) {
            batch.begin();
            batch.draw(jimmyButton.sprite, jimmyButton.bound.x, jimmyButton.bound.y, width / 2 - width / 8, height - height / 8);
            batch.draw(rachelButton.sprite, rachelButton.bound.x, rachelButton.bound.y, width / 2 - width / 8, height - height / 8);
            font.draw(batch, "Would you like to play as:", width / 6, height - 10);
            font.draw(batch, "Jimmy", jimmyButton.getXPos() + 20, jimmyButton.getYPos());
            font.draw(batch, "Rachel", rachelButton.getXPos() + 20, rachelButton.getYPos());
            batch.end();
        } else if (gameState == GameState.MAINMENU) {
            mainMenu.draw();
        } else if (gameState == GameState.CREDITS) {
            batch.begin();
            credits.draw(batch);
            batch.end();
        } else if (gameState == GameState.CREDITS_HURSHAL) {
            batch.begin();
            hurshalsface1.draw(batch);
            specialThanks.draw(batch);
            backButtonCredits.sprite.draw(batch);
            if (backButtonCredits.isPressed()) {
                backButtonCredits.spriteShade.draw(batch);
            }
            batch.end();
        } else if (gameState == GameState.LEVELSELECT) {
            levelSelect.draw();
        } else if (gameState == GameState.SHOP) {
            shop.draw();
        } else if (gameState == GameState.TUTORIAL) {
            tutorial.draw();
        } else if (gameState == GameState.INFO) {
            snailInfo.draw();
        } else if (gameState == GameState.INGAME) {
            batch.begin();
            laun.draw(batch);
            house.draw(batch);
            saltButton.sprite.draw(batch);

            //PLAYERS
            if (!rachel.enable) {
                batch.draw(jimmy.sprite, width - width / 10, jimmy.bound.y, (width / 1196) * jimmy.sprite.getWidth(), (height / 720) * jimmy.sprite.getHeight());
                waterGun.sprite.setPosition(jimmy.bound.x, 38f / 260f * (height / 720 * jimmy.sprite.getHeight()) + jimmy.bound.y);
                hydra.sprite.setPosition(jimmy.bound.x, 38f / 260f * (height / 720 * jimmy.sprite.getHeight()) + jimmy.bound.y);
            } else if (rachel.enable) { //TODO: wtf fix this
                batch.draw(rachel.rachel, width - width/10, rachel.bound.y, (width/1196)* rachel.rachel.getWidth(),(height/720)*rachel.rachel.getHeight());
                waterGun.sprite.setPosition(jimmy.bound.x, 38f/260f * (height/720*jimmy.sprite.getHeight()) +jimmy.bound.y);
                hydra.sprite.setPosition(jimmy.bound.x, 38f/260f * (height/720*jimmy.sprite.getHeight()) +jimmy.bound.y);
            }

            //PROJECTILES
            for (ThrowyThingy proj : water)
                proj.sprite.draw(batch);
            for (SaltProjectile bullet : shakers)
                bullet.sprite.draw(batch);

            //ENEMY-RELATED
            for (Droppings droppies : droppings)
                droppies.draw(batch);
            for (BombDrop bomb : bombs)
                bomb.draw(batch);
            for (Enemy enemy : LevelSelect.enemies)
                enemy.draw(batch, time);
            for (GhostSnails snailshell : deadSnails)
                snailshell.sprite.draw(batch);

            //DEBUG MESSAGES (REMOVE WHEN GAME IS FINISHED)
            font.draw(batch, "number of dead snails: " + deadSnails.size(), 200, 200);
            font.draw(batch, "hydra: salt enabled? -- " + hydra.enableSalt, 300, 400);
            font.draw(batch, "Current level: " + LevelSelect.currentLevel.getLevelNumber(), 10, 90);
            batch.end();

            //GUI
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            int topBarXPos = 50;
            int barHeight = 30;

            //GUI BACKGROUND
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.rect(0, height - height / 8, width, height / 8);

            //EMPTY BARS
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(0, 0, width, 20);
            shapeRenderer.rect(width - hpBar.getWidth() - 5, height - topBarXPos, width / 4, barHeight);
            shapeRenderer.rect(width - 2 * hpBar.getWidth() - 10, height - topBarXPos, width / 4, barHeight);
            shapeRenderer.rect(width - 3 * hpBar.getWidth() - 15, height - topBarXPos, width / 4, barHeight);

            //HP BAR
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(width - hpBar.getWidth() - 5, height - topBarXPos, (House.hp / House.maxHP) * (width / 4), barHeight);

            //WATER BAR
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(width - 2 * hpBar.getWidth() - 10, height - topBarXPos, (Weapon.currentWater / Weapon.waterSupply) * (width / 4), barHeight);

            //SALT BAR
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(width - 3 * hpBar.getWidth() - 15, height - topBarXPos, (Weapon.currentSalt / Weapon.saltSupply) * (width / 4), barHeight);

            //PROGRESS BAR
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(0, 0, ((float)LevelSelect.currentLevel.enemyCount / LevelSelect.currentLevel.totalEnemies) * width, 40);

            shapeRenderer.end();

            //GUI RELATED
            batch.begin();
            progBarSnail.draw(batch);
            hpBar.draw(batch);
            waterBar.draw(batch);
            saltBar.draw(batch);
            quitButton.sprite.draw(batch); //quit button is drawn on the bar
            font.draw(batch, (int) House.hp + "/" + (int) House.maxHP, width - hpBar.getWidth(), height - 2 * barHeight);
            font.draw(batch, (int) Weapon.currentWater + "/" + (int) Weapon.waterSupply, width - 2 * hpBar.getWidth(), height - 2 * barHeight);
            font.draw(batch, (int) Weapon.currentSalt + "/" + (int) Weapon.saltSupply, width - 3 * hpBar.getWidth(), height - 2 * barHeight);

            //WEAPONS
            if (weaponState == WeaponState.REGWEAPON) {
                font.draw(batch, "current weapon: regular", 350, 350);
                hydraButton.sprite.draw(batch);
                if (bulletType == BulletType.WATER)
                    waterGun.sprite.draw(batch);
                else if (bulletType == BulletType.SALT)
                    saltarm.sprite.draw(batch);
            } else if (weaponState == WeaponState.HYDRA) {
                font.draw(batch, "current weapon: hydra", 350, 350);
                hoseButton.sprite.draw(batch);
                if (bulletType == BulletType.WATER)
                    hydra.sprite.draw(batch);
                else if (bulletType == BulletType.SALT)
                    saltarm.sprite.draw(batch);
            } else if (weaponState == WeaponState.HOSE) {
                font.draw(batch, "current weapon: hose", 350, 350);
                hydraButton.watergunSprite.setPosition(hydraButton.getXPos(), hydraButton.getYPos());
                hydraButton.watergunSprite.draw(batch);
                if (bulletType == BulletType.WATER)
                    hose.sprite.draw(batch);
                else if (bulletType == BulletType.SALT)
                    saltarm.sprite.draw(batch);
            }
            batch.end();
        }

        if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
            batch.begin();
            if (gameState == GameState.GAMEOVER) {
                gameover.draw(batch);
                redoLevelButton.spriteNope.draw(batch);
            } else {
                win.draw(batch);
                redoLevelButton.sprite.draw(batch);
                if (redoLevelButton.isPressed()) {
                    redoLevelButton.spriteShade.draw(batch);
                }
            }
            backButtonGameEnd.spriteNope.draw(batch);
            shopButtonGameEnd.sprite.draw(batch);

            if (shopButtonGameEnd.isPressed()) {
                shopButtonGameEnd.spriteShade.draw(batch);
            }

            if(LevelSelect.currentLevelNumber <= 8 && gameState != GameState.GAMEOVER) {
                nextLevelButton.sprite.draw(batch);
            }
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