package com.missionbit.snailassalt;

/**
 * Created by douglas on 8/12/14.
 */
public class NextTutBut7 extends Button {
    protected NextTutBut7(float x, float y) {
        super(x, y, "next button.png", "next button.png");
    }

    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE7) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE8;
            }
        }
    }
}
