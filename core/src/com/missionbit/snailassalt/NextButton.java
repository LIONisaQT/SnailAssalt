package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 7/25/14.
 */
public class NextButton extends Button {
    public NextButton(float x, float y) {
        super(x, y, "images/buttons/nextButton.png", "images/buttons/nextButton.png", "images/buttons/bw buttons/bw nextButton.png");
    }

    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE1) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE2;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE2) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE3;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE3) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE4;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE4) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE5;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE5) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE6;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE6) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE7;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE7) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE8;}
            else if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE8) {SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE9;}
            else {SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;}
        }
        if (SnailAssalt.gameState == SnailAssalt.GameState.INFO) {
            if (SnailAssalt.infoState == SnailAssalt.InfoState.STANDARD) {
                SnailAssalt.infoState = SnailAssalt.InfoState.ACID;
                SnailAssalt.enemies.clear();
                SnailAssalt.enemies.add(new AcidSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailAssalt.infoState == SnailAssalt.InfoState.ACID) {
                SnailAssalt.infoState = SnailAssalt.InfoState.FLYING;
                SnailAssalt.enemies.clear();
                SnailAssalt.enemies.add(new FlyingSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailAssalt.infoState == SnailAssalt.InfoState.FLYING) {
                SnailAssalt.infoState = SnailAssalt.InfoState.HEALING;
                SnailAssalt.enemies.clear();
                SnailAssalt.enemies.add(new HealerSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailAssalt.infoState == SnailAssalt.InfoState.HEALING) {
                SnailAssalt.infoState = SnailAssalt.InfoState.BOSS;
                SnailAssalt.enemies.clear();
                SnailAssalt.enemies.add(new BossSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailAssalt.infoState == SnailAssalt.InfoState.BOSS) {
                SnailAssalt.infoState = SnailAssalt.InfoState.MOTHER;
                SnailAssalt.enemies.clear();
                SnailAssalt.enemies.add(new MotherSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailAssalt.infoState == SnailAssalt.InfoState.MOTHER) {
                SnailAssalt.infoState = SnailAssalt.InfoState.PERSON;
                SnailAssalt.enemies.clear();
                SnailAssalt.enemies.add(new Zombie(width / 11, height / 5, 0, 0, 0, 0));
            }
        }
    }
}
