package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ryanisaqt on 8/24/14.
 */
public class MainMenu extends GameStates {
    protected StartButton startButton;
    protected CreditsButton creditsButton;
    protected ShopButton shopButton;
    protected InfoButton infoButton;
    protected TutorialButton tutorialButton;
    public MainMenu() {
        background = new Sprite(new Texture("images/backgrounds/sidewaysmenu.png"));
        background.setSize(width, height);
    }

    public void create() {
        startButton = new StartButton(width / 2 - width / (4.38f), height / 2 - height / (4.5f));
        infoButton = new InfoButton(startButton.getXPos(), startButton.getYPos() - startButton.buttonGetHeight() - 10);
        creditsButton = new CreditsButton(infoButton.getXPos() + infoButton.buttonGetWidth() + 20, infoButton.getYPos());
        tutorialButton = new TutorialButton(creditsButton.getXPos() + creditsButton.buttonGetWidth() + 20, creditsButton.getYPos());
        shopButton = new ShopButton(startButton.getXPos() + startButton.buttonGetWidth() + 10, startButton.getYPos());
    }

    public void update() {
        if (startButton.touchup()) {startButton.pressedAction();}
        if (shopButton.touchup()) {shopButton.pressedAction();}
        if (creditsButton.touchup()) {creditsButton.pressedAction();}
        if (tutorialButton.touchup()) {tutorialButton.pressedAction();}
        if (infoButton.touchup()) {infoButton.pressedAction();}
    }

    public void draw() {
        batch.begin();
        background.draw(batch);

        //BUTTONS
        startButton.sprite.draw(batch);
        creditsButton.sprite.draw(batch);
        shopButton.sprite.draw(batch);
        infoButton.sprite.draw(batch);
        tutorialButton.sprite.draw(batch);

        //PRESSED BUTTONS
        if (startButton.isPressed()) {
            startButton.spriteShade.draw(batch);
        }
        if (tutorialButton.isPressed()) {
            tutorialButton.spriteShade.draw(batch);
        }
        if (creditsButton.isPressed()) {
            creditsButton.spriteShade.draw(batch);
        }
        if (shopButton.isPressed()) {
            shopButton.spriteShade.draw(batch);
        }

        batch.end();
    }
}
