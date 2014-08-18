package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 7/7/14.
 */
public class RedoButton extends Button {
    public RedoButton(float x, float y) {
        super(x, y, "images/buttons/play again button.png", "images/buttons/try again button.png", "images/buttons/bw buttons/bw play again button.png");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.INGAME;}
}