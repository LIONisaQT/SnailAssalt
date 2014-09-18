package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class SnailAssalt extends ApplicationAdapter {
    private int buttonstate = 0; //this may be able to be moved
    private SpriteBatch batch;

    public static OrthographicCamera camera; //currently locked to width and height, doesn't move
    private float width, height; //screen resolution
    private static Vector3 tap; //holds the position of tap location
    protected static float deltaTime;
    protected static float time = 0; //used with deltaTime

    protected static Preferences preferences; //saving3hard5me
    private FreeTypeFontGenerator fontGenerator; //handles .ttf --> .fnt
    protected static BitmapFont font;
    private MyInputProcessor inputProcessor; //handles touchUp and touchDown

    protected static Player jimmy, rachel;
    protected static int currency;

    //WEAPWNS
    protected static Weapon waterGun;
    protected static Hydra hydra;
    protected static Hose hose;
    protected static SaltArm saltarm;

    //SPRITES
    private Sprite
            /* credit pages */ credits, specialThanks, hurshalsface1,
            /* backgrounds */ gameover, win;

    //GAME STATES
    private static MainMenu mainMenu;
    private static Shop shop;
    private static Tutorial tutorial;
    private static LevelSelect levelSelect;
    private static SnailInfo snailInfo;
    private static CharacterSelect characterSelect;
    private static InGame inGame;
    private static GameOver gameOver;

    //BUTTONS
    private ShopButton shopButtonGameEnd;

    private RedoButton redoLevelButton;
    private NextLevelButton nextLevelButton;
    private BackButton backButtonGameEnd, backButtonCredits;

    //ENUMS AND STATES
    protected static enum GameState {
        CHARACTERSELECT, MAINMENU, TUTORIAL, INGAME, GAMEOVER, WIN, SHOP, LEVELSELECT, CREDITS, CREDITS_HURSHAL, INFO
    }

    protected static GameState gameState, prevGameState;

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

        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        tap = new Vector3(); //location of tap
        deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;

        jimmy = new Player();
        rachel = new Player();
        currency = preferences.getInteger("currency", 0);
        currency = 100000000; //temporarily set for debugging purposes

        //WEAPWNS
        waterGun = new Weapon();
        waterGun.enable = true;
        hose = new Hose();
        hydra = new Hydra();
        saltarm = new SaltArm();

        //FONT STUFF
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Chicken Butt.ttf")); //replace font with whatever
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (26 * (width / 1196)); //lin did the math and i guess it works
        font = fontGenerator.generateFont(parameter);

        //BACKGROUNDS
        gameover = new Sprite(new Texture("images/backgrounds/gameover.png"));
        gameover.setSize(width, height);
        win = new Sprite(new Texture("images/backgrounds/win.png"));
        win.setSize(width, height);

        //CREDITS
        credits = new Sprite(new Texture("images/backgrounds/credits.png"));
        credits.setSize(width / 1196 * credits.getWidth(), height / 720 * credits.getHeight());
        credits.setPosition(width / 2 - credits.getWidth() / 2, height / 2 - credits.getHeight() / 2);
        hurshalsface1 = new Sprite(new Texture("images/backgrounds/hurshalsFace.png"));
        hurshalsface1.setSize(width / 1196 * hurshalsface1.getWidth(), height / 720 * hurshalsface1.getHeight());
        hurshalsface1.setPosition(width - hurshalsface1.getWidth(), 0);
        specialThanks = new Sprite(new Texture("images/backgrounds/specialThanks.png"));
        specialThanks.setSize(width / 1196 * specialThanks.getWidth(), height / 720 * specialThanks.getHeight());
        specialThanks.setPosition(width / 2 - specialThanks.getWidth() / 2, height / 2 - 100);

        //GAME STATES
        mainMenu = new MainMenu();
        mainMenu.create();
        shop = new Shop();
        shop.create();
        tutorial = new Tutorial();
        tutorial.create();
        levelSelect = new LevelSelect();
        levelSelect.create();
        snailInfo = new SnailInfo();
        snailInfo.create();
        characterSelect = new CharacterSelect();
        characterSelect.create();
        inGame = new InGame();
        inGame.create();

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

        //BACK BUTTONS
        backButtonGameEnd = new BackButton(0, 0);
        backButtonCredits = new BackButton(width - backButtonGameEnd.sprite.getWidth(), height - shop.backButton.getHeight());

        //IN-GAME / GAME OVER / WIN BUTTONS
        nextLevelButton = new NextLevelButton(width - width / (6.39f), 0);
        redoLevelButton = new RedoButton(width / 2 - width / 6, height / 2 - width / 6);
        redoLevelButton.sprite.setSize(redoLevelButton.sprite.getWidth() + redoLevelButton.sprite.getWidth() / 8, mainMenu.startButton.getHeight());
        redoLevelButton.spriteNope.setSize(redoLevelButton.sprite.getWidth() + redoLevelButton.sprite.getWidth() / 8, mainMenu.startButton.getHeight());
        shopButtonGameEnd = new ShopButton(redoLevelButton.getXPos() + redoLevelButton.sprite.getWidth() + width / 36, redoLevelButton.getYPos());

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

        //SHOP BUTTONS
        shopButtonGameEnd.position.set(shopButtonGameEnd.getXPos(), shopButtonGameEnd.getYPos());

        //BACK BUTTONS
        backButtonGameEnd.position.set(backButtonGameEnd.getXPos(), backButtonGameEnd.getYPos());
        backButtonCredits.position.set(backButtonCredits.getXPos(), backButtonCredits.getYPos());

        //IN-GAME / GAME OVER / WIN
        redoLevelButton.position.set(redoLevelButton.getXPos(), redoLevelButton.getYPos());
        nextLevelButton.position.set(nextLevelButton.getXPos(), nextLevelButton.getYPos());
    }

    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(tap);
    }

    public void updateGame() {
        music.play(); //TODO: move this to specific game states

        //GAME STATES
        if (gameState == GameState.CHARACTERSELECT) {
            characterSelect.update();
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
            inGame.update();
        } else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
            preferences.putInteger("currency", currency);
            //preferences.flush();

            //DISPOSE
            for (GhostSnails shells : InGame.deadSnails) {
                shells.dispose();
            }
            for (ThrowyThingy waters : InGame.water) {
                waters.dispose();
            }
            for (Enemy enemy : LevelSelect.enemies) {
                enemy.dispose();
            }

            //CLEAR
            InGame.deadSnails.clear();
            InGame.water.clear();
            InGame.shakers.clear();

            //RESET
            House.hp = House.maxHP;
            Weapon.currentWater = Weapon.waterSupply;
            Weapon.saltSupply = Weapon.currentSalt;
            LevelSelect.currentLevel.enemyCount = 0;
            InGame.bulletType = InGame.BulletType.WATER;
            InGame.weaponState = InGame.WeaponState.REGWEAPON;

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
            if (nextLevelButton.isPressed() && LevelSelect.currentLevelNumber <= 8 && gameState != GameState.GAMEOVER) {
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

        font.setScale((float) ((width / 1196) * (1.4)));
        font.setColor(0, 0, 0, 1);
        if (gameState == GameState.CHARACTERSELECT) {
            characterSelect.draw();
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
            inGame.draw();
        } else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
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

            if (LevelSelect.currentLevelNumber <= 8 && gameState != GameState.GAMEOVER) {
                nextLevelButton.sprite.draw(batch);
            }
            batch.end();
        }
    }
}