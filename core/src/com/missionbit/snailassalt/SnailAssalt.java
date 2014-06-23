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
import com.badlogic.gdx.math.Vector3;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int width;
    private int height;
    private Vector3 temp;
    private Texture background;
    private BitmapFont font;
    //buttons start
    private StartButton startButtonMenu;
    private ShopButton shopButtonMenu;
    private BackButton backButtonShop, backButtonGameOver, backButtonLevelSelect; //different back buttons because their position will most likely be different
    private LevelSelectButton levelSelectButton;
    private LoseButton loseButton;
    //buttons end
    private Player jimmy;
    //enemies start
    private Enemy standardSnail;
    private Texture frame1;
    private Texture frame2;
    private Animation snail;
    float time = 0;
    private Enemy acidSnail;
    private Texture frame3;
    private Texture frame4;
    private Animation acidsnail;
    private Enemy flyingSnail;
    private Texture frame5;
    private Texture frame6;
    private Animation flyingsnail;
    private Enemy healerSnail;
    private Texture frame7;
    private Texture frame8;
    private Animation healsnail;
    private Enemy motherSnail;
    private Enemy people;
    private Enemy boss;
    //enemies end
    private Texture house;
    private Texture houseBroken;
    private Texture houseGameOver;
    private int houseHp;
    // game states -- 1 int to hold current game state, 5 ints to hold MAIN game states
    private int gameState, stateMainMenu, stateInGame, stateGameOver, stateShop, stateLevelSelect;
    //levels -- 1 String to hold current level, [int] Strings to hold levels
    //private ArrayList<Levels> levels;
    private int currentLevel;
    //private String currentLevel, level1, level2;
    private Texture balloon;
	@Override
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
        balloon = new Texture("balloon icon.png");
        //game states start
        stateMainMenu = 0;
        stateInGame = 1;
        stateGameOver = 2;
        stateShop = 3;
        stateLevelSelect = 4;
        gameState = stateMainMenu;
        //game states end
        //levels start
        //nothing here yet because coding is too hard :c
        //levels end
        temp = new Vector3();
        //buttons start
        startButtonMenu = new StartButton(width/2-250, height/2-200);
        shopButtonMenu = new ShopButton(10, startButtonMenu.buttonGetHeight() + 20);
        backButtonGameOver = new BackButton(width - 210, 10);
        backButtonShop = new BackButton(width - 210, 10);
        backButtonLevelSelect = new BackButton(width - 210, 10);
        levelSelectButton = new LevelSelectButton(10, height - 410);
        loseButton = new LoseButton(width - 210, height - 210);
        //buttons end
        jimmy = new Player();
        //enemies start
        standardSnail = new Enemy();
        frame1 = new Texture("snail.png");
        frame2 = new Texture("standardsnail2.png");
        snail = new Animation(0.7f, new TextureRegion(frame1), new TextureRegion(frame2));
        snail.setPlayMode(Animation.PlayMode.LOOP);
        acidSnail = new Enemy();
        frame3= new Texture("acid snail.png");
        frame4= new Texture("acid snail2.png");
        acidsnail= new Animation(0.4f,new TextureRegion(frame3), new TextureRegion(frame4));
        acidsnail.setPlayMode(Animation.PlayMode.LOOP);
        flyingSnail = new Enemy();
        frame5= new Texture("flying snail.png");
        frame6= new Texture("flying snail2.png");
        flyingsnail=new Animation(0.25f, new TextureRegion(frame5), new TextureRegion(frame6));
        flyingsnail.setPlayMode(Animation.PlayMode.LOOP);
        healerSnail = new Enemy();
        frame7= new Texture("healsnail.png");
        frame8=new Texture("healsnail2.png");
        healsnail=new Animation(0.6f,new TextureRegion(frame7),new TextureRegion(frame8));
        healsnail.setPlayMode(Animation.PlayMode.LOOP);
        motherSnail = new Enemy();
        people = new Enemy();
        boss = new Enemy();
        house = new Texture("house.png");
        houseBroken=new Texture("housebroken.png");
        houseGameOver=new Texture("housegameover.png");
        //enemies end
        resetGame();
    }
    public void resetGame(){
        camera.position.set(width/2, height/2, 0);
        gameState = stateMainMenu;
        //buttons start
        startButtonMenu.buttonPosition.set(startButtonMenu.getXPos(), startButtonMenu.getYPos());
        shopButtonMenu.buttonPosition.set(shopButtonMenu.getXPos(), shopButtonMenu.getYPos());
        backButtonGameOver.buttonPosition.set(backButtonGameOver.getXPos(), backButtonGameOver.getYPos());
        backButtonLevelSelect.buttonPosition.set(backButtonLevelSelect.getXPos(), backButtonLevelSelect.getYPos());
        backButtonShop.buttonPosition.set(backButtonShop.getXPos(), backButtonShop.getYPos());
        levelSelectButton.buttonPosition.set(levelSelectButton.getXPos(), levelSelectButton.getYPos());
        loseButton.buttonPosition.set(loseButton.getXPos(), loseButton.getYPos());
        //buttons end
        //enemies start
        standardSnail.standardSnailBound.x = 400;
        standardSnail.standardSnailBound.y = 400;
        standardSnail.hp = 10;
        acidSnail.acidSnailBound.x = 600;
        acidSnail.acidSnailBound.y = 600;
        acidSnail.hp = 40;
        flyingSnail.flyingSnailBound.x = 200;
        flyingSnail.flyingSnailBound.y = 400;
        flyingSnail.hp = 10;
        healerSnail.healerSnailBound.x = 400;
        healerSnail.healerSnailBound.y = 600;
        healerSnail.hp = 20;
        motherSnail.motherSnailBound.x = 0;
        motherSnail.motherSnailBound.y = 0;
        motherSnail.hp = 30;
        people.peopleBound.x = 0;
        people.peopleBound.y = 0;
        people.hp = 40;
        boss.bossBound.x = 0;
        boss.bossBound.y = 0;
        boss.hp = 100;
        houseHp = 50;

        //enemies end
    }
    public Vector3 getTapPosition() {
        temp.set(Gdx.input.getX(), Gdx.input.getY(),0);
        return camera.unproject(temp);
    }
    public void updateGame(){
        float deltaTime = Gdx.graphics.getDeltaTime();
        time += deltaTime;
        if (gameState == stateMainMenu) {
            if (Gdx.input.justTouched() && startButtonMenu.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateLevelSelect;
            if (Gdx.input.justTouched() && shopButtonMenu.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateShop;
        }
        else if (gameState == stateLevelSelect) {
            if (Gdx.input.justTouched() && levelSelectButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateInGame;
            if (Gdx.input.justTouched() && backButtonLevelSelect.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu;
        }
        else if (gameState == stateShop) {
            if (Gdx.input.justTouched() && backButtonShop.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu;
        }
        else if (gameState == stateInGame) {
            if (Gdx.input.justTouched() && loseButton.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateGameOver;
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY()))
                standardSnail.standardSnailBound.x = 400;
            if(houseHp==0){
                gameState=stateGameOver;
            }
            //some code here to determine loss condition
        }
        else if (gameState == stateGameOver) {
            if (Gdx.input.justTouched() && backButtonGameOver.buttonBound.contains(getTapPosition().x, getTapPosition().y))
                gameState = stateMainMenu;
        }
    }
    public void drawGame(){
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setScale(2);
        font.setColor(0, 0, 0, 1);
        if (gameState == stateMainMenu) {
            batch.draw(background, 0, 0);
            batch.draw(startButtonMenu.buttonImage, startButtonMenu.buttonPosition.x, startButtonMenu.buttonPosition.y);
            batch.draw(shopButtonMenu.buttonImage, shopButtonMenu.buttonPosition.x, shopButtonMenu.buttonPosition.y);
            font.draw(batch, "Current state: main menu", 10, height - 50);
        }
        else if (gameState == stateLevelSelect) {
            batch.draw(levelSelectButton.buttonImage, levelSelectButton.buttonPosition.x, levelSelectButton.buttonPosition.y);
            batch.draw(backButtonLevelSelect.buttonImage, backButtonLevelSelect.buttonPosition.x, backButtonLevelSelect.buttonPosition.y);
            font.draw(batch, "Current state: level select", 10, height - 50);
        }
        else if (gameState == stateShop) {
            batch.draw(backButtonShop.buttonImage, backButtonShop.buttonPosition.x, backButtonShop.buttonPosition.y);
            font.draw(batch, "Current state: shop", 10, height - 50);
        }
        else if (gameState == stateInGame) {
            batch.draw(balloon,width/2,height/2);
            batch.draw(loseButton.buttonImage, loseButton.buttonPosition.x, loseButton.buttonPosition.y);
            batch.draw(jimmy.jimmy, 0, 0);
            batch.draw(snail.getKeyFrame(time), standardSnail.standardSnailBound.x, standardSnail.standardSnailBound.y);
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                batch.draw(standardSnail.standardSnail, standardSnail.standardSnailBound.x, standardSnail.standardSnailBound.y);
            }
            batch.draw(acidsnail.getKeyFrame(time),acidSnail.acidSnailBound.x,acidSnail.acidSnailBound.y);
            if (acidSnail.acidSnailBound.contains(Gdx.input.getX(), Gdx.input.getY())){
                batch.draw(acidSnail.acidSnail, acidSnail.acidSnailBound.x, acidSnail.acidSnailBound.y);
            }
            batch.draw(flyingsnail.getKeyFrame(time),flyingSnail.flyingSnailBound.x,flyingSnail.flyingSnailBound.y);
            if(flyingSnail.flyingSnailBound.contains(Gdx.input.getX(),Gdx.input.getY())){
               batch.draw(flyingSnail.flyingSnail, flyingSnail.flyingSnailBound.x,flyingSnail.flyingSnailBound.y);
            }
            batch.draw(healsnail.getKeyFrame(time),healerSnail.healerSnailBound.x,healerSnail.healerSnailBound.y);
            if(standardSnail.standardSnailBound.contains(Gdx.input.getX(),Gdx.input.getY())){
                batch.draw(healerSnail.healerSnail,healerSnail.healerSnailBound.x,healerSnail.healerSnailBound.y);
            }
            if(houseHp>35){
                batch.draw(house,-300,0);
            }else if(houseHp<35& houseHp>10){
                batch.draw(houseBroken,-300,0);
            }else if(houseHp<10){
                batch.draw(houseGameOver,-300,0);
            }

            font.draw(batch, "Current state: in-game", 10, height - 50);
        }
        else if (gameState == stateGameOver) {
            batch.draw(backButtonGameOver.buttonImage, backButtonGameOver.buttonPosition.x, backButtonGameOver.buttonPosition.y);
            font.draw(batch, "Game Over", 10, 50);
            font.draw(batch, "Current state: game over", 10, height - 50);
        }
        font.draw(batch, "Resolution: " + width + ", " + height, 10, height);
        batch.end();
    }
}