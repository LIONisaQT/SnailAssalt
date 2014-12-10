package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ryanisaqt on 8/29/14.
 */
public class GameOver extends GameStates {
    protected BackButton backButton;
    protected RedoButton redoButton;
    protected ShopButton shopButton;
    public GameOver() {
        background = new Sprite(new Texture("images/backgrounds/gameover.png"));
        background.setSize(width, height);
    }

    public void create() {
        backButton = new BackButton(0, 0);
        redoButton = new RedoButton(width / 2 - width / 6, height / 2 - width / 6);
        redoButton.sprite.setSize(redoButton.sprite.getWidth() + redoButton.sprite.getWidth() / 8, MainMenu.startButton.getHeight());
        redoButton.spriteNope.setSize(redoButton.sprite.getWidth() + redoButton.sprite.getWidth() / 8, MainMenu.startButton.getHeight());
    }

    public void update() {

    }

    public void draw() {

    }
}
