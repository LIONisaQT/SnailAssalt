package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by ryanisaqt on 8/25/14.
 */
public class Tutorial extends GameStates {
    protected ArrayList<Sprite> tutorials;
    private int buttonstate = 0; //this may be able to be moved

    protected NextButton nextButton;
    protected PreviousButton previousButton;
    protected BackButton backButton;

    protected static enum TutorialState {PAGE1, PAGE2, PAGE3, PAGE4, PAGE5, PAGE6, PAGE7, PAGE8, PAGE9}
    protected static TutorialState tutorialState;
    public Tutorial() {/*no background trololololol*/}

    public void create() {
        tutorialState = TutorialState.PAGE1;
        tutorials = new ArrayList<Sprite>();
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial1.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial2.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial3.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial4.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial5.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial6.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial7.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial8.jpeg")));
        tutorials.add(new Sprite(new Texture("images/tutorials/tutorial9.jpeg")));
        for (Sprite tutor : tutorials) {
            tutor.setSize(width, height);
            tutor.setPosition(0, 0);
        }

        BackButton tempBackButton = new BackButton(0, 0);
        backButton = new BackButton(0, height - tempBackButton.buttonGetHeight());

        NextButton tempNextButton = new NextButton(0, 0);
        nextButton = new NextButton(width - tempNextButton.buttonGetWidth(), height - tempNextButton.buttonGetHeight());

        PreviousButton tempPreviousButton = new PreviousButton(0, 0);
        previousButton = new PreviousButton(0, height - tempPreviousButton.buttonGetHeight());
    }

    public void update() {
        if (tutorialState == TutorialState.PAGE9) {
            if (backButton.isPressed()) {
                buttonstate = 1;
            }
            if (backButton.touchup() && buttonstate == 1) {
                backButton.pressedAction();
            }
            if (nextButton.isPressed()) {
                buttonstate = 1;
            }
            if (nextButton.touchup() && buttonstate == 1) {
                nextButton.pressedAction();
            }
        } else {
            if (nextButton.isPressed()) {
                buttonstate = 1;
            }
            if (nextButton.touchup() && buttonstate == 1) {
                nextButton.pressedAction();
                buttonstate = 0;
            }
            if (previousButton.isPressed()) {
                buttonstate = 1;
            }
            if (previousButton.touchup() && buttonstate == 1) {
                previousButton.pressedAction();
                buttonstate = 0;
            }
        }
    }

    public void draw() {
        batch.begin();
        if (tutorialState == TutorialState.PAGE1) {
            tutorials.get(0).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE2) {
            tutorials.get(1).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE3) {
            tutorials.get(2).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE4) {
            tutorials.get(3).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE5) {
            tutorials.get(4).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE6) {
            tutorials.get(5).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE7) {
            tutorials.get(6).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE8) {
            tutorials.get(7).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if(previousButton.isPressed()){
                previousButton.spriteShade.draw(batch);
            }
        } else if (tutorialState == TutorialState.PAGE9) {
            tutorials.get(8).draw(batch);
            nextButton.sprite.draw(batch);
            if (nextButton.isPressed()) {
                nextButton.spriteShade.draw(batch);
            }
            previousButton.sprite.draw(batch);
            if (previousButton.isPressed()) {
                previousButton.spriteShade.draw(batch);
            }
        }
        batch.end();
    }
}
