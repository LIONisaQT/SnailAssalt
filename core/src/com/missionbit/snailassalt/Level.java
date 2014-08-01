package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/20/14.
 */
public class Level {
    private int enemyCount, level;
    private float width, height,SpawnOffset;
    public Level(int lvl) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        level = lvl;
        enemyCount = numberOfEnemies();
    }
    public int getLevelNumber() {return level;}
    public int numberOfEnemies() {return 4 * level;}
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int a = 0; a < enemyCount/4; a++) {enemies.add(new Enemy(-100, (float)Math.random() * height, 5, 0, 1, 10));}
        for (int a= 0; a<enemyCount/4; a++) {enemies.add(new Enemy(-width, (float)Math.random() * height, 5, 0, 1, 10));}
        for (int a= 0; a<enemyCount/4; a++) {enemies.add(new Enemy(-width * 2, (float)Math.random() * height, 5, 0, 1, 10));}
        for (int a = 0; a<enemyCount/4; a++) {enemies.add(new Enemy(-width * 3, (float)Math.random() * height, 5, 0, 1, 10));}
        for (int a = 0; a<enemyCount/4; a++) {enemies.add(new Enemy(-width * 4, (float)Math.random() * height, 5, 0, 1, 10));}
        enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
        return enemies;
    }
}
