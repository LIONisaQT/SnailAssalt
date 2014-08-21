package com.missionbit.snailassalt;

import com.badlogic.gdx.InputProcessor;

/**
 * Created by douglas on 8/5/14.
 */
public class MyInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        System.out.println("yes");
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        System.out.println("no");
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}


