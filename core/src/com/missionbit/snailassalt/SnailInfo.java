package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by ryanisaqt on 8/26/14.
 */
public class SnailInfo extends GameStates {
    protected static int numberOfTypes = 7;
    protected Sprite sign;
    protected static ArrayList<Enemy> enemies;

    protected ArrayList<SnailInfoButtons> snailInfoButtons;
    protected NextButton nextButton;
    protected BackButton backButton;

    protected static enum InfoState {SELECTION, STANDARD, ACID, FLYING, HEALING, BOSS, MOTHER, PERSON}
    protected static InfoState infoState;
    public SnailInfo(SnailAssalt game) {
        super(game);
        background = new Sprite(new Texture("images/backgrounds/info.png"));
        background.setSize(width, height);
        sign = new Sprite(new Texture("images/backgrounds/sign.png"));
        sign.setSize(width / 2, height);
        sign.setPosition(width / 2 , 0);
    }

    public void create() {
        infoState = InfoState.SELECTION;
        enemies = new ArrayList<Enemy>();
        snailInfoButtons = new ArrayList<SnailInfoButtons>();
        for (int b = 0; b < numberOfTypes; b++) {
            if (b < 5)
                snailInfoButtons.add(new SnailInfoButtons(b * 210, 200)); //first row
            else
                snailInfoButtons.add(new SnailInfoButtons((b - 5) * 210, 0)); //second row
        }

        BackButton tempBackButton = new BackButton(0, 0);
        backButton = new BackButton(0, height - tempBackButton.getHeight());

        NextButton tempNextButton = new NextButton(0, 0);
        nextButton = new NextButton(width - tempNextButton.getWidth(), height - tempNextButton.getHeight());
    }

    public void update() {
        if (backButton.isPressed()) {
            backButton.pressedAction();
        }
        for (int b = 0; b < numberOfTypes; b++) {
            if (snailInfoButtons.get(b).isPressed() && infoState == InfoState.SELECTION) {
                snailInfoButtons.get(b).pressedAction();
                if (b == 0) {
                    infoState = InfoState.STANDARD;
                    enemies.clear();
                    enemies.add(new Enemy(width / 11, height / 5, 0, 0, 0, 0));
                } else if (b == 1) {
                    infoState = InfoState.ACID;
                    enemies.clear();
                    enemies.add(new AcidSnail(width / 11, height / 5, 0, 0, 0, 0));
                } else if (b == 2) {
                    infoState = InfoState.FLYING;
                    enemies.clear();
                    for (int a = 0; a < 1; a++)
                        enemies.add(new FlyingSnail(width / 11, height / 5, 0, 0, 0, 0));
                } else if (b == 3) {
                    infoState = InfoState.HEALING;
                    enemies.clear();
                    for (int a = 0; a < 1; a++)
                        enemies.add(new HealerSnail(width / 11, height / 5, 0, 0, 0, 0));
                } else if (b == 4) {
                    infoState = InfoState.BOSS;
                    enemies.clear();
                    for (int a = 0; a < 1; a++)
                        enemies.add(new BossSnail(width / 11, height / 5, 0, 0, 0, 0));
                } else if (b == 5) {
                    infoState = InfoState.MOTHER;
                    enemies.clear();
                    for (int a = 0; a < 1; a++)
                        enemies.add(new MotherSnail(width / 11, height / 5, 0, 0, 0, 0));
                } else if (b == 6) {
                    infoState = InfoState.PERSON;
                    enemies.clear();
                    for (int a = 0; a < 1; a++)
                        enemies.add(new Zombie(width / 11, height / 5, 0, 0, 0, 0));
                }
            }
        }
    }

    public void draw() {
        batch.begin();
        SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
        SnailAssalt.font.setColor(0, 0, 0, 1);
        background.draw(batch);
        if (infoState == InfoState.SELECTION) {
            for (int b = 0; b < numberOfTypes; b++) {
                SnailInfoButtons lb = snailInfoButtons.get(b);
                batch.draw(lb.getButtonImage(b + 1), lb.bound.x, lb.bound.y);
            }
            backButton.sprite.draw(batch);
        } else if (infoState == InfoState.STANDARD) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:very weak", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "STANDARD SNAIL", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
            nextButton.sprite.draw(batch);
        } else if (infoState == InfoState.ACID) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
            SnailAssalt.font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:weak", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-'speed up' slimes ", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "ACID SNAIL", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
            nextButton.sprite.draw(batch);

        } else if (infoState == InfoState.FLYING) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
            SnailAssalt.font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-'speed up' slime bombs", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "FLYING SNAIL", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
            nextButton.sprite.draw(batch);

        } else if (infoState == InfoState.HEALING) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
            SnailAssalt.font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "HEALING SNAIL", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
            nextButton.sprite.draw(batch);

        } else if (infoState == InfoState.BOSS) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
            SnailAssalt.font.draw(batch, "-Speed:normal", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:very strong", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-high HP", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "KING SNAILEY", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
            nextButton.sprite.draw(batch);

        } else if (infoState == InfoState.MOTHER) {
            for (Enemy enemy : enemies) {
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
            SnailAssalt.font.draw(batch, "-Speed:slow", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:weak", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-high HP", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "MOTHER SNAIL", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
            nextButton.sprite.draw(batch);
        } else if (infoState == InfoState.PERSON) {
            for (Enemy enemy : enemies) {//draws and animates enemies
                enemy.draw(batch, SnailAssalt.time);
            }
            sign.draw(batch);
            SnailAssalt.font.setScale((float) ((width / 1196) * (1.4)));
            SnailAssalt.font.draw(batch, "-Speed:very fast", sign.getX() + 50, (3 * height) / 4);
            SnailAssalt.font.draw(batch, "-Attack:normal", sign.getX() + 50, (3 * height) / 4 - (SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.draw(batch, "-no special powers", sign.getX() + 50, (3 * height) / 4 - (2 * SnailAssalt.font.getLineHeight()));
            SnailAssalt.font.setScale((float) ((width / 1196) * (2.1)));
            SnailAssalt.font.draw(batch, "ZOMBIE", width / 9, (2 * SnailAssalt.font.getLineHeight()));
            backButton.sprite.draw(batch);
        }
        batch.end();
    }
}
