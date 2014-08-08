package com.missionbit.snailassalt;

/**
 * Created by vivianlam on 8/5/14.
 */
public class InfoButton extends Button {
    public InfoButton(float x, float y) {
        super(x, y, "infoButton.png", "infoButton.png");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.INFO;}
}