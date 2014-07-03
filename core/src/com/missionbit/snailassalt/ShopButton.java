package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class ShopButton extends Button {
    public ShopButton(float x, float y) {
        super(x, y, "shopButton.png");
    }
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.SHOP;}
}
