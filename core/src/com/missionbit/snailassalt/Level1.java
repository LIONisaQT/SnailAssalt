package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/20/14.
 */
public class Level1 {
    private int enemyCount;
    private float width, height;
    public Level1(Level1Button l1b) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        enemyCount = l1b.numberOfEnemies();
    }
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int a = 0; a < enemyCount; a++) {
            enemies.add(new Enemy(0, a * (height/4), 10, 0));
        }
        return enemies;
    }
}
