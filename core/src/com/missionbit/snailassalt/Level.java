package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/20/14.
 */
public class Level {
    private int enemyCount, level;
    private float width, height;
    public Level(int lvl) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        level = lvl;
        enemyCount = numberOfEnemies();
    }
    public int getLevelNumber() {return level;}
    public int numberOfEnemies() {return 3 * level;}
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int a = 0; a < enemyCount; a++) {
            enemies.add(new Enemy(0, a * (height/enemyCount), 5, 0, 1, 10));
        }
        return enemies;
    }
}
