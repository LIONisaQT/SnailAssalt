package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class QuitButton extends Button {
    public QuitButton(float x, float y) {
        super(x, y, "images/buttons/quitButton.png", "images/buttons/quitButton.png", "images/buttons/bw buttons/bw quitButton.png");
        sprite.setSize(3 * getWidth() / 2, 2 * getHeight() / 3);
        bound.setSize(3 * getWidth() / 2, 2 * getHeight() / 3);
    }

    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.GAMEOVER;}
}
