package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by vivianlam on 6/25/14.
 */
public class House {
    public Texture house1, house2, house3;
    static public Rectangle Housebounds;
    static public float hp, maxHP, healthScale;
    public House() {
    house1 = new Texture("house.png");
    house2 = new Texture("housebroken.png");
    house3 = new Texture("housegameover.png");
    Housebounds = new Rectangle(900, 0, house1.getWidth(), house1.getHeight());
    maxHP = 200.0f;
    hp = maxHP;
    healthScale = (hp / maxHP) * 2;
}
    public void draw(SpriteBatch batch, float x, float y){
        if (hp >= 8) {batch.draw(house1, x, y);
        }
        else if (hp < 8) {
            batch.draw(house2, x, y);
        }
        else if (hp < 4) {
            batch.draw(house3, x, y);
        }
    }

}
