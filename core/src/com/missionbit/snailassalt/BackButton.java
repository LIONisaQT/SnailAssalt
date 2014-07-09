package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
  Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {
        super(x,y,"backButton.png");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.MAINMENU;}
}
