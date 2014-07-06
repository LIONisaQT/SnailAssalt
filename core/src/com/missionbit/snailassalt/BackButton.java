package com.missionbit.snailassalt;
/**
  Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {super(x, y, "backButton.png", "backButtonNope.png");}
    public void pressedAction() {
        SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;
    }
}
