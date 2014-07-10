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
    private int width, height,currency;
    private static Vector3 tap;
    private BitmapFont font;
    private Player jimmy;
    private House house;
    private Snailshell shell; //should be an arraylist of shells
    public boolean snaildead; //is this supposed to be here?
    private float time = 0;
    private Preferences preferences;

    private Texture text;
    private Texture hurshal;
    //backgrounds start
    private Texture mainMenuBackground, gameOverBackground, lawn, levelscreen;
    //backgrounds end
    //weapwns start
    private Weapon waterGun;
    private Hydra hydra;

    private Raygun raygun;
    private Supersoaker supersoaker;
    private Hose hose;
    private HydraRaygun hydraraygun;
    private ArrayList<ThrowyThingy> water; //holds watergun shots
    //weapwns end
    //buttons start
    private StartButton startButtonMenu;
    private ShopButton shopButtonMenu;
    private BackButton backButtonShop, backButtonGameOver, backButtonLevelSelect, backButtonCredits ;//different back buttons because their position will most likely be different
    private LoseButton loseButton;
    private HydraButton hydraButton;
    private SupersoakerButton supersoakerbutton;
    private HoseButton hosebutton;
    private RaygunButton raygunbutton;
    private HydraRaygunButton hydraraygunbutton;
    private CreditsButton creditsButton;
    //buttons end

    //store cost
    private int hydracost;
    private int supersoakercost;
    private int hosecost;
    private int rayguncost;
    private int hydrarayguncost;
    private int upgradewalls2cost;
    private int upgradewall3cost;
    private int upgradewall4cost;
    private int upgradewall5cost;
    private int upgradewall6cost;
    private int upgradewall7cost;

    public boolean upgradewall2unlock;
    public boolean upgradewall3unlock;
    public boolean upgradewall4unlock;
    public boolean upgradewall5unlock;
    public boolean upgradewall6unlock;
    public boolean upgradewall7unlock;


    private int upgradewall2;
    private int upgradewall3;
    private int upgradewall4;
    private int upgradewall5;
    private int upgragewall6;
    private int upgradewall7;




    //in shop buttons
    private UpgradeWall2Button upgradewall2button;
    private UpgradeWall3Button upgradewall3button;
    private UpgradeWall4Button upgradewall4button;
    private UpgradeWall5Button upgradewall5button;
    private UpgradeWall6Button upgradewall6button;
    private UpgradeWall7Button upgradewall7button;
    private SpHydryBut spHydryBut;
    private SuperSoakerShopButton supersoakershopbutton;
    private HoseShopButton hoseshopbutton;
    private RaygunShopButton raygunshopbutton;
    private HydraRaygunShopButton hydraraygunshopbutton;


    //sound
    //private Sound hydrasound;
    //private Sound watergunsound;
    //private Sound victorysound;
    //private Sound defeatsound;
    //private Sound snaildeadsound;
    //private Sound currencysound;
    //private Sound waterlimitsound;
    //private Sound gameoversound;
    //private Sound househpsound;
    //private Music music;

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
    protected static enum GameState {MAINMENU, INGAME, GAMEOVER, SHOP, LEVELSELECT,CREDITS,CREDITShurshal}
    protected static GameState gameState;
    protected static enum WeaponState {REGWEAPON, HYDRA, SUPERSOAKER, HOSE, RAYGUN, HYDRARAYGUN}
    protected static WeaponState weaponState;
    //game states end
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGame();
        drawGame();
    }
    public void create() {
        preferences = new Preferences("Preferences");
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        mainMenuBackground = new Texture("sidewaysmenu.png");
        levelscreen = new Texture("levelscreen.png");
        lawn = new Texture("lawn.jpeg");
        text = new Texture("credits.png");
        hurshal = new Texture("hurshal.png");
        gameOverBackground = new Texture("gameover.png");
        shell = new Snailshell();
        jimmy = new Player();
        tap = new Vector3(); //location of tap
        house = new House();
        currency=1000;

        //weapwns start
        waterGun = new Weapon();
        if(preferences.getInteger("waterGun", 1)== 1){waterGun.enable=true;}
        hydra = new Hydra();
        if (preferences.getInteger("hydra", 0) == 1) {hydra.enable = true;}
        supersoaker = new Supersoaker();
        if (preferences.getInteger("supersoaker", 0) == 1) {supersoaker.enable = true;}
        raygun = new Raygun();
        if (preferences.getInteger("raygun", 0) == 1) {raygun.enable = true;}
        hose = new Hose();
        if (preferences.getInteger("hose", 0) == 1) {hose.enable = true;}
        hydraraygun = new HydraRaygun();
        if (preferences.getInteger("hydraraygun", 0) == 1) {hydraraygun.enable = true;}
        water = new ArrayList<ThrowyThingy>();
        droppings = new ArrayList<Droppings>();
        bombs = new ArrayList<BombDrop>();
        //weapwns end
        //game states start

        //store
        hydracost = 500;
        supersoakercost = 20000;
        rayguncost = 100000;
        hydrarayguncost = 1000000;
        upgradewalls2cost = 100; // + 10 househp 110
        upgradewall3cost = 1000; // + 10 househp 120
        upgradewall4cost = 5000; // + 20 househp 140
        upgradewall5cost = 10000; // + 20 househp 160
        upgradewall6cost = 30000; // + 30 househp 190
        upgradewall7cost = 100000; // + 50 househp; 240

        upgradewall2 =  (int) House.hp + 10;
        upgradewall3 =  (int) House.hp + 20;
        upgradewall4 =  (int) House.hp + 40;
        upgradewall5 =  (int) House.hp+ 60;
        upgragewall6 =  (int) House.hp+ 90;
        upgradewall7 =  (int) House.hp + 140;

        upgradewall2unlock = false;
        upgradewall3unlock = false;
        upgradewall4unlock = false;
        upgradewall5unlock = false;
        upgradewall6unlock = false;
        upgradewall7unlock = false;

        //buttons start
        startButtonMenu = new StartButton(width / 2 - 215, height / 2 - 200);
        shopButtonMenu = new ShopButton(width - 150, height - 100);
        backButtonGameOver = new BackButton(width - 210, 10);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonCredits = new BackButton(width - 210,10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        loseButton = new LoseButton(width - 210, height - 210);
        hydraButton = new HydraButton(width - 550, height - 250);
        supersoakerbutton = new SupersoakerButton(width - 550, height - 350);
        hosebutton = new HoseButton(width - 550, height - 100);
        raygunbutton = new RaygunButton(width - 550, height - 500);
        hydraraygunbutton = new HydraRaygunButton(width - 550, height - 450);
        creditsButton = new CreditsButton(width/ 2 - 100 , 40);
        //buttons end

        //shop buttons
        upgradewall2button = new UpgradeWall2Button(width / 2, height / 7);
        upgradewall3button = new UpgradeWall3Button(width / 3, height / 8);
        upgradewall4button = new UpgradeWall4Button(width / 4, height / 9);
        upgradewall5button = new UpgradeWall5Button(width / 5, height / 6);
        upgradewall6button = new UpgradeWall6Button(width / 2, height / 8);
        upgradewall7button = new UpgradeWall7Button(width / 11, height /9);
        spHydryBut = new SpHydryBut(width / 10, height  / 14);
        raygunshopbutton = new RaygunShopButton(width / 12, height / 6);
        hydraraygunshopbutton = new HydraRaygunShopButton(width / 12, height / 5);
        supersoakershopbutton = new SuperSoakerShopButton(width  / 6, height / 14);
        hoseshopbutton = new HoseShopButton(width / 10, height / 15);

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

        hydracost = 500;
        supersoakercost = 20000;
        hosecost = 10000;
        rayguncost = 100000;
        hydrarayguncost = 1000000;
        upgradewalls2cost = 100; // + 10 househp 110
        upgradewall3cost = 1000; // + 10 househp 120
        upgradewall4cost = 5000; // + 20 househp 140
        upgradewall5cost = 10000; // + 20 househp 160
        upgradewall6cost = 30000; // + 30 househp 190
        upgradewall7cost = 100000; // + 50 househp; 240

        upgradewall2 = (int)House.hp + 10;
        upgradewall3 = (int)House.hp + 20;
        upgradewall4 = (int)House.hp + 40;
        upgradewall5 = (int)House.hp + 60;
        upgragewall6 = (int)House.hp + 90;
        upgradewall7 = (int)House.hp + 140;

        //sound
        //watergunsound = Gdx.audio.newSound(Gdx.files.internal("watergun.mp3"));
        //victorysound = Gdx.audio.newSound(Gdx.files.internal("victorysound.mp3"));
        //defeatsound = Gdx.audio.newSound(Gdx.files.internal("defeatsound.mp3"));
        //hydrasound = Gdx.audio.newSound(Gdx.files.internal("hydra.mp3"));
        //househpsound = Gdx.audio.newSound(Gdx.files.internal("househp.mp3"));
        //gameoversound = Gdx.audio.newSound(Gdx.files.internal("gameover.mp3"));
        //currencysound = Gdx.audio.newSound(Gdx.files.internal("money.mp3"));

        //Music music = Gdx.audio.newMusic(Gdx.files.internal("burningdownthehouse.mp3"));
        //levels end
        resetGame();
    }
    public void resetGame() {
        camera.position.set(width / 2, height / 2, 0);
        gameState = GameState.MAINMENU;
        weaponState = WeaponState.REGWEAPON;
        House.hp = House.maxHP;
        Weapon.waterLimit = 15;
        //buttons start
        startButtonMenu.position.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.position.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        backButtonGameOver.position.set(backButtonGameOver.getXPos(), backButtonGameOver.getYPos());
        backButtonLevelSelect.position.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonCredits.position.set(backButtonCredits.getXPos(),backButtonCredits.getYPos());
        backButtonShop.position.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        creditsButton.position.set(creditsButton.getXPos(),creditsButton.getYPos());
        for (int a = 0; a < numberOfLevels; a++)
            levelButtons.get(a).position.set(levelButtons.get(a).getXPos(), levelButtons.get(a).getYPos());
        loseButton.position.set(loseButton.getXPos(), loseButton.getYPos());
        hydraButton.position.set(hydraButton.getXPos(), hydraButton.getYPos());
        supersoakerbutton.position.set(supersoakerbutton.getXPos(), supersoakerbutton.getYPos());
        hosebutton.position.set(hosebutton.getXPos(), hosebutton.getYPos());
        raygunbutton.position.set(raygunbutton.getXPos(), hosebutton.getYPos());
        hydraraygunbutton.position.set(hydraraygunbutton.getXPos(), hydraraygunbutton.getYPos());
        currency = 90000;
        weaponState = WeaponState.REGWEAPON;
        House.hp = House.maxHP;
        Weapon.waterLimit = Weapon.waterSupply;
        //shop


        UpgradeWall2Button.remove = false;
        UpgradeWall3Button.remove = false;
        UpgradeWall4Button.remove = false;
        UpgradeWall5Button.remove = false;
        UpgradeWall6Button.remove = false;
        UpgradeWall7Button.remove = false;

        spHydryBut.position.set(spHydryBut.getXPos(), spHydryBut.getYPos());
        supersoakershopbutton.position.set(supersoakershopbutton.getXPos(), supersoakershopbutton.getYPos());
        raygunshopbutton.position.set(raygunshopbutton.getXPos(), raygunshopbutton.getYPos());
        hydraraygunshopbutton.position.set(hydraraygunshopbutton.getXPos(), hydraraygunshopbutton.getYPos());
        hoseshopbutton.position.set(hoseshopbutton.getXPos(), hoseshopbutton.getYPos());
        upgradewall2button.position.set(upgradewall2button.getXPos(), upgradewall2button.getYPos());
        upgradewall3button.position.set(upgradewall3button.getXPos(), upgradewall3button.getYPos());
        upgradewall4button.position.set(upgradewall4button.getXPos(), upgradewall4button.getYPos());
        upgradewall5button.position.set(upgradewall5button.getXPos(), upgradewall5button.getYPos());
        upgradewall6button.position.set(upgradewall6button.getXPos(), upgradewall6button.getYPos());
        upgradewall7button.position.set(upgradewall7button.getXPos(), upgradewall7button.getYPos());


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
            if (startButtonMenu.pressable() && startButtonMenu.isPressed()) {
                startButtonMenu.pressedAction();
            } //go to level select
            if (shopButtonMenu.pressable() && shopButtonMenu.isPressed()) {
                shopButtonMenu.pressedAction();
            } //go to shop
            if (startButtonMenu.pressable() && creditsButton.isPressed()) {
                creditsButton.pressedAction();
            }
        }
        /*
        *** shop currently contains ***
         - shop --> main menu
        */
        else if (gameState == GameState.SHOP) { //in shop
            if (backButtonShop.pressable() && backButtonShop.isPressed()) {
                backButtonShop.pressedAction();
            } //go to main menu
            if (spHydryBut.isPressed() && currency >= spHydryBut.price) {
                currency = currency - spHydryBut.price;
                preferences.putInteger("hydra", 1);
                preferences.flush();

            }
            if ((supersoakershopbutton.isPressed() && currency >= supersoakershopbutton.price)) {

                currency = currency - supersoakershopbutton.price;
                preferences.putInteger("supersoaker", 1);
                preferences.flush();

            }
            if ((hoseshopbutton.isPressed() && currency >= hoseshopbutton.price)) {

                currency = currency - hoseshopbutton.price;
                preferences.putInteger("hose", 1);
                preferences.flush();

            }
            if ((raygunshopbutton.isPressed() && currency >= raygunshopbutton.price)) {

                currency = currency - rayguncost;
                preferences.putInteger("raygun", 1);
                preferences.flush();
            }
            if ((hydraraygunshopbutton.isPressed() && currency >= hydraraygunshopbutton.price)) {
                currency = currency - hydraraygunshopbutton.price;
                preferences.putInteger("hydraygunshopbutton", 1);
                preferences.flush();

            }
            if ((upgradewall2button.isPressed()) && currency >= upgradewall2button.price) {

                currency = currency - upgradewall2button.price;
                preferences.putInteger("upgradewall2", 1);
                preferences.flush();
                upgradewall2unlock = true;

            }
            if ((upgradewall3button.isPressed() && currency >= upgradewall3button.price)) {
                currency = currency - upgradewall3cost;
                preferences.putInteger("upgradewall3", 1);
                preferences.flush();
                upgradewall3unlock = true;
            }
            if ((upgradewall4button.isPressed() && currency >= upgradewall4button.price)) {
                currency = currency - upgradewall4cost;
                preferences.putInteger("upgradewall4", 1);
                preferences.flush();
                upgradewall4unlock = true;
            }
            if ((upgradewall5button.isPressed() && currency >= upgradewall5button.price)) {
                currency = currency - upgradewall5cost;
                preferences.putInteger("upgradewall5", 1);
                preferences.flush();
                upgradewall5unlock = true;
            }
            if ((upgradewall6button.isPressed() && currency >= upgradewall6button.price)) {
                currency = currency - upgradewall6cost;
                preferences.putInteger("upgradewall6", 1);
                preferences.flush();
                upgradewall6unlock = true;
            }
            if ((upgradewall7button.isPressed() && currency >= upgradewall7button.price)) {
                currency = currency - upgradewall7cost;
                preferences.putInteger("upgradewall7", 1);
                preferences.flush();
                upgradewall7unlock = true;
            }

            if (upgradewall2unlock = true) {
                House.hp = upgradewall2;
                UpgradeWall2Button.remove = true;
            } else if (upgradewall3unlock = true) {
                House.hp = upgradewall3;
                UpgradeWall3Button.remove = true;
            } else if (upgradewall4unlock = true) {
                House.hp = upgradewall4;
                UpgradeWall4Button.remove = true;
            } else if (upgradewall5unlock = true) {
                House.hp = upgradewall5;
                UpgradeWall5Button.remove = true;
            } else if (upgradewall6unlock = true) {
                House.hp = upgragewall6;
                UpgradeWall6Button.remove = true;
            } else if (upgradewall7unlock = true) {
                House.hp = upgradewall7;
                UpgradeWall7Button.remove = true;
            }
        }


        /*
        *** level select currently contains ***
         - level select --> in-game
         - level select --> main menu
        */
        else if (gameState == GameState.LEVELSELECT) { //in level select
            for (int a = 0; a < numberOfLevels; a++) {
                if (levelButtons.get(a).pressable() && levelButtons.get(a).isPressed()) {
                    currentLevel = levels.get(a); //current level is now whatever level that was pressed
                    enemies = currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
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
                gameState = GameState.MAINMENU;
            }
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
            if (hydra.enable) {
                if (hydraButton.pressable() && hydraButton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {
                        weaponState = WeaponState.HYDRA;
                    } //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {
                        weaponState = WeaponState.REGWEAPON;
                    } //switch to regular gun
                    else if (weaponState == WeaponState.SUPERSOAKER) {
                        weaponState = WeaponState.HYDRA;
                    } else if (weaponState == WeaponState.HOSE) {
                        weaponState = WeaponState.HYDRA;
                    } else if (weaponState == WeaponState.RAYGUN) {
                        weaponState = WeaponState.HYDRA;
                    } else if (weaponState == WeaponState.HYDRARAYGUN) {
                        weaponState = WeaponState.HYDRA;
                    }
                }
            }
            if (supersoaker.enable) {
                if (supersoakerbutton.pressable() && supersoakerbutton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {
                        weaponState = WeaponState.SUPERSOAKER;
                    } //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {
                        weaponState = WeaponState.SUPERSOAKER;
                    } //switch to regular gun
                    else if (weaponState == WeaponState.SUPERSOAKER) {
                        weaponState = WeaponState.REGWEAPON;
                    } else if (weaponState == WeaponState.HOSE) {
                        weaponState = WeaponState.SUPERSOAKER;
                    } else if (weaponState == WeaponState.RAYGUN) {
                        weaponState = WeaponState.SUPERSOAKER;
                    } else if (weaponState == WeaponState.HYDRARAYGUN) {
                        weaponState = WeaponState.SUPERSOAKER;
                    }
                }
            }
            if (hose.enable) {
                if (hosebutton.pressable() && hosebutton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {
                        weaponState = WeaponState.HOSE;
                    } //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {
                        weaponState = WeaponState.HOSE;
                    } //switch to regular gun
                    else if (weaponState == WeaponState.SUPERSOAKER) {
                        weaponState = WeaponState.HOSE;
                    } else if (weaponState == WeaponState.HOSE) {
                        weaponState = WeaponState.REGWEAPON;
                    } else if (weaponState == WeaponState.RAYGUN) {
                        weaponState = WeaponState.HOSE;
                    } else if (weaponState == WeaponState.HYDRARAYGUN) {
                        weaponState = WeaponState.HOSE;
                    }
                }
            }
            if (raygun.enable) {
                if (raygunbutton.pressable() && raygunbutton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {
                        weaponState = WeaponState.RAYGUN;
                    } //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {
                        weaponState = WeaponState.RAYGUN;
                    } //switch to regular gun
                    else if (weaponState == WeaponState.SUPERSOAKER) {
                        weaponState = WeaponState.RAYGUN;
                    } else if (weaponState == WeaponState.HOSE) {
                        weaponState = WeaponState.RAYGUN;
                    } else if (weaponState == WeaponState.RAYGUN) {
                        weaponState = WeaponState.REGWEAPON;
                    } else if (weaponState == WeaponState.HYDRARAYGUN) {
                        weaponState = WeaponState.RAYGUN;
                    }
                }
            }
            if (hydraraygun.enable) {
                if (hydraraygunbutton.pressable() && hydraraygunbutton.isPressed()) {
                    if (weaponState == WeaponState.REGWEAPON) {
                        weaponState = WeaponState.HYDRARAYGUN;
                    } //switch to hydra
                    else if (weaponState == WeaponState.HYDRA) {
                        weaponState = WeaponState.HYDRARAYGUN;
                    } //switch to regular gun
                    else if (weaponState == WeaponState.SUPERSOAKER) {
                        weaponState = WeaponState.HYDRARAYGUN;
                    } else if (weaponState == WeaponState.HOSE) {
                        weaponState = WeaponState.HYDRARAYGUN;
                    } else if (weaponState == WeaponState.RAYGUN) {
                        weaponState = WeaponState.HYDRARAYGUN;
                    } else if (weaponState == WeaponState.HYDRARAYGUN) {
                        weaponState = WeaponState.REGWEAPON;
                    }
                }
            }
            if (weaponState == WeaponState.REGWEAPON) {
                if (waterGun.enable) {waterGun.Update(water);}
            }
            if (waterGun.enable) {
                waterGun.Update(water);
            }
            else if (weaponState == WeaponState.HYDRA) {
                hydra.Update(water);
            }
            else if (weaponState == weaponState.HOSE) {
                hose.Update(water);
            }
            else if (weaponState == WeaponState.RAYGUN) {
                raygun.Update(water);
            }
            else if (weaponState == weaponState.HYDRARAYGUN) {
                hydraraygun.Update(water);
            }


            for (int i = 0; i < water.size(); i++) { //projectiles
                ThrowyThingy proj = water.get(i);
                proj.Update();
                if (proj.bound.y >= height) {
                    water.remove(i);
                    }
                    if (proj.bound.y < -proj.bound.getHeight()) {
                            water.remove(i);
                        }
                        boolean projectileHit = false;
                        for (int a = 0; a < enemies.size(); a++) {
                            if (proj.bound.overlaps(enemies.get(a).bound)) {
                                projectileHit = true;
                                enemies.get(a).hp = enemies.get(a).hp - Weapon.str;
                                if (enemies.get(a).hp <= 0) {
                                    shell.sprite.setPosition(enemies.get(a).bound.x, enemies.get(a).bound.y);
                                    enemies.remove(a);
                                    snaildead = true;
                                    currency += 5;
                                }
                            }
                        }
                        if (projectileHit) {
                            water.remove(i);
                        }
                    }
                    for (Enemy enemy : enemies) {
                        enemy.Update(deltaTime, this);
                        for (BombDrop bomb : bombs) {
                            if (enemy.bound.overlaps(bomb.bound) && !(enemy instanceof FlyingSnail)) {
                                enemy.speed.x = enemy.speed.x + 2;
                                bombs.remove(bomb);
                            }
                        }
                        for (Droppings droppies : droppings) {
                            if (enemy.bound.overlaps(droppies.bound) && !(enemy instanceof AcidSnail)) {
                                enemy.speed.x++;
                                droppings.remove(droppies);
                            }
                            if (enemy.bound.overlaps(House.Housebounds))
                                House.hp -= enemy.Attack * Gdx.graphics.getDeltaTime();
                        }
                    }
                    if (House.hp <= 0 || loseButton.isPressed()) {
                        gameState = GameState.GAMEOVER;
                    }
                }
        /*
        *** game over currently contains ***
         - disposes enemies arraylist
         - game over --> main menu
        */
                else if (gameState == GameState.GAMEOVER) { //in game over
                    if (backButtonGameOver.pressable() && backButtonGameOver.isPressed()) {
                        backButtonGameOver.pressedAction(); //go to main menu
                        enemies.clear();
                        water.clear();
                        bombs.clear();
                        droppings.clear();
                        House.hp = House.maxHP;
                        Weapon.waterLimit = Weapon.waterSupply;
                        weaponState = WeaponState.REGWEAPON;
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
            creditsButton.draw(batch);
            batch.draw(shopButtonMenu.image, shopButtonMenu.position.x, shopButtonMenu.position.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
            batch.end();
        } else if (gameState == GameState.CREDITS) {
            batch.begin();
            batch.draw(levelscreen, 0, 0);
            batch.draw(text, width / 2 - 305, 100);
            batch.end();

        } else if (gameState == GameState.CREDITShurshal) {
            batch.begin();
            batch.draw(levelscreen, 0, 0);
            batch.draw(hurshal, width / 2 - 390, height / 2 - 100);
            backButtonCredits.draw(batch);
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

            if (UpgradeWall2Button.remove = false) {
                batch.draw(upgradewall2button.sprite, height / 2, width / 3);
            }
            if (UpgradeWall3Button.remove = false) {
                batch.draw(upgradewall3button.sprite, height / 2, width / 3);
            }
            if (UpgradeWall4Button.remove = false) {
                batch.draw(upgradewall4button.sprite, height / 2, width / 3);
            }
            if (UpgradeWall5Button.remove = false) {
                batch.draw(upgradewall5button.sprite, height / 2, width / 3);
            }
            if (UpgradeWall6Button.remove = false) {
                batch.draw(upgradewall6button.sprite, height / 2, width / 3);
            }
            if (UpgradeWall7Button.remove = false) {
                batch.draw(upgradewall7button.sprite, height / 2, width / 3);
            }
            batch.draw(hydraraygunshopbutton.sprite, height / 4, width / 7);
            batch.draw(spHydryBut.sprite, height / 2, width / 2);
            batch.draw(supersoakershopbutton.sprite, height - 100, width / 2);
            batch.draw(hoseshopbutton.sprite, height / 4, width - 100);
            batch.draw(raygunshopbutton.sprite, height / 4, width - 600);

            font.draw(batch, "snail shells:" + currency, width / 2, height / 5);
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
                batch.draw(jimmy.sprite, 0, 0);
                if (weaponState == WeaponState.REGWEAPON) {
                    waterGun.sprite.draw(batch);
                }
                if (weaponState == WeaponState.HYDRA) {
                    hydra.sprite.draw(batch);
                }
                if (weaponState == WeaponState.SUPERSOAKER) {
                    supersoaker.sprite.draw(batch);
                }
                if (weaponState == WeaponState.HOSE){
                    hose.sprite.draw(batch);
                }
                if (weaponState == WeaponState.RAYGUN) {
                    raygun.sprite.draw(batch);
                }
                if (weaponState == WeaponState.HYDRARAYGUN) {
                    hydraraygun.sprite.draw(batch);
                }
                batch.draw(hydraButton.image, hydraButton.position.x, hydraButton.position.y);
                batch.draw(supersoakerbutton.image, supersoakerbutton.position.x, supersoakerbutton.position.y);
                batch.draw(hosebutton.image, hosebutton.position.x, hosebutton.position.y);
                batch.draw(raygunbutton.image, raygunbutton.position.x, raygunbutton.position.y);
                batch.draw(hydraraygunbutton.image, hydraraygunbutton.position.x, hydraraygunbutton.position.y);
                shell.sprite.draw(batch);
                for (ThrowyThingy proj : water) {
                    proj.sprite.draw(batch);
                }
                for (Droppings droppies : droppings) {
                    droppies.draw(batch);
                }
                for (BombDrop bomb : bombs) {
                    bomb.draw(batch);
                }
                for (Enemy enemy : enemies) { //draws and animates enemies
                    enemy.draw(batch, time);
                    if (enemy.hp <= 0) {
                        shell.sprite.draw(batch);
                    }
                }
                font.draw(batch, "Current level: " + currentLevel.getLevelNumber(), 10, 90);
                font.draw(batch, "Number of snails: " + enemies.size(), 10, 50);
                font.draw(batch, "Current state: in-game", 10, height - 50);
                font.draw(batch, "Water Amount: " + Weapon.waterLimit, 10, height - 100);
                font.draw(batch, "Snailshells: " + currency, 10, height - 200);
                font.draw(batch, "HP: " + (int) House.hp, 10, height - 400);
                batch.end();
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(3, 50, House.hp * House.healthScale, 30);
                shapeRenderer.setColor(Color.BLUE);
                shapeRenderer.rect(3, 70, Weapon.waterLimit * Weapon.waterScale, 40);
                shapeRenderer.end();
            }
        /* game over screen currently contains ***
         - back button
        */
            else if (gameState == GameState.GAMEOVER) { //in game over
                batch.begin();
                batch.draw(gameOverBackground, 0, 0);
                backButtonGameOver.draw(batch);
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