package com.missionbit.snailassalt;

/**
 * Created by douglas on 8/12/14.
 */
public class NextTutBut6 extends Button {
    protected NextTutBut6(float x, float y) {
        super(x, y, "weapon icon.png", "weapon icon.png");
    }

    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE6) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE7;
            }
        }
    }
}
