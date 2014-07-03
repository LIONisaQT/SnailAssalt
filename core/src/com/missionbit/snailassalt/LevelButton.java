package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class LevelButton extends Button {
    ArrayList<TextureRegion> levelButtonImages;
    public LevelButton(float x, float y) {
        super(x, y, "levels1-10.png");
        levelButtonImages = new ArrayList<TextureRegion>();
        for (int a = 0; a < SnailAssalt.numberOfLevels; a++) {
            if (a < 5) {levelButtonImages.add(new TextureRegion(image, a * 200, 0, 200, 200));}
            else if (a >= 5) {levelButtonImages.add(new TextureRegion(image, a * 200, 200, 200, 200));}
        }
    }
    public float buttonGetWidth() {return 200;}
    public float buttonGetHeight() {return 200;}
    public TextureRegion getButtonImage(int level) {return levelButtonImages.get(level - 1);}
    public void pressedAction() {SnailAssalt.gameState = SnailAssalt.GameState.INGAME;}
}