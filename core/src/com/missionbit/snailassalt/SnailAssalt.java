package com.missionbit.snailassalt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnailAssalt extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private int width;
    private int height;
    private BitmapFont font;
    private Player jimmy;
    private Enemy standardSnail;
    private Enemy acidSnail;
    private Enemy flyingSnail;
    private Enemy healerSnail;
    private Enemy motherSnail;
    private Enemy people;
    private Enemy boss;
    private Texture house;
    private Texture houseBroken;
    private Texture houseGameOver;
    private int houseHp;
    private int gameState, stateMainMenu, stateInGame, stateGameOver;

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateGame();
        drawGame();
    }

    public void create() {
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        font = new BitmapFont(Gdx.files.internal("font.fnt.txt"), Gdx.files.internal("font.png"), false);
        stateMainMenu = 0;
        stateInGame = 1;
        stateGameOver = 2;
        gameState = stateMainMenu;
        jimmy = new Player();
        standardSnail = new Enemy();
        acidSnail = new Enemy();
        flyingSnail = new Enemy();
        healerSnail = new Enemy();
        motherSnail = new Enemy();
        people = new Enemy();
        boss = new Enemy();
        house = new Texture("house.png");
        houseBroken=new Texture("housebroken.png");
        houseGameOver=new Texture("housegameover.png");
        resetGame();
    }

    public void resetGame() {
        camera.position.set(width / 2, height / 2, 0);
        standardSnail.standardSnailBound.x = 400;
        standardSnail.standardSnailBound.y = 400;
        standardSnail.hp = 10;
        acidSnail.acidSnailBound.x = 0;
        acidSnail.acidSnailBound.y = 0;
        acidSnail.hp = 30;
        flyingSnail.flyingSnailBound.x = 0;
        flyingSnail.flyingSnailBound.y = 0;
        flyingSnail.hp = 10;
        healerSnail.healerSnailBound.x = 0;
        healerSnail.healerSnailBound.y = 0;
        healerSnail.hp = 20;
        motherSnail.motherSnailBound.x = 0;
        motherSnail.motherSnailBound.y = 0;
        motherSnail.hp = 30;
        people.peopleBound.x = 0;
        people.peopleBound.y = 0;
        people.hp = 20;
        boss.bossBound.x = 0;
        boss.bossBound.y = 0;
        boss.hp = 100;
        houseHp = 50;

    }

    public void updateGame() {
        if (gameState == stateMainMenu) {
            if (Gdx.input.justTouched())
                gameState = stateInGame;

        } else if (gameState == stateInGame) {
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                standardSnail.standardSnailBound.x = 400;
            }
            if (houseHp == 0) {
                gameState = stateGameOver;
            }
            //some code here to determine loss condition
        } else if (gameState == stateGameOver) {
            if (Gdx.input.justTouched())
                gameState = stateMainMenu;
        }
    }

    public void drawGame() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setScale(2);
        font.setColor(0,0,0,1);
        if (gameState == stateMainMenu) {
            font.draw(batch,"Tap to begin",50,camera.position.y-(height/4)-50);
        } else if (gameState == stateInGame) {
            batch.draw(jimmy.jimmy, 0,0);
            batch.draw(standardSnail.standardSnail, standardSnail.standardSnailBound.x, standardSnail.standardSnailBound.y);
            if (standardSnail.standardSnailBound.contains(Gdx.input.getX(), Gdx.input.getY())) {
                batch.draw(standardSnail.standardSnail, standardSnail.standardSnailBound.x, standardSnail.standardSnailBound.y);
            }
            if(houseHp>35){
                batch.draw(house,-150,0);

            }else if(houseHp<35& houseHp>0){
                batch.draw(houseBroken,-150,0);
            }
       } else if (gameState == stateGameOver || houseHp<=0) {
                font.draw(batch,"Game Over",50,camera.position.y-(height/4)-50);
                font.draw(batch,"Tap to restart",50,camera.position.y-(height/4)-50);
                batch.draw(houseGameOver,-200,0);

            }
            batch.end();
        }
    }
