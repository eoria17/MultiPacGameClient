package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class KeyBoard implements KeyListener {
    private File config;
    private static KeyEventHandler handler;
    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    public KeyBoard() {

    }

    public void keyBoardListener(BoardPanel gameBoard) {
        gameBoard.addKeyListener(this);
    }

    public void keyEventRegister(KeyEventHandler handler) {
        this.handler = handler;
    }

    public String getKey(int keyCode) {
        if (keyCode == 38) {
            return UP;
        } else if (keyCode == 40) {
            return DOWN;
        } else if (keyCode == 37) {
            return LEFT;
        } else if (keyCode == 39) {
            return RIGHT;
        } else {
            return null;
        }
    }

    public void keyEventDispatcher(int keyCode) {
        if (KeyBoard.handler != null) {
            KeyBoard.handler.handleKeyEvent(getKey(keyCode));
        }
    }

    public void setupKeyBinding() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keyEventDispatcher(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
