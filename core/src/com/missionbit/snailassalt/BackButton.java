package com.missionbit.snailassalt;
/**
  Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {
        super(x, y, "back button.png", "levelselect.png");
    }
    public void pressedAction() {
        if (SnailAssalt.prevGameState == SnailAssalt.GameState.GAMEOVER || SnailAssalt.prevGameState == SnailAssalt.GameState.WIN) {
            SnailAssalt.gameState = SnailAssalt.prevGameState;
        }
        else if (SnailAssalt.gameState == SnailAssalt.GameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE4) {
                SnailAssalt.preferences.putInteger("tutorial", 1);
                SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE1;
            }
            if (SnailAssalt.preferences.getInteger("tutorial", 1) == 1) {
                SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;
            }
        }
        else if (SnailAssalt.gameState == SnailAssalt.GameState.GAMEOVER || SnailAssalt.gameState == SnailAssalt.GameState.WIN)
            SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;
        else
            SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
        SnailAssalt.prevGameState = null; //clear prevGameState
    }
}
