package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import com.badlogic.gdx.InputProcessor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;


public class SnailAssalt extends ApplicationAdapter {
    private int buttonstate = 0;
    ///trial stuff this is all extra shit
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public static OrthographicCamera camera;
    protected static int numberOfLevels = 10;
    protected int currentLevelNumber = 0;
    protected static int currency;
    protected static int numberOfTypes = 7;
    private int width, height;
    private static Vector3 tap;
    private FreeTypeFontGenerator fontGenerator;
    private BitmapFont font;
    private Player jimmy, rachel;
    private House house;
    private float time = 0;
    protected static Preferences preferences;
    private Sprite
            /* credit pages */ credits, specialThanks, hurshalsface1,
            /* tutorial pages */ tutor1, tutor2, tutor3, tutor4, tutor5, tutor6, tutor7, tutor8, tutor9,
            /* backgrounds */ menu, gameover, win, levelSelect, shop, laun, info,
            /* shop stuff */ spHose, spHydra, spSalt,
            /* UI */ hpBar, waterBar, saltBar, progBarSnail, sign;
    protected boolean projectileHit;
    //UI end
    //weapwns start
    private Weapon waterGun;
    private Hydra hydra;
    private Hose hose;
    private SaltArm saltarm;
    private ArrayList<Droppings> droppings;
    private ArrayList<BombDrop> bombs;
    private ArrayList<ThrowyThingy> water; //holds watergun shots
    private ArrayList<Salt> shakers;
    private ArrayList<Snailshell> shell;
    //weapwns end
    //buttons start
    private StartButton startButtonMenu;
    private CreditsButton creditsButton;
    private ShopButton shopButtonMenu, shopButtonGameEnd;
    private NextLevelButton nextLevelButton;
    private BackButton backButtonShop, backButtonGameEnd, backButtonLevelSelect, backButtonCredits, backButtonTutorial, backButtonInfoSelection, backButtonInfoStandard, backButtonInfoAcid, backButtonInfoFlying, backButtonInfoHealing, backButtonInfoBoss, backButtonInfoMother,backButtonInfoPerson; //different back buttons because their position will most likely be different
    private NextButton nextTutorial, nextInfoStandard, nextInfoAcid, nextInfoFlying, nextInfoHealing, nextInfoBoss, nextInfoMother;
    private LoseButton loseButton;
    private InfoButton infoButton;
    protected static HydraButton hydraButton;
    protected static HoseBut hosebut;
    private RedoButton redoLevelButton;
    protected static SaltButton saltButton;
    private SpHydraBut spHydraBut;
    private SpSaltBut spSaltBut;
    private SpHosebutton spHoseBut;
    private RachelBut RachelBut;
    private JimmyBut JimmyBut;
    private TutorialButton tutorialButton;
    private PreviousButton previousButtontutorial;
    //buttons end
    //levels start
    public  static  ArrayList<Enemy> enemies; //temporarily holds level's enemy arraylist
    private ArrayList<LevelButton> levelButtons;
    private ArrayList<Level> levels;
    private Level currentLevel;
    //levels end
    protected static enum GameState {CHARACTERSELECT,MAINMENU, TUTORIAL, INGAME, GAMEOVER, WIN, SHOP, LEVELSELECT, CREDITS, CREDITShurshal, INFO}
    private ArrayList<SnailInfo> snailInfo;
    protected static GameState gameState, prevGameState;
    protected static enum TutorialState {PAGE1, PAGE2, PAGE3, PAGE4, PAGE5, PAGE6, PAGE7, PAGE8, PAGE9}
    protected static TutorialState tutState;
    protected static enum InfoState {SELECTION, STANDARD, ACID, FLYING, HEALING, BOSS, MOTHER, PERSON}
    protected static InfoState infoState;
    protected static enum WeaponState {REGWEAPON, HYDRA, HOSE}
    protected static WeaponState weaponState;
    protected static enum BulletType {SALT, WATER}
    protected static BulletType bulletType;
    //enums end
    //sounds start
    private Sound victorysound, defeatsound, snaildeadsound, currencysound, waterlimitsound, gameoversound, househpsound;
    private Music music;
    //sounds end
    protected ArrayList<Sprite> tutorials;
    private MyinputProcessor inputProcessor;

    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGame();
        drawGame();
    }

    public void create() {
        inputProcessor = new MyinputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

//extra shit
        batch = new SpriteBatch();
        preferences = new Preferences("Preferences");
        shapeRenderer = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);

        //FONT STUFF
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("helvetica.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = fontGenerator.generateFont(parameter);

        shell = new ArrayList<Snailshell>();
        jimmy = new Player();
        rachel = new Player();
        tap = new Vector3(); //location of tap
        house = new House();

        //UI start
        Texture hpBarTexture = new Texture("hpbar.png");
        hpBar = new Sprite(hpBarTexture);
        hpBar.setSize(width / 4, hpBarTexture.getHeight());
        hpBar.setPosition(width - hpBar.getWidth() - 5, height - 50);
        Texture waterBarTexture = new Texture("hpbar.png");
        waterBar = new Sprite(waterBarTexture);
        waterBar.setSize(width / 4, waterBarTexture.getHeight());
        waterBar.setPosition(width - 2 * hpBar.getWidth() - 10, height - 50);
        Texture saltBarTexture = new Texture("hpbar.png");
        saltBar = new Sprite(saltBarTexture);
        saltBar.setSize(width / 4, saltBarTexture.getHeight());
        saltBar.setPosition(width - 3 * hpBar.getWidth() - 15, height - 50);
        Texture progBar = new Texture("snail.png");
        progBarSnail = new Sprite(progBar);
        progBarSnail.setSize(progBar.getWidth(), progBar.getHeight());
        progBarSnail.setPosition(0, 0);
        //UI end
        //backgrounds start
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
        info = new Sprite(new Texture("info.png"));
        info.setSize(width, height);
        sign = new Sprite( new Texture("sign.png"));
        sign.setSize(width/2 ,height);
        sign.setPosition( width/2 , 0);
        //background end
        //credits start
        credits = new Sprite(new Texture("credits.png"));
        credits.setPosition(width / 2 - 305, 100);
        hurshalsface1 = new Sprite(new Texture("hurshal's face copy.png"));
        hurshalsface1.setPosition(width / 2 - 160, 0);
        hurshalsface1.setSize(2 * width / 3, 2 * height / 3);
        specialThanks = new Sprite(new Texture("hurshal.png"));
        specialThanks.setPosition(width / 2 - 428, height / 2 - 100);
        //credits end
        //tutorial start
        tutorials = new ArrayList<Sprite>();
        tutor1 = new Sprite(new Texture("tutorial 1.jpeg"));
        tutorials.add(tutor1);
        tutor2 = new Sprite(new Texture("tutorial 2.jpeg"));
        tutorials.add(tutor2);
        tutor3 = new Sprite(new Texture("tutorial 3.jpeg"));
        tutorials.add(tutor3);
        tutor4 = new Sprite(new Texture("tutorial4.jpeg"));
        tutorials.add(tutor4);
        tutor5 = new Sprite(new Texture("tutorial5.jpeg"));
        tutorials.add(tutor5);
        tutor6 = new Sprite(new Texture("tutorial 6.jpeg"));
        tutorials.add(tutor6);
        tutor7 = new Sprite(new Texture("tutorial7.jpeg"));
        tutorials.add(tutor7);
        tutor8 = new Sprite(new Texture("tutorial8.jpeg"));
        tutorials.add(tutor8);
        tutor9 = new Sprite(new Texture("tutorial9.jpeg"));
        tutorials.add(tutor9);
        for (Sprite tutor : tutorials) {
            tutor.setSize(width, height);
            tutor.setPosition(0, 0);
        }
        //tutorial end
        //info start
        snailInfo = new ArrayList<SnailInfo>();
        //info end
        //weapwns start
        currency = preferences.getInteger("currency", 0);
        Weapon.saltSupply = preferences.getInteger("saltsupply", 0);
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
            hose.enableSalt = true;
        }
        if (preferences.getInteger("hose", 0) == 1) {
            hose.enable = true;
        }

        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        shell = new ArrayList<Snailshell>();
        saltarm = new SaltArm();
        //weapwns end
        //buttons start #iSuckAtCoding
        startButtonMenu = new StartButton(width / 2 - 325, height / 2 - 200);
        redoLevelButton = new RedoButton(width / 2 - 200, height / 2 - 200);
        nextLevelButton = new NextLevelButton(width - 210,0);
        redoLevelButton.sprite.setSize(redoLevelButton.image.getWidth() + (redoLevelButton.image.getWidth() / 3), startButtonMenu.buttonGetHeight());
        redoLevelButton.spriteNope.setSize(redoLevelButton.image.getWidth() + (redoLevelButton.image.getWidth() / 3), startButtonMenu.buttonGetHeight());
        shopButtonMenu = new ShopButton(startButtonMenu.getXPos() + startButtonMenu.image.getWidth() + 10, startButtonMenu.getYPos());
        shopButtonGameEnd = new ShopButton(redoLevelButton.getXPos() + redoLevelButton.sprite.getWidth() + 10, redoLevelButton.getYPos());
        backButtonGameEnd = new BackButton(0, 0);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        backButtonTutorial = new BackButton(width - 210, height - backButtonShop.buttonGetHeight());
        previousButtontutorial = new PreviousButton( 20, height - backButtonShop.buttonGetHeight());
        backButtonCredits = new BackButton(width - 210, height - backButtonShop.buttonGetHeight());
        creditsButton = new CreditsButton(width / 2 - 100, startButtonMenu.getYPos() - startButtonMenu.buttonGetHeight() - 10);
        tutorialButton = new TutorialButton(width / 2 - 90 + creditsButton.sprite.getWidth(), creditsButton.getYPos());
        nextTutorial = new NextButton(width - 210, height - backButtonShop.buttonGetHeight());
        LoseButton tempLoseButton = new LoseButton(0, 0); //temporary quit button to get the sprite's dimensions
        loseButton = new LoseButton(0, height - tempLoseButton.sprite.getHeight());
        RachelBut = new RachelBut(width /2+ 100 , 50 );
        JimmyBut = new JimmyBut(100,50);

        backButtonInfoSelection = new BackButton(0, height-nextTutorial.sprite.getHeight());
        backButtonInfoStandard = new BackButton (0, height-nextTutorial.sprite.getHeight());
        nextInfoStandard=new NextButton (width-200,height-nextTutorial.sprite.getHeight());
        backButtonInfoAcid = new BackButton (0, height-nextTutorial.sprite.getHeight());
        nextInfoAcid=new NextButton (width-200,height-nextTutorial.sprite.getHeight());
        backButtonInfoFlying = new BackButton (0, height-nextTutorial.sprite.getHeight());
        nextInfoFlying=new NextButton (width-200,height-nextTutorial.sprite.getHeight());
        backButtonInfoHealing = new BackButton (0, height-nextTutorial.sprite.getHeight());
        nextInfoHealing=new NextButton (width-200,height-nextTutorial.sprite.getHeight());
        backButtonInfoBoss = new BackButton (0, height-nextTutorial.sprite.getHeight());
        nextInfoBoss=new NextButton (width-200,height-nextTutorial.sprite.getHeight());
        backButtonInfoMother = new BackButton (0, height-nextTutorial.sprite.getHeight());
        nextInfoMother=new NextButton (width-200,height-nextTutorial.sprite.getHeight());
        backButtonInfoPerson= new BackButton (0, height-nextTutorial.sprite.getHeight());
        infoButton = new InfoButton(width / 2 - 110 - creditsButton.sprite.getWidth(), creditsButton.getYPos());
        spHydraBut = new SpHydraBut(width /6, height / 2);
        spSaltBut = new SpSaltBut(width / 2, height / 2);
        spHoseBut = new SpHosebutton(width / 3, height / 2);

        spHose = new Sprite(new Texture("hose icon.png"));
        spHydra = new Sprite(new Texture("weapon icon.png"));
        spSalt = new Sprite(new Texture("Salt icon.png"));
        spHydra.setPosition(spHydraBut.getXPos() - 40, spHydraBut.getYPos() + 100);
        spSalt.setPosition(spSaltBut.getXPos() - 40,spSaltBut.getYPos() + 100);
        spHose.setPosition(spHoseBut.getXPos()- 40,spHoseBut.getYPos() +100);
        //shop images end
        SaltButton tempSaltButton = new SaltButton(0, 0);
        saltButton = new SaltButton(width - tempSaltButton.sprite.getWidth(), height - 600);
        HydraButton tempHydraButton = new HydraButton(0, 0);
        hydraButton = new HydraButton(width - tempHydraButton.sprite.getWidth(), height - 200);
        HoseBut tempHoseButton = new HoseBut(0, 0);
        hosebut = new HoseBut(width - tempHoseButton.sprite.getWidth(), hydraButton.getYPos());
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
        currency = 100000000;
        currentLevel = new Level(0);
        //levels end
        //info start
        // catalog=new ArrayList<Catalog>();
        for (int b = 0; b < numberOfTypes; b++) {
            if (b < 5) {
                snailInfo.add(new SnailInfo(b * 210, 200));
            } else {
                snailInfo.add(new SnailInfo((b - 5) * 210, 0));
            }
        }
        //currentSnail=new Catalog(1);
        //info end
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
        gameState = GameState.CHARACTERSELECT;
        tutState = TutorialState.PAGE1;
        infoState = InfoState.SELECTION;
        prevGameState = null;
        weaponState = WeaponState.REGWEAPON;
        bulletType = BulletType.WATER;
        House.hp = House.maxHP;
        Weapon.currentWater = Weapon.waterSupply;
        Weapon.saltSupply = Weapon.currentSalt;
        //buttons start
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        saltButton.position.set(saltButton.getXPos(), saltButton.getYPos());
        hosebut.position.set(hosebut.getXPos(), hosebut.getYPos());
        RachelBut.position.set(RachelBut.getXPos(),RachelBut.getYPos());
        JimmyBut.position.set(JimmyBut.getXPos(),JimmyBut.getYPos());
        spHydraBut.position.set(spHydraBut.getXPos(), spHydraBut.getYPos());
        spSaltBut.position.set(spSaltBut.getXPos(), spSaltBut.getYPos());
        spHoseBut.position.set(spHoseBut.getXPos(), spHoseBut.getYPos());
        startButtonMenu.position.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.position.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        shopButtonGameEnd.position.set(shopButtonGameEnd.getXPos(), shopButtonGameEnd.getYPos());
        backButtonGameEnd.position.set(backButtonGameEnd.getXPos(), backButtonGameEnd.getYPos());
        backButtonLevelSelect.position.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonCredits.position.set(backButtonCredits.getXPos(), backButtonCredits.getYPos());
        backButtonShop.position.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        backButtonTutorial.position.set(backButtonTutorial.getXPos(), backButtonTutorial.getYPos());
        previousButtontutorial.position.set(previousButtontutorial.getXPos(),previousButtontutorial.getYPos());
        nextTutorial.position.set(nextTutorial.getXPos(), nextTutorial.getYPos());
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
        creditsButton.position.set(creditsButton.getXPos(), creditsButton.getYPos());
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        redoLevelButton.position.set(redoLevelButton.getXPos(), redoLevelButton.getYPos());
        nextLevelButton.position.set(nextLevelButton.getXPos(), nextLevelButton.getYPos());
        for (int a = 0; a < numberOfLevels; a++)
            levelButtons.get(a).position.set(levelButtons.get(a).getXPos(), levelButtons.get(a).getYPos());
        for (int b = 0; b < numberOfTypes; b++)
            snailInfo.get(b).position.set(snailInfo.get(b).getXPos(), snailInfo.get(b).getYPos());
    }

    public static Vector3 getTapPosition() { //gets and translates coordinates of tap to game world coordinates
        tap.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(tap);
    }

    public void updateGame() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        music.play();
        if (gameState == GameState.CHARACTERSELECT){
            if (RachelBut.isPressed() ){
                rachel.enable = true;
                gameState = GameState.MAINMENU;
            }
            else if (JimmyBut.isPressed()){
                rachel.enable = false;
                gameState = GameState.MAINMENU;
            }

        }
        if (gameState == GameState.MAINMENU) { //in main menu
            if (startButtonMenu.touchup()) {
                startButtonMenu.pressedAction();
            } //go to level select
            if (shopButtonMenu.touchup()) {
                gameState = gameState.SHOP;
            }
            if (creditsButton.touchup()) {
                creditsButton.pressedAction();
            }
            //go to credits
            if (tutorialButton.touchup()) {
                tutorialButton.pressedAction();
            }
            if (infoButton.touchup()) {
                infoButton.pressedAction();

            }
        }else if (gameState == GameState.SHOP) { //in shop

            if (backButtonShop.touchup()) {
                gameState = gameState.MAINMENU;
            }

            // part of trial stuff

            /*if (backButtonShop.pressable() && backButtonShop.isPressed() && backButtonShop.status == false) {
                backButtonShop.status = true;

            }
            if (!backButtonShop.isPressed() && backButtonShop.status == true) {
                gameState = gameState.MAINMENU;
                backButtonShop.status = false;
            }*/
            //old stuff
            if (currency > spHydraBut.price && spHydraBut.isPressed()) {
                spHydraBut.ownd = true;
                currency -= spHydraBut.price;
                preferences.putInteger("hydra", 1);
                //preferences.flush();

            }
            if (preferences.getInteger("hydra", 0) == 1) {
                hydra.enable = true;
            }

            if (currency > spSaltBut.price && spSaltBut.isPressed()) {
                currency -= spSaltBut.price;
                Weapon.saltSupply++;
                Weapon.currentSalt = Weapon.saltSupply;
                preferences.putInteger("saltsupply", (int) Weapon.saltSupply);
                //preferences.flush();
            }
            if (currency >= spHoseBut.price && spHoseBut.isPressed() && !spHoseBut.ownd) {
                spHoseBut.ownd = true;
                currency -= spHoseBut.price;
                preferences.putInteger("hose", 1);
                //preferences.flush();
            }
            if (preferences.getInteger("hose", 0) == 1) {hose.enable = true;}
            if (preferences.getInteger("salt", 0) == 1) {
                waterGun.enableSalt = true;
                hydra.enableSalt = true;
                hose.enableSalt = true;
            }


        } else if (gameState == GameState.TUTORIAL) {
            if (tutState == TutorialState.PAGE9) {
                if (backButtonTutorial.touchup()) {
                    backButtonTutorial.pressedAction();
                } //go to next page of tutorial
            } else { //tutState == TutorialState.PAGE4
                if (nextTutorial.isPressed()) {
                    buttonstate = 1;
                }
                if (nextTutorial.touchup() && buttonstate == 1) {
                    nextTutorial.pressedAction();
                    buttonstate = 0;
                } //go to next page of tutorial
            }
        } else if (gameState == GameState.LEVELSELECT) { //in level select
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
            }

            else if (gameState == GameState.INFO) {
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
                if (snailInfo.get(b).isPressed() && infoState == InfoState.SELECTION) {
                    snailInfo.get(b).pressedAction();
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
                            enemies.add(new Person(width / 11, height / 5, 0, 0, 0, 0));
                    }
                }
            }

        } else if (gameState == GameState.CREDITS) {
            if (Gdx.input.justTouched())
                gameState = GameState.CREDITShurshal;
        } else if (gameState == GameState.CREDITShurshal) {
            if (backButtonCredits.touchup()) {
                backButtonCredits.pressedAction();
            }
        }

        else if (gameState == GameState.INGAME) {
            progBarSnail.setPosition(((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width - (progBarSnail.getWidth() / 2), 0);
            if (hydraButton.isPressed())
                bulletType = BulletType.WATER;
            else if (saltButton.isPressed())
                bulletType = BulletType.SALT;
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
            if (weaponState == WeaponState.REGWEAPON) {
                if (waterGun.enable)
                    waterGun.Update(water);
                if (waterGun.enableSalt) {
                    if (saltButton.isPressed())
                        bulletType = BulletType.SALT;
                    if (bulletType == BulletType.WATER)
                        waterGun.Update(water);
                    if (bulletType == BulletType.SALT) {
                        saltarm.sprite.setRotation(Weapon.rot);
                        waterGun.Update2(shakers);
                        //saltarm.Update2(shakers);
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
                        //saltarm.Update2(shakers);
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
                        //saltarm.Update2(shakers);
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
                            currentLevel.enemyCount++;
                            a--;
                            currency += 5;//TODO:
                        }
                    }
                }
                if (projectileHit){
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
            progBarSnail.setPosition(((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width - progBarSnail.getWidth() / 2, -progBarSnail.getHeight() / 2);
            if (House.hp <= 0 || loseButton.isPressed())
                gameState = GameState.GAMEOVER;
            if (enemies.size() == 0)
                gameState = GameState.WIN;
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
            Weapon.currentSalt = Weapon.saltSupply;
            currentLevel.enemyCount = 0;
            bulletType = BulletType.WATER;
            if (backButtonGameEnd.isPressed()) {
                buttonstate = 1;
                System.out.println(buttonstate);
            }

            if (backButtonGameEnd.touchup() && buttonstate == 1) {
                backButtonGameEnd.pressedAction();
                buttonstate = 0;
                System.out.println(buttonstate);
            } //go to main menu
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
            if (nextLevelButton.isPressed()&& nextLevelButton.pressable()){
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
        font.setScale(1.5f);
        font.setColor(0, 0, 0, 1);
        if (gameState == GameState.CHARACTERSELECT) {
            batch.begin();
            levelSelect.draw(batch);
            JimmyBut.draw(batch);
            RachelBut.draw(batch);
            font.draw(batch, "Would you like to play as:", width / 6, height - 10);
            font.draw(batch, "Jimmy", JimmyBut.getXPos() + 20, JimmyBut.getYPos());
            font.draw(batch, "Rachel", RachelBut.getXPos() + 20, RachelBut.getYPos());
            batch.end();
        }

        //main Menu
        if (gameState == GameState.MAINMENU) {
            batch.begin();
            menu.draw(batch);
            startButtonMenu.sprite.draw(batch);
            if (startButtonMenu.isPressed()) {
                startButtonMenu.spriteShade.draw(batch);
            }
            if (preferences.getInteger("tutorial", 0) == 2) {
                tutorialButton.sprite.draw(batch);
            }
            if (tutorialButton.isPressed()) {
                tutorialButton.spriteShade.draw(batch);
            }
            creditsButton.sprite.draw(batch);
            if (creditsButton.isPressed()) {
                creditsButton.spriteShade.draw(batch);
            }
            shopButtonMenu.sprite.draw(batch);
            infoButton.sprite.draw(batch);
            if (shopButtonMenu.isPressed()) {
                shopButtonMenu.spriteShade.draw(batch);
            }
            //font.draw(batch, "Current state: main menu", 10, height);
            batch.end();
        }
        //credits
        else if (gameState == GameState.CREDITS) {
            batch.begin();
            levelSelect.draw(batch);
            credits.draw(batch);
            batch.end();
        } else if (gameState == GameState.CREDITShurshal) {
            batch.begin();
            levelSelect.draw(batch);
            hurshalsface1.draw(batch);
            specialThanks.draw(batch);
            backButtonCredits.draw(batch);
            if (backButtonCredits.isPressed()) {
                backButtonCredits.spriteShade.draw(batch);
            }

            batch.end();
        }
        //level
        else if (gameState == GameState.LEVELSELECT) { //in level select
            batch.begin();
            levelSelect.draw(batch);
            for (int a = 0; a < numberOfLevels; a++) {
                LevelButton lb = levelButtons.get(a);
                batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
            }
            batch.draw(backButtonLevelSelect.image, backButtonLevelSelect.position.x, backButtonLevelSelect.position.y);
            if (backButtonLevelSelect.isPressed()) {
                backButtonLevelSelect.spriteShade.draw(batch);
            }
            font.draw(batch, "Current state: level select", 10, height);
            batch.end();
        }
        //shop
        else if (gameState == GameState.SHOP) { //in shop
            batch.begin();
            //font.draw(batch, "Current state: shop", 10, height - 50);
            shop.draw(batch);
            spHydraBut.sprite.draw(batch);
            spSaltBut.sprite.draw(batch);
            spHoseBut.sprite.draw(batch);
            if (spHoseBut.ownd) {
                spHoseBut.spriteNope.draw(batch);
            }
            if (spHydraBut.ownd) {
                spHydraBut.spriteNope.draw(batch);
            }


            spSalt.draw(batch);
            spHydra.draw(batch);
            spHose.draw(batch);

            font.setScale(1.5f);
            batch.draw(backButtonShop.sprite, backButtonShop.position.x, backButtonShop.position.y);

            font.draw(batch, "$" + spHoseBut.price, spHydraBut.getXPos(), spHydraBut.getYPos() - 10);
            font.draw(batch, "$" + spHoseBut.price, spHoseBut.getXPos(), spHoseBut.getYPos() - 10);
            font.draw(batch, "$" + spSaltBut.price, spSaltBut.getXPos(), spSaltBut.getYPos() - 10);

            font.draw(batch, " salts: " + (int) Weapon.saltSupply, spSaltBut.getXPos() + spSaltBut.buttonGetWidth(), spSaltBut.getYPos() + spSaltBut.buttonGetHeight());
            font.draw(batch, "shells: " + currency, 10, height);
            backButtonShop.sprite.draw(batch);
            if (backButtonShop.isPressed()) {
                backButtonShop.spriteShade.draw(batch);
            }
            batch.end();
        } else if (gameState == GameState.TUTORIAL) {
            batch.begin();
            if (tutState == TutorialState.PAGE1) {
                tutor1.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE2) {
                tutor2.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE3) {
                tutor3.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE4) {
                tutor4.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE5) {
                tutor5.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE6) {
                tutor6.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE7) {
                tutor7.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            } else if (tutState == TutorialState.PAGE8) {
                tutor8.draw(batch);
                nextTutorial.draw(batch);
                if (nextTutorial.isPressed()) {
                    nextTutorial.spriteShade.draw(batch);
                }
            }
            batch.end();
        } else if (gameState == GameState.INFO) { //in info
            batch.begin();
            font.setScale((float) ((width / 1196) * (1.4)));
            if (infoState == InfoState.SELECTION) {
                info.draw(batch);
                for (int b = 0; b < numberOfTypes; b++) {
                    SnailInfo lb = snailInfo.get(b);
                    batch.draw(lb.getButtonImage(b + 1), lb.bound.x, lb.bound.y);
                }
                batch.draw(backButtonInfoSelection.image, backButtonInfoSelection.position.x, backButtonInfoSelection.position.y);
            } else if (infoState == InfoState.STANDARD) {
                info.draw(batch);
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:very weak", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "STANDARD SNAIL", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoStandard.image, backButtonInfoStandard.position.x, backButtonInfoStandard.position.y);
                batch.draw(nextInfoStandard.image, nextInfoStandard.position.x, nextInfoStandard.position.y);
            } else if (infoState == InfoState.ACID) {
                info.draw(batch);
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:weak", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-'speed up' slimes ", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "ACID SNAIL", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoAcid.image, backButtonInfoAcid.position.x, backButtonInfoAcid.position.y);
                batch.draw(nextInfoAcid.image, nextInfoAcid.position.x, nextInfoAcid.position.y);

            } else if (infoState == InfoState.FLYING) {
                info.draw(batch);
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-'speed up' slime bombs", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "FLYING SNAIL", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoFlying.image, backButtonInfoFlying.position.x, backButtonInfoFlying.position.y);
                batch.draw(nextInfoFlying.image, nextInfoFlying.position.x, nextInfoFlying.position.y);

            } else if (infoState == InfoState.HEALING) {
                info.draw(batch);
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-heals other snails", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "HEALING SNAIL", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoHealing.image, backButtonInfoHealing.position.x, backButtonInfoHealing.position.y);
                batch.draw(nextInfoHealing.image, nextInfoHealing.position.x, nextInfoHealing.position.y);

            } else if (infoState == InfoState.BOSS) {
                info.draw(batch);
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:very strong", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-high HP", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "KING SNAILEY", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoBoss.image, backButtonInfoBoss.position.x, backButtonInfoBoss.position.y);
                batch.draw(nextInfoBoss.image, nextInfoBoss.position.x, nextInfoBoss.position.y);

            } else if (infoState == InfoState.MOTHER) {
                info.draw(batch);
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                }

                sign.draw(batch);
                font.draw(batch, "-Speed:very fast", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:weak", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-high HP", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "MOTHER SNAIL", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoMother.image, backButtonInfoMother.position.x, backButtonInfoMother.position.y);
                batch.draw(nextInfoMother.image, nextInfoMother.position.x, nextInfoMother.position.y);

            } else if (infoState == InfoState.PERSON) {
                info.draw(batch);
                for (Enemy enemy : enemies) {//draws and animates enemies
                    enemy.draw(batch, time);
                }
                sign.draw(batch);
                font.draw(batch, "-Speed:very fast", sign.getX() + 50, (3 * height) / 4);
                font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (2 * font.getLineHeight()));
                font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (4 * font.getLineHeight()));
                font.draw(batch, "ZOMBIE", width / 9, (2 * font.getLineHeight()));
                batch.draw(backButtonInfoPerson.image, backButtonInfoPerson.position.x, backButtonInfoPerson.position.y);
            }

            font.draw(batch, "Current state: info", 10, height);
            batch.end();
        } else if (gameState == GameState.INGAME) { //in-game
            batch.begin();
            laun.draw(batch);
            house.draw(batch);
            saltButton.sprite.draw(batch);
            font.draw(batch, "hydra salt " + hydra.enableSalt, 300, 400);
            if (!rachel.enable) {
                batch.draw(jimmy.sprite, jimmy.bound.x, jimmy.bound.y);
            } else if (rachel.enable) {
                batch.draw(rachel.rachel, rachel.bound.x, rachel.bound.y);
            }
            font.draw(batch, "hydra salt " + hydra.enableSalt, 300, 400);
            batch.draw(jimmy.sprite, jimmy.bound.x, jimmy.bound.y);
            if (weaponState == WeaponState.REGWEAPON) {
                font.draw(batch, "current weap reg ", 350, 350);
                batch.draw(hydraButton.sprite, hydraButton.position.x, hydraButton.position.y);
                waterGun.sprite.draw(batch);
            } else if (weaponState == WeaponState.HYDRA) {
                font.draw(batch, "current weap hydra", 350, 350);
                hydra.sprite.draw(batch);
                batch.draw(hosebut.sprite, hydraButton.position.x, hydraButton.position.y);
            } else if (weaponState == WeaponState.HOSE) {
                font.draw(batch, "current weap hose", 350, 350);
                hose.sprite.draw(batch);
                batch.draw(hydraButton.watergun, hydraButton.getXPos(), hydraButton.getYPos());
            }
            for (ThrowyThingy proj : water)
                proj.sprite.draw(batch);
            for (Salt bullet : shakers)
                bullet.sprite.draw(batch);
            for (Droppings droppies : droppings)
                droppies.draw(batch);
            for (BombDrop bomb : bombs)
                bomb.draw(batch);
            for (Enemy enemy : enemies)
                enemy.draw(batch, time);
            font.draw(batch, "" + projectileHit, 300, 300);
            for (Snailshell snailshell : shell) {
                batch.draw(snailshell.image, snailshell.bounds.x, snailshell.bounds.y);
                //snailshell.sprite.draw(batch);
            }
            font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);
            font.draw(batch, "Current state: in-game", 10, height);
            font.draw(batch, "Snailshells: " + currency, 10, height - 200);
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            int topBarXPos = 50;
            int barHeight = 30;
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.rect(0, height - height / 8, width, height / 8);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(0, 0, width, 20);
            shapeRenderer.rect(width - hpBar.getWidth() - 5, height - topBarXPos, width / 4, barHeight);
            shapeRenderer.rect(width - 2 * hpBar.getWidth() - 10, height - topBarXPos, width / 4, barHeight);
            shapeRenderer.rect(width - 3 * hpBar.getWidth() - 15, height - topBarXPos, width / 4, barHeight);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(width - hpBar.getWidth() - 5, height - topBarXPos, (House.hp / House.maxHP) * (width / 4), barHeight);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.rect(width - 2 * hpBar.getWidth() - 10, height - topBarXPos, (Weapon.currentWater / Weapon.waterSupply) * (width / 4), barHeight);
            shapeRenderer.setColor(Color.GRAY);
            shapeRenderer.rect(width - 3 * hpBar.getWidth() - 15, height - topBarXPos, (Weapon.currentSalt / Weapon.saltSupply) * (width / 4), barHeight);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(0, 0, ((float) currentLevel.enemyCount / currentLevel.totalEnemies) * width, 40);
            shapeRenderer.end();
            batch.begin();
            progBarSnail.draw(batch);
            hpBar.draw(batch);
            waterBar.draw(batch);
            saltBar.draw(batch);
            loseButton.sprite.draw(batch);
            font.draw(batch, (int) House.hp + "/" + (int) House.maxHP, width - hpBar.getWidth(), height - 2 * barHeight);
            font.draw(batch, (int) Weapon.currentWater + "/" + (int) Weapon.waterSupply, width - 2 * hpBar.getWidth(), height - 2 * barHeight);
            font.draw(batch, (int) Weapon.currentSalt + "/" + (int) Weapon.saltSupply, width - 3 * hpBar.getWidth(), height - 2 * barHeight);
            if (weaponState == WeaponState.REGWEAPON) {
                font.draw(batch, "current weap reg ", 350, 350);
                hydraButton.sprite.draw(batch);
                if (bulletType == BulletType.WATER)
                    waterGun.sprite.draw(batch);
                else if (bulletType == BulletType.SALT)
                    saltarm.sprite.draw(batch);
            } else if (weaponState == WeaponState.HYDRA) {
                font.draw(batch, "current weap hydra", 350, 350);
                hosebut.sprite.draw(batch);
                if (bulletType == BulletType.WATER)
                    hydra.sprite.draw(batch);
                else if (bulletType == BulletType.SALT)
                    saltarm.sprite.draw(batch);
            } else if (weaponState == WeaponState.HOSE) {
                font.draw(batch, "current weap hose", 350, 350);
                hydraButton.watergunSprite.setPosition(hydraButton.getXPos(), hydraButton.getYPos());
                hydraButton.watergunSprite.draw(batch);
                if (bulletType == BulletType.WATER)
                    hose.sprite.draw(batch);
                else if (bulletType == BulletType.SALT)
                    saltarm.sprite.draw(batch);
            }
            saltButton.sprite.draw(batch);
            batch.end();
        }

        if (gameState == GameState.GAMEOVER || gameState == GameState.WIN) { //in game over/win
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


            nextLevelButton.sprite.draw(batch);


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
