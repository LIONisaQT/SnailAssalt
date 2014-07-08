package com.missionbit.snailassalt;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class ShopButton extends Button {
    public ShopButton(float x, float y) {super(x, y,"shopButton.png", "shopButton.png");}
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.SHOP;}
}
