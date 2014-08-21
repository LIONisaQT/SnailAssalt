package com.missionbit.snailassalt;

/**
 * Created by linchen on 7/9/14.
 */
public class TutorialButton extends Button {
    public TutorialButton(float x, float y) {
        super(x, y, "images/buttons/tutorialButton.png", "images/buttons/tutorialButton.png", "images/buttons/bw buttons/bw tutorialButton.png");
    }
    public void pressedAction() {
        SnailAssalt.gameState = SnailAssalt.GameState.TUTORIAL;
    }
}
