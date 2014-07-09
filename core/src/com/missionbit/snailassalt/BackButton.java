package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
  Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {
        super(x, y, "back button.png", "backButtonNope.png");
    }
    public void pressedAction() {
        if (SnailAssalt.prevGameState == SnailAssalt.GameState.GAMEOVER) {
            SnailAssalt.gameState = SnailAssalt.prevGameState; //go back to game over
        } else if (SnailAssalt.prevGameState == SnailAssalt.GameState.WIN) {
            SnailAssalt.gameState = SnailAssalt.prevGameState; //go back to win
        } else {
            SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
        }
        SnailAssalt.prevGameState = null; //clear prevGameState
    }
}
