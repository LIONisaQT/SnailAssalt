package com.missionbit.snailassalt;

/**
 * Created by linchen on 8/7/14.
 */
public class PreviousButton extends Button {
    public PreviousButton(float x, float y) {
        super(x, y, "previous button.png", "previous button.png");}
    public void pressedAction(){
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE2) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE1;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE3) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE2;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE4) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE3;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE5) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE4;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE6) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE5;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE7) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE6;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE8) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE7;}
            else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE9) {SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE8;}
    }
}
}