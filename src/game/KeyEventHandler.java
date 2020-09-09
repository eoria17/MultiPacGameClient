package game;

public interface KeyEventHandler {
    void handleKeyEvent(String key);
    void addKeyListener(java.awt.event.KeyListener l);
}