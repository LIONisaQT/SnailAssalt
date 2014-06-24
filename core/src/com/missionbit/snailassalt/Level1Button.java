package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class Level1Button extends Button {
    public Level1Button(float x, float y) {
        super(x, y);
        image = new Texture("level1.png");
    }
    public boolean isPressed() {return true;}
    public int numberOfEnemies() {
        return 5;
    }
}