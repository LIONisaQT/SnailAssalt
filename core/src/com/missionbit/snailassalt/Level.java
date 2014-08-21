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
    public Level(int lvl) {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        level = lvl;
        enemyCount = numberOfEnemies();
        totalEnemies = numberOfEnemies();
        SpawnOffset = height - 200;
    }
    public int getLevelNumber() {return level;}

    public int numberOfEnemies() {return getEnemies().size();}

    public ArrayList<Enemy> getEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        for (int a = 0; a < level; a++) {
            enemies.add(new Enemy(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        for (int a = 0; a < level; a++) {
            enemies.add(new Enemy(-width, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        for (int a = 0; a < level; a++) {
            enemies.add(new Enemy(-width * 2, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }
        for (int a = 0; a < level; a++) {
            enemies.add(new Enemy(-width * 3, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
        }

        if (level == 2) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
        } else if (level == 3) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
        } else if (level == 4) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
        } else if (level == 5) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            //
            enemies.add(new BossSnail(-width, height / 2, 1, 0, 15, 75));
        } else if (level == 6) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
        } else if (level == 7) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
        } else if (level == 8) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
        } else if (level == 9) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
        } else if (level == 10) {
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 5, 0, 1, 10));
            enemies.add(new AcidSnail(-100, (float) Math.random() * SpawnOffset, 3, 0, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 3, -1, 1, 10));
            enemies.add(new AcidSnail(-width, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            enemies.add(new AcidSnail(-width * 2, (float) Math.random() * SpawnOffset, 2, 1, 1, 10));
            //flying snail
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-100, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            enemies.add(new FlyingSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 3, 5, 20));
            // heal/ stronger standard snail
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-100, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 2, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new HealerSnail(-width * 3, (float) Math.random() * SpawnOffset, 4, 0, 15, 20));
            enemies.add(new BossSnail(-width, height / 2, 1, 0, 15, 75));
            enemies.add(new BossSnail(-width, height / 2, 1, 0, 15, 75));
        }
        return enemies;
    }
}
