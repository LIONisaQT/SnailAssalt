package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class BackButton extends Button {
    public BackButton(float x, float y) {
        super(x,y);
        image = new Texture("backButton.png");
    }
}
