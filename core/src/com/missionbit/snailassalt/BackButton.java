package com.missionbit.snailassalt;
/**
  Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {
        super(x, y, "back button.png", "backButtonNope.png");
    }
    public void pressedAction() {
        if (SnailAssalt.prevGameState == SnailAssalt.GameState.GAMEOVER) {SnailAssalt.gameState = SnailAssalt.prevGameState;}
        else if (SnailAssalt.prevGameState == SnailAssalt.GameState.WIN) {SnailAssalt.gameState = SnailAssalt.prevGameState;} else if (SnailAssalt.gameState == SnailAssalt.gameState.TUTORIAL) {
            if (SnailAssalt.tutState == SnailAssalt.TutorialState.PAGE4) {
                SnailAssalt.preferences.putInteger("tutorial", 1);
            }
            if (SnailAssalt.preferences.getInteger("tutorial", 1) == 1) {
                SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;

            }
        }

        else {
            SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
            SnailAssalt.tutState = SnailAssalt.TutorialState.PAGE1;
        }

        SnailAssalt.prevGameState = null; //clear prevGameState

    }
}
