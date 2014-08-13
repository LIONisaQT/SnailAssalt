package com.missionbit.snailassalt;

/**
 * Created by douglas on 8/12/14.
 */
public class NextTutBut2 extends Button {
    public NextTutBut2(float x, float y) {
        super(x, y, "next button.png", "next button.png");
    }

    public void pressedAction() {
        if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE2) {
            SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE3;
        }
    }
}


