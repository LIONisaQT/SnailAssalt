package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 7/25/14.
 */
public class NextButton extends Button {
    public NextButton(float x, float y) {
        super(x, y, "next button.png", "next button.png");
    }
    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE1) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE2;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE2) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE3;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE3) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE4;}
            else {
                SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
            }
        }
    }
}