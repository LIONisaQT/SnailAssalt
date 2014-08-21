package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by vivianlam on 6/25/14.
 */
public class House {
    public Texture house1, house2, house3;
    private Sprite houseFull, houseHalf, houseZero;
    static public Rectangle Housebounds;
    static public float hp, maxHP, healthScale;
    protected float width, height;
    public House() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        house1 = new Texture("images/house/houseFull.png");
        houseFull = new Sprite(house1);
        houseFull.setSize((width / 1196) * house1.getWidth(), (int)height);
        houseFull.setPosition(width - (width / 1196) * house1.getWidth(), 0);

        house2 = new Texture("images/house/houseHalf.png");
        houseHalf = new Sprite(house2);
        houseHalf.setSize((width / 1196) * house2.getWidth(), (int)height);
        houseHalf.setPosition(width - (width / 1196) * house2.getWidth(), 0);

        house3 = new Texture("images/house/houseDead.png");
        houseZero = new Sprite(house3);
        houseZero.setSize((width / 1196) * house3.getWidth(), (int)height);
        houseZero.setPosition(width - (width / 1196) * house3.getWidth(), 0);

        Housebounds = new Rectangle(width - houseFull.getWidth(), 0, houseFull.getWidth(),houseFull.getHeight());
        maxHP = 200.0f;
        hp = maxHP;
        healthScale = (hp / maxHP) * 2;
    }

    public void draw(SpriteBatch batch){
        if (hp >= 2 * maxHP / 3) {houseFull.draw(batch);}
        else if (hp < 2 * maxHP / 3 && hp > maxHP / 3) {houseHalf.draw(batch);}
        else if (hp <= maxHP / 3) {houseZero.draw(batch);}
    }
}
