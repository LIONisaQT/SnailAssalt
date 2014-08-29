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
            if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE1) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE2;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE2) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE3;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE3) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE4;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE4) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE5;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE5) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE6;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE6) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE7;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE7) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE8;}
            else if (Tutorial.tutorialState == Tutorial.TutorialState.PAGE8) {Tutorial.tutorialState = Tutorial.TutorialState.PAGE9;}
            else {
                if (SnailAssalt.preferences.getInteger("tutorial", 0) == 0) {
                    SnailAssalt.preferences.putInteger("tutorial", 1);
                }
                if (SnailAssalt.preferences.getInteger("tutorial", 0) == 2) {
                    SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
                }
                Tutorial.tutorialState = Tutorial.TutorialState.PAGE1;
                if (SnailAssalt.preferences.getInteger("tutorial", 0) == 1) {
                    SnailAssalt.gameState = SnailAssalt.GameState.CHARACTERSELECT;
                    if (SnailAssalt.gameState == SnailAssalt.GameState.CHARACTERSELECT) {
                        SnailAssalt.preferences.putInteger("tutorial", 2);
                    }
                }
            }
        }
        if (SnailAssalt.gameState == SnailAssalt.GameState.INFO) {
            if (SnailInfo.infoState == SnailInfo.InfoState.STANDARD) {
                SnailInfo.infoState = SnailInfo.InfoState.ACID;
                SnailInfo.enemies.clear();
                SnailInfo.enemies.add(new AcidSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailInfo.infoState == SnailInfo.InfoState.ACID) {
                SnailInfo.infoState = SnailInfo.InfoState.FLYING;
                SnailInfo.enemies.clear();
                SnailInfo.enemies.add(new FlyingSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailInfo.infoState == SnailInfo.InfoState.FLYING) {
                SnailInfo.infoState = SnailInfo.InfoState.HEALING;
                SnailInfo.enemies.clear();
                SnailInfo.enemies.add(new HealerSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailInfo.infoState == SnailInfo.InfoState.HEALING) {
                SnailInfo.infoState = SnailInfo.InfoState.BOSS;
                SnailInfo.enemies.clear();
                SnailInfo.enemies.add(new BossSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailInfo.infoState == SnailInfo.InfoState.BOSS) {
                SnailInfo.infoState = SnailInfo.InfoState.MOTHER;
                SnailInfo.enemies.clear();
                SnailInfo.enemies.add(new MotherSnail(width / 11, height / 5, 0, 0, 0, 0));
            } else if (SnailInfo.infoState == SnailInfo.InfoState.MOTHER) {
                SnailInfo.infoState = SnailInfo.InfoState.PERSON;
                SnailInfo.enemies.clear();
                SnailInfo.enemies.add(new Zombie(width / 11, height / 5, 0, 0, 0, 0));
            }
        }
    }
}
