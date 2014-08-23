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
    private float time = 0; //used with deltaTime

    protected static Preferences preferences; //saving3hard5me
    private FreeTypeFontGenerator fontGenerator; //handles .ttf --> .fnt
    private BitmapFont font;
    private MyInputProcessor inputProcessor; //handles touchUp and touchDown

    protected static int numberOfLevels = 10; //hard-coded number of levels
    protected int currentLevelNumber = 0; //holds current level's value
    protected static int numberOfTypes = 7; //hard-coded number of snail types

    private Player jimmy, rachel;
    protected static int currency;
    private House house;

    //SPRITES
    private Sprite
            /* credit pages */ credits, specialThanks, hurshalsface1,
            /* tutorial pages */ tutor1, tutor2, tutor3, tutor4, tutor5, tutor6, tutor7, tutor8, tutor9,
            /* backgrounds */ menu, gameover, win, levelSelect, shop, laun, info,
            /* shop stuff */ shopHose, shopHydra, shopSalt,
            /* UI */ hpBar, waterBar, saltBar, progBarSnail, sign;
    protected ArrayList<Sprite> tutorials;

    //WEAPONS
    private Weapon waterGun; //default watergun
    private Hydra hydra; //hydra
    private Hose hose; //hose
    private SaltArm saltarm; //salt
    private ArrayList<ThrowyThingy> water; //holds watergun shots
    private ArrayList<SaltProjectile> shakers; //holds salt shakers thrown
    protected boolean projectileHit;

    //BUTTONS
    private RachelButton rachelButton; //select rachel to play as
    private JimmyButton jimmyButton; //select jimmy to play as

    private StartButton startButtonMenu;
    private CreditsButton creditsButton;
    private ShopButton shopButtonMenu, shopButtonGameEnd;
    private InfoButton infoButton;
    private NextButton nextTutorial, nextInfoStandard, nextInfoAcid, nextInfoFlying, nextInfoHealing, nextInfoBoss, nextInfoMother;
    private QuitButton quitButton;
    private RedoButton redoLevelButton;
    private NextLevelButton nextLevelButton;
    private TutorialButton tutorialButton;
    private PreviousButton previousButtonTutorial;
    private BackButton backButtonShop, backButtonGameEnd, backButtonLevelSelect, backButtonCredits, backButtonTutorial, backButtonInfoSelection,
            /* snail info back buttons */ backButtonInfoStandard, backButtonInfoAcid, backButtonInfoFlying, backButtonInfoHealing, backButtonInfoBoss, backButtonInfoMother,backButtonInfoPerson;

    //WEAPON BUTTONS
    protected static HydraButton hydraButton;
    protected static HoseButton hoseButton;
    protected static SaltButton saltButton;

    //SNAIL INFO
    private ArrayList<SnailInfoButtons> snailInfoButtons;

    //SHOP BUTTONS
    private ShopHydraButton shopHydraButton;
    private ShopSaltButton shopSaltButton;
    private ShopHoseButton shopHoseButton;

    //LEVEL-RELATED
    public  static  ArrayList<Enemy> enemies; //temporarily holds level's enemy arraylist
    private ArrayList<LevelButton> levelButtons; //holds all level buttons
    private ArrayList<Level> levels; //holds all levels
    private Level currentLevel; //temporarily holds current level

    //ENEMY-RELATED
    private ArrayList<Droppings> droppings; //acid snail droppings
    private ArrayList<BombDrop> bombs; //flying snail droppings
    private ArrayList<GhostSnails> deadSnails; //holds dead snails that go to heaven

    //ENUMS
    protected static enum GameState {CHARACTERSELECT, MAINMENU, TUTORIAL, INGAME, GAMEOVER, WIN, SHOP, LEVELSELECT, CREDITS, CREDITS_HURSHAL, INFO}
    protected static GameState gameState, prevGameState;
    protected static enum TutorialState {PAGE1, PAGE2, PAGE3, PAGE4, PAGE5, PAGE6, PAGE7, PAGE8, PAGE9}
    protected static TutorialState tutorialState;
    protected static enum InfoState {SELECTION, STANDARD, ACID, FLYING, HEALING, BOSS, MOTHER, PERSON}
    protected static InfoState infoState;
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
        menu = new Sprite(new Texture("images/backgrounds/sidewaysmenu.png"));
        menu.setSize(width, height);
        levelSelect = new Sprite(new Texture("images/backgrounds/levelscreen.png"));
        levelSelect.setSize(width, height);
        shop = new Sprite(new Texture("images/backgrounds/levelscreen.png"));
        shop.setSize(width, height);
        gameover = new Sprite(new Texture("images/backgrounds/gameover.png"));
        gameover.setSize(width, height);
        win = new Sprite(new Texture("images/backgrounds/win.png"));
        win.setSize(width, height);
        laun = new Sprite(new Texture("images/backgrounds/lawn.jpeg"));
        laun.setSize(width, height);
        info = new Sprite(new Texture("images/backgrounds/info.png"));
        info.setSize(width, height);
        sign = new Sprite( new Texture("images/backgrounds/sign.png"));
        sign.setSize(width / 2, height);
        sign.setPosition(width / 2 , 0);

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

        //TUTORIALS
        tutorials = new ArrayList<Sprite>();
        tutor1 = new Sprite(new Texture("images/tutorials/tutorial1.jpeg"));
        tutorials.add(tutor1);
        tutor2 = new Sprite(new Texture("images/tutorials/tutorial2.jpeg"));
        tutorials.add(tutor2);
        tutor3 = new Sprite(new Texture("images/tutorials/tutorial3.jpeg"));
        tutorials.add(tutor3);
        tutor4 = new Sprite(new Texture("images/tutorials/tutorial4.jpeg"));
        tutorials.add(tutor4);
        tutor5 = new Sprite(new Texture("images/tutorials/tutorial5.jpeg"));
        tutorials.add(tutor5);
        tutor6 = new Sprite(new Texture("images/tutorials/tutorial6.jpeg"));
        tutorials.add(tutor6);
        tutor7 = new Sprite(new Texture("images/tutorials/tutorial7.jpeg"));
        tutorials.add(tutor7);
        tutor8 = new Sprite(new Texture("images/tutorials/tutorial8.jpeg"));
        tutorials.add(tutor8);
        tutor9 = new Sprite(new Texture("images/tutorials/tutorial9.jpeg"));
        tutorials.add(tutor9);
        for (Sprite tutor : tutorials) {
            tutor.setSize(width, height);
            tutor.setPosition(0, 0);
        }

        //SNAIL INFO
        snailInfoButtons = new ArrayList<SnailInfoButtons>();

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
        enemies = new ArrayList<Enemy>();
        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        deadSnails = new ArrayList<GhostSnails>();

        //BACK BUTTONS
        backButtonGameEnd = new BackButton(0, 0);
        backButtonShop = new BackButton(width - backButtonGameEnd.sprite.getWidth(), 10);
        backButtonLevelSelect = new BackButton(width - backButtonGameEnd.sprite.getWidth(), 10);
        backButtonTutorial = new BackButton(width - backButtonGameEnd.sprite.getWidth(), height - backButtonShop.buttonGetHeight());
        backButtonCredits = new BackButton(width - backButtonGameEnd.sprite.getWidth(), height - backButtonShop.buttonGetHeight());

        //START MENU BUTTONS
        startButtonMenu = new StartButton(width / 2 - width / (4.38f), height / 2 - height / (4.5f));
        infoButton = new InfoButton(startButtonMenu.getXPos(), startButtonMenu.getYPos() - startButtonMenu.buttonGetHeight() - 10);
        creditsButton = new CreditsButton(infoButton.getXPos() + infoButton.sprite.getWidth() + 20, infoButton.getYPos());
        tutorialButton = new TutorialButton(creditsButton.getXPos() + creditsButton.sprite.getWidth() + 20, creditsButton.getYPos());
        shopButtonMenu = new ShopButton(startButtonMenu.getXPos() + startButtonMenu.sprite.getWidth() + 10, startButtonMenu.getYPos());
        nextTutorial = new NextButton(width - backButtonGameEnd.sprite.getWidth(), height - backButtonShop.buttonGetHeight());
        previousButtonTutorial = new PreviousButton(20, height - backButtonShop.buttonGetHeight());

        //JIMMY AND RACHEL BUTTONS
        rachelButton = new RachelButton(width / 2 + 100 , 50 );
        rachelButton.bound.set(rachelButton.getXPos(), rachelButton.getYPos(), width / 2 - width / 8, height - height / 8);
        jimmyButton = new JimmyButton(100, 50);
        jimmyButton.bound.set(jimmyButton.getXPos(), jimmyButton.getYPos(), width / 2 - width / 8, height - height / 8);

        //IN-GAME / GAME OVER / WIN BUTTONS
        QuitButton tempQuitButton = new QuitButton(0, 0); //temporary quit button to get the sprite's dimensions
        quitButton = new QuitButton(0, height - tempQuitButton.sprite.getHeight());
        nextLevelButton = new NextLevelButton(width - width / (6.39f), 0);
        redoLevelButton = new RedoButton(width / 2 - width / 6, height / 2 - width / 6);
        redoLevelButton.sprite.setSize(redoLevelButton.sprite.getWidth() + redoLevelButton.sprite.getWidth() / 8 , startButtonMenu.buttonGetHeight());
        redoLevelButton.spriteNope.setSize(redoLevelButton.sprite.getWidth()+ redoLevelButton.sprite.getWidth() / 8 , startButtonMenu.buttonGetHeight());
        shopButtonGameEnd = new ShopButton(redoLevelButton.getXPos() + redoLevelButton.sprite.getWidth() + width / 36, redoLevelButton.getYPos());

        //SNAIL INFO BUTTONS
        backButtonInfoSelection = new BackButton(0, height - nextTutorial.sprite.getHeight());
        backButtonInfoStandard = new BackButton (0, height - nextTutorial.sprite.getHeight());
        nextInfoStandard = new NextButton(width - backButtonGameEnd.sprite.getWidth(),height - nextTutorial.sprite.getHeight());
        backButtonInfoAcid = new BackButton(0, height - nextTutorial.sprite.getHeight());
        nextInfoAcid = new NextButton(width - backButtonGameEnd.sprite.getWidth(), height - nextTutorial.sprite.getHeight());
        backButtonInfoFlying = new BackButton(0, height - nextTutorial.sprite.getHeight());
        nextInfoFlying = new NextButton(width - backButtonGameEnd.sprite.getWidth(), height - nextTutorial.sprite.getHeight());
        backButtonInfoHealing = new BackButton(0, height - nextTutorial.sprite.getHeight());
        nextInfoHealing = new NextButton(width - backButtonGameEnd.sprite.getWidth(), height - nextTutorial.sprite.getHeight());
        backButtonInfoBoss = new BackButton (0, height - nextTutorial.sprite.getHeight());
        nextInfoBoss = new NextButton(width - backButtonGameEnd.sprite.getWidth(), height - nextTutorial.sprite.getHeight());
        backButtonInfoMother = new BackButton(0, height - nextTutorial.sprite.getHeight());
        nextInfoMother = new NextButton(width - backButtonGameEnd.sprite.getWidth(), height - nextTutorial.sprite.getHeight());
        backButtonInfoPerson = new BackButton(0, height - nextTutorial.sprite.getHeight());

        //SHOP BUTTONS
        Sprite tempShopButton = new Sprite(new Texture("images/buttons/hoseIcon.png"));
        tempShopButton.setSize(width / 1196 * tempShopButton.getWidth(), height / 720 * tempShopButton.getHeight());

        //SHOP: BUY HYDRA BUTTON
        shopHydra = new Sprite(new Texture("images/buttons/weaponIcon.png"));
        shopHydraButton = new ShopHydraButton(width / 12 , height / 2);
        shopHydra.setSize(width / 1196 * shopHydra.getWidth(), height / 720 * shopHydra.getHeight());
        shopHydra.setPosition(shopHydraButton.getXPos() + (shopHydraButton.buttonGetWidth() - shopHydra.getWidth()) / 2, shopHydraButton.getYPos() + shopHydraButton.buttonGetHeight() + 10);

        //SHOP: BUY HOSE BUTTON
        shopHose = new Sprite(new Texture("images/buttons/hoseIcon.png"));
        shopHoseButton = new ShopHoseButton(shopHydraButton.getXPos() + tempShopButton.getWidth() + 20 , height / 2);
        shopHose.setSize(width / 1196 * shopHose.getWidth(), height / 720 * shopHose.getHeight());
        shopHose.setPosition(shopHoseButton.getXPos() + (shopHydraButton.buttonGetWidth() - shopHydra.getWidth()) / 2, shopHoseButton.getYPos() + shopHydraButton.buttonGetHeight() + 10);

        //SHOP: BUY SALT BUTTON
        shopSaltButton = new ShopSaltButton(shopHoseButton.getXPos() + tempShopButton.getWidth() + 20, height / 2);
        shopSalt = new Sprite(new Texture("images/buttons/saltIcon.png"));
        shopSalt.setSize(width / 1196 * shopSalt.getWidth(), height / 720 * shopSalt.getHeight());
        shopSalt.setPosition(shopSaltButton.getXPos() + (shopHydraButton.buttonGetWidth() - shopHydra.getWidth()) / 2, shopSaltButton.getYPos() + shopHydraButton.buttonGetHeight() + 10);

        //IN-GAME WEAPON BUTTONS
        SaltButton tempSaltButton = new SaltButton(0, 0);
        saltButton = new SaltButton(width - tempSaltButton.sprite.getWidth(), height/5);
        HydraButton tempHydraButton = new HydraButton(0, 0);
        hydraButton = new HydraButton(width - tempHydraButton.sprite.getWidth(), (4*height)/(5));
        HoseButton tempHoseButton = new HoseButton(0, 0);
        hoseButton = new HoseButton(width - tempHoseButton.sprite.getWidth(), hydraButton.getYPos());

        //LEVELS
        levelButtons = new ArrayList<LevelButton>();
        levels = new ArrayList<Level>();
        for (int a = 0; a < numberOfLevels; a++) {
            if (a < 5)
                levelButtons.add(new LevelButton(a * 210, 200)); //first row
            else
                levelButtons.add(new LevelButton((a - 5) * 210, 0)); //second row
            levels.add(new Level(a + 1));
        }
        currentLevel = new Level(0);

        //SNAIL INFO
        for (int b = 0; b < numberOfTypes; b++) {
            if (b < 5)
                snailInfoButtons.add(new SnailInfoButtons(b * 210, 200)); //first row
            else
                snailInfoButtons.add(new SnailInfoButtons((b - 5) * 210, 0)); //second row
        }

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
        tutorialState = TutorialState.PAGE1; //set tutorial page
        infoState = InfoState.SELECTION; //set snail info page
        prevGameState = null; //set previous game state
        weaponState = WeaponState.REGWEAPON; //set first weapon
        bulletType = BulletType.WATER; //set first bullet type

        House.hp = House.maxHP; //set current hp to max hp
        Weapon.currentWater = Weapon.waterSupply; //set current water to max water
        Weapon.saltSupply = Weapon.currentSalt; //sets max salt to salt bought; dynamically changes depending on how much used in a level

        //MAIN MENU BUTTONS
        startButtonMenu.position.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.position.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        creditsButton.position.set(creditsButton.getXPos(), creditsButton.getYPos());

        //LEVEL SELECT
        for (int a = 0; a < numberOfLevels; a++)
            levelButtons.get(a).position.set(levelButtons.get(a).getXPos(), levelButtons.get(a).getYPos());

        //WEAPON BUTTONS
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        saltButton.position.set(saltButton.getXPos(), saltButton.getYPos());
        hoseButton.position.set(hoseButton.getXPos(), hoseButton.getYPos());

        //JIMMY AND RACHEL BUTTONS
        rachelButton.position.set(rachelButton.getXPos(), rachelButton.getYPos());
        jimmyButton.position.set(jimmyButton.getXPos(), jimmyButton.getYPos());

        //SHOP BUTTONS
        shopHydraButton.position.set(shopHydraButton.getXPos(), shopHydraButton.getYPos());
        shopSaltButton.position.set(shopSaltButton.getXPos(), shopSaltButton.getYPos());
        shopHoseButton.position.set(shopHoseButton.getXPos(), shopHoseButton.getYPos());
        shopButtonGameEnd.position.set(shopButtonGameEnd.getXPos(), shopButtonGameEnd.getYPos());

        //BACK BUTTONS
        backButtonGameEnd.position.set(backButtonGameEnd.getXPos(), backButtonGameEnd.getYPos());
        backButtonLevelSelect.position.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonCredits.position.set(backButtonCredits.getXPos(), backButtonCredits.getYPos());
        backButtonShop.position.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        backButtonTutorial.position.set(backButtonTutorial.getXPos(), backButtonTutorial.getYPos());

        //SNAIL INFO BUTTONS
        for (int b = 0; b < numberOfTypes; b++)
            snailInfoButtons.get(b).position.set(snailInfoButtons.get(b).getXPos(), snailInfoButtons.get(b).getYPos());
        backButtonInfoSelection.position.set(backButtonInfoSelection.getXPos(), backButtonInfoSelection.getYPos());
        backButtonInfoStandard.position.set(backButtonInfoStandard.getXPos(), backButtonInfoStandard.getYPos());
        nextInfoStandard.position.set(nextInfoStandard.getXPos(),nextInfoStandard.getYPos());
        backButtonInfoAcid.position.set(backButtonInfoAcid.getXPos(), backButtonInfoAcid.getYPos());
        nextInfoAcid.position.set(nextInfoAcid.getXPos(),nextInfoAcid.getYPos());
        backButtonInfoFlying.position.set(backButtonInfoFlying.getXPos(), backButtonInfoFlying.getYPos());
        nextInfoFlying.position.set(nextInfoFlying.getXPos(),nextInfoFlying.getYPos());
        backButtonInfoHealing.position.set(backButtonInfoHealing.getXPos(), backButtonInfoHealing.getYPos());
        nextInfoHealing.position.set(nextInfoHealing.getXPos(),nextInfoHealing.getYPos());
        backButtonInfoBoss.position.set(backButtonInfoBoss.getXPos(), backButtonInfoBoss.getYPos());
        nextInfoBoss.position.set(nextInfoBoss.getXPos(),nextInfoBoss.getYPos());
        backButtonInfoMother.position.set(backButtonInfoMother.getXPos(), backButtonInfoMother.getYPos());
        nextInfoMother.position.set(nextInfoMother.getXPos(),nextInfoMother.getYPos());
        backButtonInfoPerson.position.set(backButtonInfoPerson.getXPos(), backButtonInfoPerson.getYPos());

        //TUTORIAL BUTTONS
        previousButtonTutorial.position.set(previousButtonTutorial.getXPos(), previousButtonTutorial.getYPos());
        nextTutorial.position.set(nextTutorial.getXPos(), nextTutorial.getYPos());

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
            if (rachelButton.touchup() &&buttonstate == 1) {
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
            if (startButtonMenu.touchup()) {
                startButtonMenu.pressedAction();
            }
            if (shopButtonMenu.touchup()) {
                gameState = GameState.SHOP;
            }
            if (creditsButton.touchup()) {
                creditsButton.pressedAction();
            }
            if (tutorialButton.touchup()) {
                tutorialButton.pressedAction();
            }
            if (infoButton.touchup()) {
                infoButton.pressedAction();
            }
        } else if (gameState == GameState.SHOP) {

            if (backButtonShop.touchup()) {
                gameState = GameState.MAINMENU;
            }

            //DOUGLAS'S TRIAL STUFF -- LEAVE UNTIL FURTHER NOTICE
            /*if (backButtonShop.pressable() && backButtonShop.isPressed() && backButtonShop.status == false) {
                backButtonShop.status = true;

            }
            if (!backButtonShop.isPressed() && backButtonShop.status == true) {
                gameState = gameState.MAINMENU;
                backButtonShop.status = false;
            }*/

            //BUY HYDRA
            if (currency > shopHydraButton.price && shopHydraButton.isPressed()) {
                shopHydraButton.ownd = true;
                currency -= shopHydraButton.price;
                preferences.putInteger("hydra", 1);
                //preferences.flush();
            }
            if (preferences.getInteger("hydra", 0) == 1)
                hydra.enable = true;

            //BUY HOSE
            if (currency >= shopHoseButton.price && shopHoseButton.isPressed() && !shopHoseButton.ownd) {
                shopHoseButton.ownd = true;
                currency -= shopHoseButton.price;
                preferences.putInteger("hose", 1);
                //preferences.flush();
            }
            if (preferences.getInteger("hose", 0) == 1)
                hose.enable = true;

            //BUY SALT
            if (currency > shopSaltButton.price && shopSaltButton.isPressed()) {
                currency -= shopSaltButton.price;
                Weapon.saltSupply++;
                Weapon.currentSalt = Weapon.saltSupply;
                preferences.putInteger("saltsupply", (int) Weapon.saltSupply);
                //preferences.flush();
            }
            if (preferences.getInteger("salt", 0) == 1) {
                waterGun.enableSalt = true;
                hydra.enableSalt = true;
                hose.enableSalt = true;
            }
        } else if (gameState == GameState.TUTORIAL) {
            if (tutorialState == TutorialState.PAGE9) {
                if (backButtonTutorial.isPressed()){
                    buttonstate = 1;
                }
                if (backButtonTutorial.touchup() && buttonstate == 1 ) {
                    backButtonTutorial.pressedAction();
                }
            }
            if (nextTutorial.isPressed()) {
                buttonstate = 1;
            }
            if (nextTutorial.touchup() && buttonstate == 1) {
                nextTutorial.pressedAction();
                buttonstate = 0;
            }
            if (previousButtonTutorial.isPressed()) {
                buttonstate = 1;
            }
            if (previousButtonTutorial.touchup() && buttonstate == 1) {
                previousButtonTutorial.pressedAction();
                buttonstate = 0;
            }
        } else if (gameState == GameState.LEVELSELECT) {
            for (int a = 0; a < numberOfLevels; a++) {
                if (levelButtons.get(a).isPressed()) {
                    currentLevelNumber = a;
                    currentLevel = levels.get(currentLevelNumber); //current level is now whatever level that was pressed
                    enemies = currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
                    currentLevel.enemyCount = 0;
                    levelButtons.get(a).pressedAction(); //go in-game
                }
            }
            if (backButtonLevelSelect.isPressed()) {
                buttonstate = 1;
            }
            if (backButtonLevelSelect.touchup() && buttonstate == 1) {
                gameState = GameState.MAINMENU;
                buttonstate = 0;
            }
        } else if (gameState == GameState.INFO) {
            if (backButtonInfoSelection.isPressed()) {
                backButtonInfoSelection.pressedAction();
            }
            else if (backButtonInfoStandard.isPressed()) {
                backButtonInfoStandard.pressedAction();
            }
            else if (backButtonInfoAcid.isPressed()) {
                backButtonInfoAcid.pressedAction();
            }
            else if (backButtonInfoFlying.isPressed()) {
                backButtonInfoFlying.pressedAction();
            }
            else if (backButtonInfoHealing.isPressed()) {
                backButtonInfoHealing.pressedAction();
            }
            else if (backButtonInfoBoss.isPressed()) {
                backButtonInfoBoss.pressedAction();
            }
            else if (backButtonInfoMother.isPressed()) {
                backButtonInfoMother.pressedAction();
            }
            else if (backButtonInfoPerson.isPressed()) {
                backButtonInfoPerson.pressedAction();
            }
            if(nextInfoStandard.isPressed()){
                nextInfoStandard.pressedAction();
            }
            else if(nextInfoAcid.isPressed()){
                nextInfoAcid.pressedAction();
            }
            else if(nextInfoFlying.isPressed()){
                nextInfoFlying.pressedAction();
            }
            else if(nextInfoHealing.isPressed()){
                nextInfoHealing.pressedAction();
            }
            else if(nextInfoBoss.isPressed()){
                nextInfoBoss.pressedAction();
            }
            else if(nextInfoMother.isPressed()){
                nextInfoMother.pressedAction();
            }

            for (int b = 0; b < numberOfTypes; b++) {
                if (snailInfoButtons.get(b).isPressed() && infoState == InfoState.SELECTION) {
                    snailInfoButtons.get(b).pressedAction();
                    if (b == 0) {
                        infoState = InfoState.STANDARD;
                        enemies.clear();
                        enemies.add(new Enemy(width / 11, height / 5, 0, 0, 0, 0));
                    } else if (b == 1) {
                        infoState = InfoState.ACID;
                        enemies.clear();
                        enemies.add(new AcidSnail(width / 11, height / 5, 0, 0, 0, 0));
                    } else if (b == 2) {
                        infoState = InfoState.FLYING;
                        enemies.clear();
                        for (int a = 0; a < 1; a++)
                            enemies.add(new FlyingSnail(width / 11, height / 5, 0, 0, 0, 0));
                    } else if (b == 3) {
                        infoState = InfoState.HEALING;
                        enemies.clear();
                        for (int a = 0; a < 1; a++)
                            enemies.add(new HealerSnail(width / 11, height / 5, 0, 0, 0, 0));
                    } else if (b == 4) {
                        infoState = InfoState.BOSS;
                        enemies.clear();
                        for (int a = 0; a < 1; a++)
                            enemies.add(new BossSnail(width / 11, height / 5, 0, 0, 0, 0));
                    } else if (b == 5) {
                        infoState = InfoState.MOTHER;
                        enemies.clear();
                        for (int a = 0; a < 1; a++)
                            enemies.add(new MotherSnail(width / 11, height / 5, 0, 0, 0, 0));
                    } else if (b == 6) {
                        infoState = InfoState.PERSON;
                        enemies.clear();
                        for (int a = 0; a < 1; a++)
                            enemies.add(new Zombie(width / 11, height / 5, 0, 0, 0, 0));
                    }
                }
            }
        } else if (gameState == GameState.CREDITS) {
            if (Gdx.input.justTouched())
                gameState = GameState.CREDITS_HURSHAL;
        } else if (gameState == GameState.CREDITS_HURSHAL) {
            if (backButtonCredits.touchup()) {
                backButtonCredits.pressedAction();
            }
        } else if (gameState == GameState.INGAME) {
            progBarSnail.setPosition(((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width - (progBarSnail.getWidth() / 2), 0);
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
                for (int a = 0; a < enemies.size(); a++) {
                    if (proj.bound.overlaps(enemies.get(a).bound)) {
                        projectileHit = true;
                        enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                        enemies.get(a).startFlash(.1f);
                        if (enemies.get(a).hp <= 0) {
                            deadSnails.add(new GhostSnails((int) enemies.get(a).bound.x, (int) enemies.get(a).bound.y));
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
                for (int a = 0; a < enemies.size(); a++) {
                    if (bullet.bound.overlaps(enemies.get(a).bound)) {
                        projectileHit = true;
                        enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                        enemies.get(a).startFlash(.1f);
                        if (enemies.get(a).hp <= 0) {
                            deadSnails.add(new GhostSnails((int) enemies.get(a).bound.x, (int) enemies.get(a).bound.y));
                            enemies.get(a).dispose();
                            enemies.remove(a);
                            currentLevel.enemyCount++;
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
            for (Enemy enemy : enemies) {
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
            progBarSnail.setPosition(((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width - progBarSnail.getWidth() / 2, -progBarSnail.getHeight() / 2);

            //LOSE / VICTORY CONDITIONS
            if (House.hp <= 0 || quitButton.isPressed())
                gameState = GameState.GAMEOVER;
            if (enemies.size() == 0)
                gameState = GameState.WIN;
        } else if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) {
            preferences.putInteger("currency", currency);
            //preferences.flush();

            //DISPOSE
            for (GhostSnails shells : deadSnails) {shells.dispose();}
            for (ThrowyThingy waters : water) {waters.dispose();}
            for (Droppings derpies : droppings) {derpies.dispose();}
            for (Enemy enemy : enemies) {enemy.dispose();}
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
            currentLevel.enemyCount = 0;
            bulletType = BulletType.WATER;
            weaponState = WeaponState.REGWEAPON;

            //BUTTONS
            if (backButtonGameEnd.isPressed()) {
                buttonstate = 1;
                System.out.println(buttonstate);
            }
            if (backButtonGameEnd.touchup() && buttonstate == 1) {
                backButtonGameEnd.pressedAction();
                buttonstate = 0;
                System.out.println(buttonstate);
            }
            if (shopButtonGameEnd.touchup()) { //go to shop
                if (gameState == GameState.GAMEOVER)
                    prevGameState = GameState.GAMEOVER;
                else
                    prevGameState = GameState.WIN;
                shopButtonGameEnd.pressedAction();
            }
            if (redoLevelButton.touchup()) {
                enemies = currentLevel.getEnemies(); //reloads level's enemies
                redoLevelButton.pressedAction(); //go to in-game
            }
            if (nextLevelButton.isPressed()&& currentLevelNumber<=8 && gameState != GameState.GAMEOVER){
                enemies = currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
                currentLevelNumber++;
                currentLevel = levels.get(currentLevelNumber);
                currentLevel.enemyCount = 0;
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
            levelSelect.draw(batch);
            batch.draw(jimmyButton.sprite, jimmyButton.bound.x, jimmyButton.bound.y, width / 2 - width / 8, height - height / 8);
            batch.draw(rachelButton.sprite, rachelButton.bound.x, rachelButton.bound.y, width / 2 - width / 8, height - height / 8);
            font.draw(batch, "Would you like to play as:", width / 6, height - 10);
            font.draw(batch, "Jimmy", jimmyButton.getXPos() + 20, jimmyButton.getYPos());
            font.draw(batch, "Rachel", rachelButton.getXPos() + 20, rachelButton.getYPos());
            batch.end();
        } else if (gameState == GameState.MAINMENU) {
            batch.begin();
            menu.draw(batch);
            startButtonMenu.sprite.draw(batch);
            creditsButton.sprite.draw(batch);
            shopButtonMenu.sprite.draw(batch);
            infoButton.sprite.draw(batch);
            if (startButtonMenu.isPressed()) {
                startButtonMenu.spriteShade.draw(batch);
            }
            if (preferences.getInteger("tutorial", 0) == 2) {
                tutorialButton.sprite.draw(batch);
            }
            if (tutorialButton.isPressed()) {
                tutorialButton.spriteShade.draw(batch);
            }
            if (creditsButton.isPressed()) {
                creditsButton.spriteShade.draw(batch);
            }
            if (shopButtonMenu.isPressed()) {
                shopButtonMenu.spriteShade.draw(batch);
            }
            batch.end();
        } else if (gameState == GameState.CREDITS) {
            batch.begin();
            levelSelect.draw(batch);
            credits.draw(batch);
            batch.end();
        } else if (gameState == GameState.CREDITS_HURSHAL) {
            batch.begin();
            levelSelect.draw(batch);
            hurshalsface1.draw(batch);
            specialThanks.draw(batch);
            backButtonCredits.sprite.draw(batch);
            if (backButtonCredits.isPressed()) {
                backButtonCredits.spriteShade.draw(batch);
            }
            batch.end();
        } else if (gameState == GameState.LEVELSELECT) {
            batch.begin();
            levelSelect.draw(batch);
            for (int a = 0; a < numberOfLevels; a++) {
                LevelButton lb = levelButtons.get(a);
                batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
            }
            backButtonLevelSelect.sprite.draw(batch);
            if (backButtonLevelSelect.isPressed()) {
                backButtonLevelSelect.spriteShade.draw(batch);

            }
            batch.end();
        } else if (gameState == GameState.SHOP) {
            batch.begin();
            shop.draw(batch);
            shopHydraButton.sprite.draw(batch);
            shopSaltButton.sprite.draw(batch);
            shopHoseButton.sprite.draw(batch);
            if (shopHoseButton.ownd) {
                shopHoseButton.spriteNope.draw(batch);
            }
            if (shopHydraButton.ownd) {
                shopHydraButton.spriteNope.draw(batch);
            }

            //ITEM SPRITES
            shopSalt.draw(batch);
            shopHydra.draw(batch);
            shopHose.draw(batch);

            //PRICE TAGS
            font.draw(batch, "$" + shopHoseButton.price, shopHydraButton.getXPos(), shopHydraButton.getYPos() - 10);
            font.draw(batch, "$" + shopHoseButton.price, shopHoseButton.getXPos(), shopHoseButton.getYPos() - 10);
            font.draw(batch, "$" + shopSaltButton.price, shopSaltButton.getXPos(), shopSaltButton.getYPos() - 10);

            //POOR EXCUSE FOR INVENTORY
            font.draw(batch, " salts: " + (int) Weapon.saltSupply, shopSaltButton.getXPos() + shopSaltButton.sprite.getWidth(), shopSaltButton.getYPos() + shopSaltButton.buttonGetHeight());
            font.draw(batch, "shells: " + currency, 10, height);
            backButtonShop.sprite.draw(batch);
            if (backButtonShop.isPressed()) {
                backButtonShop.spriteShade.draw(batch);
            }
            batch.end();
        } else if (gameState == GameState.TUTORIAL) {
            batch.begin();
            if (tutorialState == TutorialState.PAGE1) {
                tutor1.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE2) {
                tutor2.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE3) {
                tutor3.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE4) {
                tutor4.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE5) {
                tutor5.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE6) {
                tutor6.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE7) {
                tutor7.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE8) {
                tutor8.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if(previousButtonTutorial.isPressed()){
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            } else if (tutorialState == TutorialState.PAGE9) {
                tutor9.draw(batch);
                nextTutorial.sprite.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
                previousButtonTutorial.sprite.draw(batch);
                if (previousButtonTutorial.isPressed()) {
                    previousButtonTutorial.spriteShade.draw(batch);
                }
            }
            batch.end();
        } else if (gameState == GameState.INFO) {
            batch.begin();
            font.setScale((float) ((width / 1196) * (1.4)));
            if (infoState == InfoState.SELECTION) {
                info.draw(batch);
                for (int b = 0; b < numberOfTypes; b++) {
                    SnailInfoButtons lb = snailInfoButtons.get(b);
                    batch.draw(lb.getButtonImage(b + 1), lb.bound.x, lb.bound.y);
                }
                backButtonInfoPerson.sprite.draw(batch);
            } else if (infoState == InfoState.STANDARD) {
                info.draw(batch);
                for (Enemy enemy : enemies) {
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:very weak", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "STANDARD SNAIL", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
                nextInfoAcid.sprite.draw(batch);
            } else if (infoState == InfoState.ACID) {
                info.draw(batch);
                for (Enemy enemy : enemies) {
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.setScale((float) ((width / 1196) * (1.4)));
                font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:weak", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-'speed up' slimes ", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "ACID SNAIL", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
                nextInfoAcid.sprite.draw(batch);

            } else if (infoState == InfoState.FLYING) {
                info.draw(batch);
                for (Enemy enemy : enemies) {
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.setScale((float) ((width / 1196) * (1.4)));
                font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-'speed up' slime bombs", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "FLYING SNAIL", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
                nextInfoAcid.sprite.draw(batch);

            } else if (infoState == InfoState.HEALING) {
                info.draw(batch);
                for (Enemy enemy : enemies) {
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.setScale((float) ((width / 1196) * (1.4)));
                font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "HEALING SNAIL", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
                nextInfoAcid.sprite.draw(batch);

            } else if (infoState == InfoState.BOSS) {
                info.draw(batch);
                for (Enemy enemy : enemies) {
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.setScale((float) ((width / 1196) * (1.4)));
                font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:very strong", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-high HP", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "KING SNAILEY", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
                nextInfoAcid.sprite.draw(batch);

            } else if (infoState == InfoState.MOTHER) {
                info.draw(batch);
                for (Enemy enemy : enemies) {
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.setScale((float) ((width / 1196) * (1.4)));
                font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:weak", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-high HP", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "MOTHER SNAIL", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
                nextInfoAcid.sprite.draw(batch);
            } else if (infoState == InfoState.PERSON) {
                info.draw(batch);
                for (Enemy enemy : enemies) {//draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.setScale((float) ((width / 1196) * (1.4)));
                font.draw(batch, "-Speed:very fast", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (font.getLineHeight()));
                font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.setScale((float) ((width / 1196) * (2.1)));
                font.draw(batch, "ZOMBIE", width / 9, (2 * font.getLineHeight()));
                backButtonInfoPerson.sprite.draw(batch);
            }
            font.setScale((float) ((width / 1196) * (1.4)));
            batch.end();
        } else if (gameState == GameState.INGAME) { //in-game
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
            for (Enemy enemy : enemies)
                enemy.draw(batch, time);
            for (GhostSnails snailshell : deadSnails)
                snailshell.sprite.draw(batch);

            //DEBUG MESSAGES (REMOVE WHEN GAME IS FINISHED)
            font.draw(batch, "number of dead snails: " + deadSnails.size(), 200, 200);
            font.draw(batch, "hydra: salt enabled? -- " + hydra.enableSalt, 300, 400);
            font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);
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
            shapeRenderer.rect(0, 0, ((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width, 40);

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

            if(currentLevelNumber <= 8 && gameState != GameState.GAMEOVER) {
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
