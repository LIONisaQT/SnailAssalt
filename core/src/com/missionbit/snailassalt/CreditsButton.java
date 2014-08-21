package com.missionbit.snailassalt;

/**
 * Created by linchen on 7/7/14.
 */
public class CreditsButton extends Button {
    public CreditsButton(float x, float y) {
        super(x, y, "images/buttons/creditsButton.png", "images/buttons/creditsButton.png", "images/buttons/bw buttons/bw creditsButton.png");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.CREDITS;}
}
