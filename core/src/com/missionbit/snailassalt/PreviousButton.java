package com.missionbit.snailassalt;

/**
 * Created by linchen on 8/7/14.
 */
public class PreviousButton extends Button {
    public PreviousButton(float x, float y) {
        super(x, y, "images/buttons/previousButton.png", "images/buttons/previousButton.png", "images/buttons/previousButton.png");} //TODO: bw previousButton.png needed
    public void pressedAction(){
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE2) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE1;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE3) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE2;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE4) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE3;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE5) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE4;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE6) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE5;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE7) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE6;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE8) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE7;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE9) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE8;}
        }
    }
}

