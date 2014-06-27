package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by vivianlam on 6/25/14.
 */
public class House {
    public Texture house1;
    public Texture house2;
    public Texture house3;
    static public int hp;

    public House() {
    house1 = new Texture("house.png");
    house2 = new Texture("housebroken.png");
    house3 = new Texture("housegameover.png");
    hp=50;
}


    public void draw(SpriteBatch batch){
        if (hp>=35) {
            batch.draw(house1, 450, 0);
        } else if (hp<35 && hp>=10) {
            batch.draw(house2, 450, 0);
        }else if (hp<10) {
            batch.draw(house3, 450, 0);
        }
    }

}
