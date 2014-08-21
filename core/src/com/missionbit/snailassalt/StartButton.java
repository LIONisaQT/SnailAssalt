package com.missionbit.snailassalt;

/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class StartButton extends Button {
    public StartButton(float x, float y) {
        super(x, y, "images/buttons/start.png", "images/buttons/bw buttons/bw start.png", "images/buttons/bw buttons/bw start.png");
    }

    public void pressedAction() {
        if (SnailAssalt.preferences.getInteger("tutorial", 0) == 0) {SnailAssalt.gameState = SnailAssalt.GameState.TUTORIAL;}
        if (SnailAssalt.preferences.getInteger("tutorial", 0) == 2) {SnailAssalt.gameState = SnailAssalt.GameState.CHARACTERSELECT;}
    }
}

