package com.missionbit.snailassalt;

/**
 * Created by linchen on 7/7/14.
 */
public class CreditsButton extends Button {
    public CreditsButton(float x, float y) {
        super(x, y, "creditsButton.png", "creditsButton.png", "bw creditsButton.png");
    }

    public void pressedAction() {
        SnailAssalt.gameState = SnailAssalt.GameState.CREDITS;
    }
}
