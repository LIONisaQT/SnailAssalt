package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by vivianlam on 6/25/14.
 */
public class House {
    public Texture house1;
    public Texture house2;
    public Texture house3;
    static public Rectangle Housebounds;
    static public float hp;

    public House() {
    house1 = new Texture("house.png");
    house2 = new Texture("housebroken.png");
    house3 = new Texture("housegameover.png");
    Housebounds= new Rectangle(0,0,house1.getWidth(),house1.getHeight());
    hp=1000.0f;
}


    public void draw(SpriteBatch batch,float x,float y){
        if (hp>=35) {
            batch.draw(house1, x, y);
        } else if (hp<35 && hp>=10) {
            batch.draw(house2, x, y);
        }else if (hp<10) {
            batch.draw(house3, x, y);
        }
    }

}
