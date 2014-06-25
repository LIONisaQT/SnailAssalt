package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/**
 * Created by ryansheeisaqt on 6/18/14.
 */
public class Button {
    public Texture image;
    public Rectangle bound;
    public Vector2 position;
    public Button() {
        image = new Texture("badlogic.jpg");
        position = new Vector2();
    }
    public void draw(SpriteBatch batch) {
        batch.draw(this.image, this.position.x, this.position.y);
    }
}