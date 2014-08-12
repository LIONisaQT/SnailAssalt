package com.missionbit.snailassalt;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
/**
 * Created by ryansheeisaqt on 6/20/14.
 */
public class Level {
    public int enemyCount, level;
    private float width, height,SpawnOffset;
    public int totalEnemies;
    public float enemyScale;
    public Level(int lvl) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        level = lvl;
        enemyCount = numberOfEnemies();
        totalEnemies = numberOfEnemies();
        enemyScale = (enemyCount/totalEnemies)*20;
        SpawnOffset = height - 200;
    }
    public int getLevelNumber() {return level;}
    public int numberOfEnemies() {return 4 * level+1;}
    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int a = 0; a < totalEnemies / 4; a++) {
            enemies.add(new Enemy(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        for (int a = 0; a < totalEnemies / 4; a++) {
            enemies.add(new Enemy(-width, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        for (int a = 0; a < totalEnemies / 4; a++) {
            enemies.add(new Enemy(-width * 2, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        for (int a = 0; a < totalEnemies / 4; a++) {
            enemies.add(new Enemy(-width * 3, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        if (level == 2) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
        }
        return enemies;
    }

}
