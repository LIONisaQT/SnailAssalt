package com.missionbit.snailassalt;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ryanisaqt on 8/29/14.
 */
public class CharacterSelect extends GameStates {
    private int buttonstate;

    protected RachelButton rachelButton;
    protected JimmyButton jimmyButton;


    public CharacterSelect() {
        background = new Sprite(new Texture("images/backgrounds/levelscreen.png"));
        background.setSize(width, height);
    }

    public void create() {
        rachelButton = new RachelButton(width / 2 + 100 , 50);
        rachelButton.bound.set(rachelButton.getXPos(), rachelButton.getYPos(), width / 2 - width / 8, height - height / 8);
        jimmyButton = new JimmyButton(100, 50);
        jimmyButton.bound.set(jimmyButton.getXPos(), jimmyButton.getYPos(), width / 2 - width / 8, height - height / 8);
    }

    public void update() {
        if (rachelButton.isPressed()) {
            buttonstate = 1;
        }
        if (rachelButton.touchup() && buttonstate == 1) {
            buttonstate = 0;
            SnailAssalt.rachel.enable = true;
            SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;
        }
        if (jimmyButton.isPressed()) {
            buttonstate = 1;
        }
        if (jimmyButton.touchup() && buttonstate == 1) {
            buttonstate = 0;
            SnailAssalt.rachel.enable = false;
            SnailAssalt.gameState = SnailAssalt.GameState.LEVELSELECT;
        }
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        batch.draw(jimmyButton.sprite, jimmyButton.bound.x, jimmyButton.bound.y, width / 2 - width / 8, height - height / 8);
        batch.draw(rachelButton.sprite, rachelButton.bound.x, rachelButton.bound.y, width / 2 - width / 8, height - height / 8);
        SnailAssalt.font.draw(batch, "Would you like to play as:", width / 6, height - 10);
        SnailAssalt.font.draw(batch, "Jimmy", jimmyButton.getXPos() + 20, jimmyButton.getYPos());
        SnailAssalt.font.draw(batch, "Rachel", rachelButton.getXPos() + 20, rachelButton.getYPos());
        batch.end();
    }
}
