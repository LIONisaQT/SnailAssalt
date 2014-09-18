package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by ryanisaqt on 8/29/14.
 */
public class InGame extends GameStates {
    private ShapeRenderer shapeRenderer;
    protected Sprite hpBar, waterBar, saltBar, progBarSnail;
    private House house;

    protected QuitButton quitButton;
    protected static HydraButton hydraButton;
    protected static HoseButton hoseButton;
    protected static SaltButton saltButton;

    //WEAPONS
    protected static ArrayList<ThrowyThingy> water;
    protected static ArrayList<SaltProjectile> shakers;

    //ENEMY-RELATED
    protected static ArrayList<GhostSnails> deadSnails;

    protected static enum WeaponState {REGWEAPON, HYDRA, HOSE}
    protected static WeaponState weaponState;
    protected static enum BulletType {SALT, WATER}
    protected static BulletType bulletType;
    public InGame() {
        background = new Sprite(new Texture("images/backgrounds/lawn.jpeg"));
        background.setSize(width, height);
    }

    public void create() {
        shapeRenderer = new ShapeRenderer();
        house = new House();

        //WEAPONS
        weaponState = WeaponState.REGWEAPON;
        bulletType = BulletType.WATER;
        Weapon.saltSupply = SnailAssalt.preferences.getInteger("saltsupply", 0);
        water = new ArrayList<ThrowyThingy>();
        shakers = new ArrayList<SaltProjectile>();

        //ENEMY RELATED
        deadSnails = new ArrayList<GhostSnails>();

        //HP BAR
        Texture hpBarTexture = new Texture("images/house/hpBar.png");
        hpBar = new Sprite(hpBarTexture);
        hpBar.setSize(width / 4, hpBarTexture.getHeight());
        hpBar.setPosition(width - hpBar.getWidth() - 5, height - 50);

        //WATER BAR
        Texture waterBarTexture = new Texture("images/house/waterGauge.png");
        waterBar = new Sprite(waterBarTexture);
        waterBar.setSize(width / 4, waterBarTexture.getHeight());
        waterBar.setPosition(width - 2 * hpBar.getWidth() - 10, height - 50);

        //SALT BAR
        Texture saltBarTexture = new Texture("images/house/saltBar.png");
        saltBar = new Sprite(saltBarTexture);
        saltBar.setSize(width / 4, saltBarTexture.getHeight());
        saltBar.setPosition(width - 3 * hpBar.getWidth() - 15, height - 50);

        //PROGRESSION BAR
        Texture progBar = new Texture("images/enemies/snailMeh.png");
        progBarSnail = new Sprite(progBar);
        progBarSnail.setSize(progBar.getWidth(), progBar.getHeight());
        progBarSnail.setPosition(0, 0);

        //QUIT BUTTON
        QuitButton tempQuitButton = new QuitButton(0, 0);
        quitButton = new QuitButton(0, height - tempQuitButton.sprite.getHeight());

        //IN-GAME WEAPON BUTTONS
        SaltButton tempSaltButton = new SaltButton(0, 0);
        saltButton = new SaltButton(width - tempSaltButton.sprite.getWidth(), height/5);
        HydraButton tempHydraButton = new HydraButton(0, 0);
        hydraButton = new HydraButton(width - tempHydraButton.sprite.getWidth(), (4*height)/(5));
        HoseButton tempHoseButton = new HoseButton(0, 0);
        hoseButton = new HoseButton(width - tempHoseButton.sprite.getWidth(), hydraButton.getYPos());
    }

