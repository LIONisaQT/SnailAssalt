package com.missionbit.snailassalt;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class LoseButton extends Button {
    public LoseButton(float x, float y) {
        super(x, y, "quit button.png", "quit button.png");
        sprite.setSize(3 * buttonGetWidth() / 2, 2 * buttonGetHeight() / 3);
        bound.setSize(3 * buttonGetWidth() / 2, 2 * buttonGetHeight() / 3);
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.GAMEOVER;}
}
