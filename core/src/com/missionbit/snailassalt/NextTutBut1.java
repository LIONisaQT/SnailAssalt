package com.missionbit.snailassalt;

/**
 * Created by douglas on 8/12/14.
 */
public class NextTutBut1 extends Button {
    protected NextTutBut1(float x, float y) {
        super(x, y, "next button.png", "next button.png");
    }

    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE1) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE2;
            }
        }
    }
}
