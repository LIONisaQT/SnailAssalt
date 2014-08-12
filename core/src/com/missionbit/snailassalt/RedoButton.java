package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 7/7/14.
 */
public class RedoButton extends Button {
    public RedoButton(float x, float y) {
        super(x, y, "play again button.png", "try again button.png");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.INGAME;}
}