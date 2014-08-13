package com.missionbit.snailassalt;

/**
 * Created by douglas on 8/12/14.
 */
public class NextTutBut3 extends Button {
    protected NextTutBut3(float x, float y) {
        super(x, y, "next button.png", "next button.png");
    }

    public void pressedAction() {
        if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE3) {
            SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE4;
        }
    }
}
