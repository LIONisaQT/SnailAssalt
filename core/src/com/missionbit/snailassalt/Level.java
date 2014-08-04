package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Created by ryansheeisaqt on 6/20/14.
 */
public class Level {
    private int enemyCount, level;
    private float width, height, SpawnOffset;

    public Level(int lvl) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        level = lvl;
        enemyCount = numberOfEnemies();

    }

    public int getLevelNumber() {
        return level;
    }

    public int numberOfEnemies() {
        return 4 * level;
    }

    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int a = 0; a < enemyCount / 4; a++) {
            enemies.add(new Enemy(-100, (float) Math.random() * height, 5, 0, 1, 10));
        }
        for (int a = 0; a < enemyCount / 4; a++) {
            enemies.add(new Enemy(-width, (float) Math.random() * height, 5, 0, 1, 10));
        }
        for (int a = 0; a < enemyCount / 4; a++) {
            enemies.add(new Enemy(-width * 2, (float) Math.random() * height, 5, 0, 1, 10));
        }
        for (int a = 0; a < enemyCount / 4; a++) {
            enemies.add(new Enemy(-width * 3, (float) Math.random() * height, 5, 0, 1, 10));
        }
        for (int a = 0; a < enemyCount / 4; a++) {
            enemies.add(new Enemy(-width * 4, (float) Math.random() * height, 5, 0, 1, 10));
        }
        if (level == 2) {
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));

        }
        if (level == 3) {
            for (int a = 0; a < enemyCount / 6; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
        }
        if (level == 4) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
        }
        if (level == 5) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
            enemies.add(new Person(300, (height / 7), 10, 0, 7, 15));
        }
        if (level == 6) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
            enemies.add(new Person(300, (height / 7), 10, 0, 7, 15));

        }
        if (level == 7) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
            enemies.add(new Person(300, (height / 7), 10, 0, 7, 15));
        }
        if (level == 8) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
            enemies.add(new Person(300, (height / 7), 10, 0, 7, 15));
        }
        if (level == 9) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
            enemies.add(new Person(300, (height / 7), 10, 0, 7, 15));
        }
        if (level == 10) {
            for (int a = 0; a < enemyCount / 8; a++) {
                enemies.add(new FlyingSnail(0, (float) Math.random() * height, 5, 0, 1, 50));
            }
            enemies.add(new AcidSnail(0, (height / 4), 5, 0, 1, 10));
            enemies.add(new HealerSnail(200, (height / 6), 10, 0, 6, 10));
            enemies.add(new Person(300, (height / 7), 10, 0, 7, 15));
            if (enemyCount <= 10) {
                enemies.add(new MotherSnail(300, (height / 5), 3, 0, 25, 250));
            }
        }
        return enemies;
    }
}
