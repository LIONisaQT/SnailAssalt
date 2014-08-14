package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;

/**
 * Created by ryansheeisaqt on 7/25/14.
 */
public class NextButton extends Button {
    public NextButton(float x, float y) {
        super(x, y, "next button.png", "next button.png", "bw next button.png");
    }
    public void pressedAction() {
        if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE1) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE2;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE2) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE3;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE3) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE4;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE4) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE5;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE5) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE6;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE6) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE7;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE7) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE8;
            } else if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE8) {
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE9;
            } else {
                SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
            }
        }
            if (SnailAssalt.gameState == SnailAssalt.GameState.INFO) {
                if (SnailAssalt.infoState == SnailAssalt.InfoState.STANDARD) {
                    SnailAssalt.infoState = SnailAssalt.InfoState.ACID;
                    SnailAssalt.enemies.clear();
                    SnailAssalt.enemies.add(new AcidSnail(width / 5, height / 2, 0, 0, 0, 0));
                } else if (SnailAssalt.infoState == SnailAssalt.InfoState.ACID) {
                    SnailAssalt.infoState = SnailAssalt.InfoState.FLYING;
                    SnailAssalt.enemies.clear();
                    SnailAssalt.enemies.add(new FlyingSnail(width / 5, height / 2, 0, 0, 0, 0));
                } else if (SnailAssalt.infoState == SnailAssalt.InfoState.FLYING) {
                    SnailAssalt.infoState = SnailAssalt.InfoState.HEALING;
                    SnailAssalt.enemies.clear();
                    SnailAssalt.enemies.add(new HealerSnail(width / 5, height / 2, 0, 0, 0, 0));
                } else if (SnailAssalt.infoState == SnailAssalt.InfoState.HEALING) {
                    SnailAssalt.infoState = SnailAssalt.InfoState.BOSS;
                    SnailAssalt.enemies.clear();
                    SnailAssalt.enemies.add(new BossSnail(width / 5, height / 2, 0, 0, 0, 0));
                } else if (SnailAssalt.infoState == SnailAssalt.InfoState.BOSS) {
                    SnailAssalt.infoState = SnailAssalt.InfoState.MOTHER;
                    SnailAssalt.enemies.clear();
                    SnailAssalt.enemies.add(new MotherSnail(width / 5, height / 2, 0, 0, 0, 0));
                } else if (SnailAssalt.infoState == SnailAssalt.InfoState.MOTHER) {
                    SnailAssalt.infoState = SnailAssalt.InfoState.PERSON;
                    SnailAssalt.enemies.clear();
                    SnailAssalt.enemies.add(new Person(width / 5, height / 2, 0, 0, 0, 0));
                }

            }


    }
}
