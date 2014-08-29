package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by ryanisaqt on 8/26/14.
 */
public class LevelSelect extends GameStates {
    private int buttonstate = 0; //this may be able to be moved
    protected static int numberOfLevels = 10;
    protected static int currentLevelNumber = 0;

    protected ArrayList<LevelButton> levelButtons;
    protected static ArrayList<Level> levels;
    protected static  ArrayList<Enemy> enemies;
    protected static Level currentLevel;

    protected BackButton backButton;
    public LevelSelect(SnailAssalt game) {
        super(game);
        background = new Sprite(new Texture("images/backgrounds/levelscreen.png"));
        background.setSize(width, height);
    }

    public void create() {
        levelButtons = new ArrayList<LevelButton>();
        levels = new ArrayList<Level>();
        enemies = new ArrayList<Enemy>();

        for (int a = 0; a < numberOfLevels; a++) {
            if (a < 5)
                levelButtons.add(new LevelButton(a * 210, 200)); //first row
            else
                levelButtons.add(new LevelButton((a - 5) * 210, 0)); //second row
            levels.add(new Level(a + 1));
        }
        currentLevel = new Level(0);

        BackButton tempBackButton = new BackButton(0, 0);
        backButton = new BackButton(width - tempBackButton.getWidth(), 0);
    }

    public void update() {
        for (int a = 0; a < numberOfLevels; a++) {
            if (levelButtons.get(a).isPressed()) {
                currentLevelNumber = a;
                currentLevel = levels.get(currentLevelNumber); //current level is now whatever level that was pressed
                enemies = currentLevel.getEnemies(); //enemies arraylist now holds level's enemies
                currentLevel.enemyCount = 0;
                levelButtons.get(a).pressedAction();
            }
        }
        if (backButton.isPressed()) {
            buttonstate = 1;
        }
        if (backButton.touchup() && buttonstate == 1) {
            backButton.pressedAction();
            buttonstate = 0;
        }
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        for (int a = 0; a < numberOfLevels; a++) {
            LevelButton lb = levelButtons.get(a);
            batch.draw(lb.getButtonImage(a + 1), lb.bound.x, lb.bound.y);
        }
        backButton.sprite.draw(batch);
        if (backButton.isPressed()) {
            backButton.spriteShade.draw(batch);
        }
        batch.end();
    }
}
