package com.missionbit.snailassalt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by ryanisaqt on 9/22/14.
 */
public class Credits extends GameStates {
    protected Sprite credits, hurshalsFace, specialThanks;
    protected BackButton backButton;
    protected static enum CreditState {
        PAGE1, PAGE2
    }
    protected static CreditState state;
    public Credits() {
        background = new Sprite(new Texture("images/backgrounds/levelscreen.png"));
        background.setSize(width, height);
    }

    public void create() {
        state = CreditState.PAGE1;
        credits = new Sprite(new Texture("images/backgrounds/credits.png"));
        credits.setSize(width / 1196 * credits.getWidth(), height / 720 * credits.getHeight());
        credits.setPosition(width / 2 - credits.getWidth() / 2, height / 2 - credits.getHeight() / 2);
        hurshalsFace = new Sprite(new Texture("images/backgrounds/hurshalsFace.png"));
        hurshalsFace.setSize(width / 1196 * hurshalsFace.getWidth(), height / 720 * hurshalsFace.getHeight());
        hurshalsFace.setPosition(width - hurshalsFace.getWidth(), 0);
        specialThanks = new Sprite(new Texture("images/backgrounds/specialThanks.png"));
        specialThanks.setSize(width / 1196 * specialThanks.getWidth(), height / 720 * specialThanks.getHeight());
        specialThanks.setPosition(width / 2 - specialThanks.getWidth() / 2, height / 2 - 100);

        BackButton tempBackButton = new BackButton(0, 0);
        backButton = new BackButton(width - tempBackButton.getWidth(), height - tempBackButton.getHeight());
    }

    public void update() {
        if (state == CreditState.PAGE1) {
            if (Gdx.input.justTouched()) {
                state = CreditState.PAGE2;
            }
        }
        if (state == CreditState.PAGE2) {
            if (backButton.isPressed()) {
                backButton.pressedAction();
            }
        }
    }

    public void draw() {
        batch.begin();
        background.draw(batch);
        if (state == CreditState.PAGE1) {
            credits.draw(batch);
        }
        if (state == CreditState.PAGE2) {
            hurshalsFace.draw(batch);
            specialThanks.draw(batch);
            backButton.sprite.draw(batch);
        }
        batch.end();
    }
}
