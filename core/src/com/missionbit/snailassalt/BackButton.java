package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {
        super(x, y, "images/buttons/backButton.png", "images/buttons/levelselect.png", "images/buttons/bw buttons/bw backButton.png");
    }

    public void pressedAction() {
        if (SnailAssalt.prevGameState == SnailAssalt.GameState.GAMEOVER || SnailAssalt.prevGameState == SnailAssalt.GameState.WIN) {
            SnailAssalt.gameState = SnailAssalt.prevGameState;
        } else if (SnailAssalt.gameState == SnailAssalt.GameState.WIN || SnailAssalt.gameState == SnailAssalt.GameState.GAMEOVER) {
            SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;
        } else if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutorialState == SnailAssalt.TutorialState.PAGE9) {
                if (SnailAssalt.preferences.getInteger("tutorial", 0) == 0) {
                    SnailAssalt.preferences.putInteger("tutorial", 1);
                }
                if (SnailAssalt.preferences.getInteger("tutorial", 0) == 2) {
                    SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
                }
                SnailAssalt.tutorialState = SnailAssalt.TutorialState.PAGE1;
                if (SnailAssalt.preferences.getInteger("tutorial", 0) == 1) {
                    SnailAssalt.gameState = SnailAssalt.GameState.CHARACTERSELECT;
                    if (SnailAssalt.gameState == SnailAssalt.GameState.CHARACTERSELECT) {
                        SnailAssalt.preferences.putInteger("tutorial", 2);
                    }
                }
            }
        } else if (SnailAssalt.gameState == SnailAssalt.GameState.INFO){
            if(SnailAssalt.infoState== SnailAssalt.InfoState.STANDARD ||SnailAssalt.infoState== SnailAssalt.InfoState.ACID ||SnailAssalt.infoState== SnailAssalt.InfoState.FLYING ||SnailAssalt.infoState== SnailAssalt.InfoState.HEALING ||SnailAssalt.infoState== SnailAssalt.InfoState.MOTHER ||SnailAssalt.infoState== SnailAssalt.InfoState.PERSON ||SnailAssalt.infoState== SnailAssalt.InfoState.BOSS){
                SnailAssalt.infoState= SnailAssalt.InfoState.SELECTION;
            } else if (SnailAssalt.infoState==SnailAssalt.InfoState.SELECTION){
                SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
            }
        } else {
            SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
        }
        SnailAssalt.prevGameState = null; //clear prevGameState
    }
}