    public void update() {
        progBarSnail.setPosition(((float)LevelSelect.currentLevel.enemyCount / LevelSelect.currentLevel.totalEnemies) * width - (progBarSnail.getWidth() / 2), 0);
        if (hydraButton.isPressed())
            bulletType = BulletType.WATER;
        else if (saltButton.isPressed())
            bulletType = BulletType.SALT;

        //this seems to only run if we have hydra, but not hose?
        if (SnailAssalt.hydra.enable) {
            if (hydraButton.isPressed()) {
                if (weaponState == WeaponState.REGWEAPON)
                    weaponState = WeaponState.HYDRA;
                else if (weaponState == WeaponState.HYDRA)
                    weaponState = WeaponState.HOSE;
                else if (weaponState == WeaponState.HOSE)
                    weaponState = WeaponState.REGWEAPON;
            }
        }

        //ACTUAL WATER GUN CODE
        if (weaponState == WeaponState.REGWEAPON) {
            if (SnailAssalt.waterGun.enable)
                SnailAssalt.waterGun.Update(water);
            if (SnailAssalt.waterGun.enableSalt) {
                if (saltButton.isPressed())
                    bulletType = BulletType.SALT;
                if (bulletType == BulletType.SALT) {
                    SnailAssalt.saltarm.sprite.setRotation(Weapon.rot);
                    SnailAssalt.waterGun.Update2(shakers);
                    SnailAssalt.saltarm.Update2(shakers);
                }
            }
        } else if (weaponState == WeaponState.HYDRA) {
            if (bulletType == BulletType.WATER) {
                if (SnailAssalt.hydra.enable)
                    SnailAssalt.hydra.Update(water);
            }
            if (SnailAssalt.hydra.enableSalt) {
                if (saltButton.isPressed())
                    bulletType = BulletType.SALT;
                if (bulletType == BulletType.SALT) {
                    SnailAssalt.saltarm.sprite.setRotation(Weapon.rot);
                    SnailAssalt.hydra.Update2(shakers);
                    SnailAssalt.saltarm.Update2(shakers);
                }
            }
        } else if (weaponState == WeaponState.HOSE) {
            if (bulletType == BulletType.WATER){
                if (SnailAssalt.hose.enable)
                    SnailAssalt.hose.Update(water);
            }
            if (bulletType == BulletType.SALT) {
                if (SnailAssalt.hose.enableSalt) {
                    SnailAssalt.saltarm.sprite.setRotation(Weapon.rot);
                    SnailAssalt.hose.Update2(shakers);
                    SnailAssalt.saltarm.Update2(shakers);
                }
            }
        }

        //WATER PROJECTILE INTERACTION
        for (int i = 0; i < water.size(); i++) {
            ThrowyThingy proj = water.get(i);
            proj.Update();
            if (proj.bound.y >= height) {
                water.remove(i);
            }
            if (proj.bound.y < 0) {
                water.remove(i);
            }
            boolean projectileHit = false;
            for (int a = 0; a < LevelSelect.enemies.size(); a++) {
                if (proj.bound.overlaps(LevelSelect.enemies.get(a).bound)) {
                    projectileHit = true;
                    LevelSelect.enemies.get(a).hp = LevelSelect.enemies.get(a).hp - Weapon.str;
                    LevelSelect.enemies.get(a).startFlash(.1f);
                    if (LevelSelect.enemies.get(a).hp <= 0) {
                        deadSnails.add(new GhostSnails((int)LevelSelect.enemies.get(a).bound.x, (int)LevelSelect.enemies.get(a).bound.y));
                        LevelSelect.enemies.remove(a);
                        a--;
                        LevelSelect.currentLevel.enemyCount++;
                        SnailAssalt.currency += 5;
                        Weapon.currentWater += 10;
                        if (Weapon.currentWater >= Weapon.waterSupply) {
                            Weapon.currentWater = 50;
                        }
                    }
                }
            }
            if (projectileHit) {
                water.get(i).dispose();
                water.remove(i);
            }
        }

        //SALT SHAKER PROJECTILE INTERACTION
        for (int c = 0; c < shakers.size(); c++) {
            SaltProjectile bullet = shakers.get(c);
            bullet.Update();
            if (bullet.bound.y >= height) {
                shakers.remove(c);
            }
            if (bullet.bound.y < 0) {
                shakers.remove(c);
            }
            boolean projectileHit = false;
            for (int a = 0; a < LevelSelect.enemies.size(); a++) {
                if (bullet.bound.overlaps(LevelSelect.enemies.get(a).bound)) {
                    projectileHit = true;
                    LevelSelect.enemies.get(a).hp = LevelSelect.enemies.get(a).hp - Weapon.str;
                    LevelSelect.enemies.get(a).startFlash(.1f);
                    if (LevelSelect.enemies.get(a).hp <= 0) {
                        deadSnails.add(new GhostSnails((int)LevelSelect.enemies.get(a).bound.x, (int)LevelSelect.enemies.get(a).bound.y));
                        LevelSelect.enemies.get(a).dispose();
                        LevelSelect.enemies.remove(a);
                        LevelSelect.currentLevel.enemyCount++;
                        a--;
                        SnailAssalt.currency += 5;
                    }
                }
            }
            if (projectileHit){
                shakers.remove(c);
            }
        }

        //GHOST SNAILS
        for (int b = 0; b < deadSnails.size(); b++) {
            deadSnails.get(b).Update();
            if (deadSnails.get(b).bounds.y > height) {
                deadSnails.get(b).dispose();
                deadSnails.remove(b);
                b--;
            }
        }

        //ENEMY CODE
        for (Enemy enemy : LevelSelect.enemies) {
            enemy.Update(SnailAssalt.deltaTime);

            //ENEMIES HITTING HOUSE
            if (enemy.bound.overlaps(House.Housebounds))
                House.hp -= enemy.Attack * Gdx.graphics.getDeltaTime();
        }

        //PROGRESSION BAR
        progBarSnail.setPosition(((float)LevelSelect.currentLevel.enemyCount /LevelSelect.currentLevel.totalEnemies) * width - progBarSnail.getWidth() / 2, -progBarSnail.getHeight() / 2);

        //LOSE / VICTORY CONDITIONS
        if (House.hp <= 0 || quitButton.isPressed())
            SnailAssalt.gameState = SnailAssalt.GameState.GAMEOVER;
        if (LevelSelect.enemies.size() == 0)
            SnailAssalt.gameState = SnailAssalt.GameState.WIN;
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        house.draw(batch);
        shapeRenderer.setProjectionMatrix(SnailAssalt.camera.combined);
        saltButton.sprite.draw(batch);

        //PLAYERS
        if (!SnailAssalt.rachel.enable) {
            batch.draw(SnailAssalt.jimmy.sprite, width - width / 10, SnailAssalt.jimmy.bound.y, (width / 1196) * SnailAssalt.jimmy.sprite.getWidth(), (height / 720) * SnailAssalt.jimmy.sprite.getHeight());
            SnailAssalt.waterGun.sprite.setPosition(SnailAssalt.jimmy.bound.x, 38f / 260f * (height / 720 * SnailAssalt.jimmy.sprite.getHeight()) + SnailAssalt.jimmy.bound.y);
            SnailAssalt.hydra.sprite.setPosition(SnailAssalt.jimmy.bound.x, 38f / 260f * (height / 720 * SnailAssalt.jimmy.sprite.getHeight()) + SnailAssalt.jimmy.bound.y);
        } else if (SnailAssalt.rachel.enable) { //TODO: wtf fix this
            batch.draw(SnailAssalt.rachel.rachel, width - width / 10, SnailAssalt.rachel.bound.y, (width / 1196) * SnailAssalt.rachel.rachel.getWidth(), (height / 720) * SnailAssalt.rachel.rachel.getHeight());
            SnailAssalt.waterGun.sprite.setPosition(SnailAssalt.jimmy.bound.x, 38f / 260f * (height / 720 * SnailAssalt.jimmy.sprite.getHeight()) + SnailAssalt.jimmy.bound.y);
            SnailAssalt.hydra.sprite.setPosition(SnailAssalt.jimmy.bound.x, 38f / 260f * (height / 720 * SnailAssalt.jimmy.sprite.getHeight()) + SnailAssalt.jimmy.bound.y);
        }

        //PROJECTILES
        for (ThrowyThingy proj : water)
            proj.sprite.draw(batch);
        for (SaltProjectile bullet : shakers)
            bullet.sprite.draw(batch);

        //ENEMY-RELATED
        for (Enemy enemy : LevelSelect.enemies)
            enemy.draw(batch, SnailAssalt.time);
        for (GhostSnails snailshell : deadSnails)
            snailshell.sprite.draw(batch);

        //DEBUG MESSAGES (REMOVE WHEN GAME IS FINISHED)
        SnailAssalt.font.draw(batch, "number of dead snails: " + deadSnails.size(), 200, 200);
        SnailAssalt.font.draw(batch, "hydra: salt enabled? -- " + SnailAssalt.hydra.enableSalt, 300, 400);
        SnailAssalt.font.draw(batch, "Current level: " + LevelSelect.currentLevel.getLevelNumber(), 10, 90);
        batch.end();

        //GUI
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        int topBarXPos = 50;
        int barHeight = 30;

        //GUI BACKGROUND
        shapeRenderer.setColor(Color.ORANGE);
        shapeRenderer.rect(0, height - height / 8, width, height / 8);

        //EMPTY BARS
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0, width, 20);
        shapeRenderer.rect(width - hpBar.getWidth() - 5, height - topBarXPos, width / 4, barHeight);
        shapeRenderer.rect(width - 2 * hpBar.getWidth() - 10, height - topBarXPos, width / 4, barHeight);
        shapeRenderer.rect(width - 3 * hpBar.getWidth() - 15, height - topBarXPos, width / 4, barHeight);

