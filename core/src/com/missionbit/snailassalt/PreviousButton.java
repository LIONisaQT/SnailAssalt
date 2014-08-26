package com.missionbit.snailassalt;

/**
 * Created by linchen on 8/7/14.
 */
public class PreviousButton extends Button {
    public PreviousButton(float x, float y) {
        super(x, y, "images/buttons/previousButton.png", "images/buttons/previousButton.png", "images/buttons/previousButton.png");} //TODO: bw previousButton.png needed
    public void pressedAction(){
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE2) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE1;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE3) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE2;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE4) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE3;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE5) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE4;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE6) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE5;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE7) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE6;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE8) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE7;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE9) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE8;}
        }
    }
}

