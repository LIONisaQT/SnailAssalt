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
        }  else if (SnailAssalt.gameState == SnailAssalt.GameState.INFO){
            if (SnailInfo.infoState == SnailInfo.InfoState.SELECTION) {
                SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
            }
            else {
                SnailInfo.infoState = SnailInfo.InfoState.SELECTION;
            }
        } else {
            SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
        }
        SnailAssalt.prevGameState = null; //clear prevGameState
    }
}
