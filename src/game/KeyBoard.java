package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

public class KeyBoard {
	private File config;
	private ArrayList<KeyEventHandler> handler;
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";

	public KeyBoard(KeyEventHandler eventHandler) {
		this.handler = new ArrayList<>();
		this.keyBoardListener(eventHandler);
	}

	private void keyBoardListener(KeyEventHandler eventHandler) {
		KeyBoard that = this;

		eventHandler.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				that.keyEventDispatcher(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
	}

	public void keyEventRegister(KeyEventHandler handler) {
		this.handler.add(handler);
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
        }else if(keyCode == 65) {
        	return "drop";
        } else {
            return null;
        }
    }

	public void keyEventDispatcher(int keyCode) {
		for (int i = 0; i < this.handler.size(); i++) {
			this.handler.get(i).handleKeyEvent(getKey(keyCode));
		}
	}

	public void setupKeyBinding() {

	}
}