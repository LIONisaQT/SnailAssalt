package com.missionbit.snailassalt;

/**
 * Created by linchen on 7/7/14.
 */


public class CreditsButton extends Button {
    public CreditsButton(float x, float y) {
        super(x, y, "creditsButton", "creditsButton");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.CREDITS;}
}
