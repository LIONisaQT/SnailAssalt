package com.missionbit.snailassalt;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
public class Player {
        public Texture texture;
        public Sprite sprite;
        public Rectangle bound;
        public int currentWaterGun, squirtGun, superSoaker;

        public Player () {
            texture = new Texture("jimmy.png");
            sprite = new Sprite(texture,60,60, texture.getWidth(), texture.getHeight());
            bound = new Rectangle(60,60, sprite.getWidth(), sprite.getHeight());

            squirtGun = 0;
            superSoaker = 1;
            currentWaterGun = squirtGun;
        }
        public void playerPosition(float c,float d){
            bound.setX(c);
            bound.setY(d);
            sprite.setX(c);
            sprite.setY(d);
        }

    }