        //HP BAR
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(width - hpBar.getWidth() - 5, height - topBarXPos, (House.hp / House.maxHP) * (width / 4), barHeight);

        //WATER BAR
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(width - 2 * hpBar.getWidth() - 10, height - topBarXPos, (Weapon.currentWater / Weapon.waterSupply) * (width / 4), barHeight);

        //SALT BAR
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(width - 3 * hpBar.getWidth() - 15, height - topBarXPos, (Weapon.currentSalt / Weapon.saltSupply) * (width / 4), barHeight);

        //PROGRESS BAR
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, 0, ((float)LevelSelect.currentLevel.enemyCount / LevelSelect.currentLevel.totalEnemies) * width, 40);

        shapeRenderer.end();

        //GUI RELATED
        batch.begin();
        progBarSnail.draw(batch);
        hpBar.draw(batch);
        waterBar.draw(batch);
        saltBar.draw(batch);
        quitButton.sprite.draw(batch); //quit button is drawn on the bar
        SnailAssalt.font.draw(batch, (int) House.hp + "/" + (int) House.maxHP, width - hpBar.getWidth(), height - 2 * barHeight);
        SnailAssalt.font.draw(batch, (int) Weapon.currentWater + "/" + (int) Weapon.waterSupply, width - 2 * hpBar.getWidth(), height - 2 * barHeight);
        SnailAssalt.font.draw(batch, (int) Weapon.currentSalt + "/" + (int) Weapon.saltSupply, width - 3 * hpBar.getWidth(), height - 2 * barHeight);

        //WEAPONS
        if (weaponState == WeaponState.REGWEAPON) {
            SnailAssalt.font.draw(batch, "current weapon: regular", 350, 350);
            hydraButton.sprite.draw(batch);
            if (bulletType == BulletType.WATER)
                SnailAssalt.waterGun.sprite.draw(batch);
            else if (bulletType == BulletType.SALT)
                SnailAssalt.saltarm.sprite.draw(batch);
        } else if (weaponState == WeaponState.HYDRA) {
            SnailAssalt.font.draw(batch, "current weapon: hydra", 350, 350);
            hoseButton.sprite.draw(batch);
            if (bulletType == BulletType.WATER)
                SnailAssalt.hydra.sprite.draw(batch);
            else if (bulletType == BulletType.SALT)
                SnailAssalt.saltarm.sprite.draw(batch);
        } else if (weaponState == WeaponState.HOSE) {
            SnailAssalt.font.draw(batch, "current weapon: hose", 350, 350);
            hydraButton.watergunSprite.setPosition(hydraButton.getXPos(), hydraButton.getYPos());
            hydraButton.watergunSprite.draw(batch);
            if (bulletType == BulletType.WATER)
                SnailAssalt.hose.sprite.draw(batch);
            else if (bulletType == BulletType.SALT)
                SnailAssalt.saltarm.sprite.draw(batch);
        }
        batch.end();
    }
}
