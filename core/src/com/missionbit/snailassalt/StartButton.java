package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;

/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class StartButton extends Button {
    public StartButton(float x, float y) {
        super(x, y, "start.png", "bw start.png", "bw start.png");
    }

    @Override
    public boolean touchup() {
        if (!Gdx.input.isTouched() && this.bound.contains(SnailAssalt.getTapPosition().x, SnailAssalt.getTapPosition().y) && buttonstatus == 1) {
            this.pressedAction();
            buttonstatus = 0;
            return true;
        } else {
            return false;
        }
    }

    public void pressedAction() {
        if (SnailAssalt.preferences.getInteger("tutorial", 0) == 0) {
            SnailAssalt.gameState = SnailAssalt.GameState.TUTORIAL;
        }
        if (SnailAssalt.preferences.getInteger("tutorial", 0) == 2) {
            SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;
        }
    }
}

