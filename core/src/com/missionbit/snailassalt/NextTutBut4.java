package com.missionbit.snailassalt;

/**
 * Created by douglas on 8/12/14.
 */
public class NextTutBut4 extends Button {
    protected NextTutBut4(float x, float y) {
        super(x, y, "next button.png", "next button.png");
    }

    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE4) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE5;
            }
        }
    }
}

