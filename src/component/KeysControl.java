package component;

import commons.Globals;
import core.LazarusWorld;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeysControl extends KeyAdapter {

    private Lazarus player;

    public KeysControl(Lazarus player) {
        this.player = player;
    }

    public void keyPressed(KeyEvent e) {

        int keysCode = e.getKeyCode();


        if (keysCode == KeyEvent.VK_LEFT ) {
            LazarusWorld.startX = player.x;
            LazarusWorld.endLeft = LazarusWorld.startX - LazarusWorld.width;
            LazarusWorld.movingLeft = true;
            LazarusWorld.moveLeft = true;
        }

        if (keysCode == KeyEvent.VK_RIGHT ) {
            LazarusWorld.startX = player.x;
            LazarusWorld.endRight = LazarusWorld.startX + LazarusWorld.width;
            LazarusWorld.movingRight = true;
            LazarusWorld.moveRight = true;
        }
        }


    public void keyReleased(KeyEvent e) {
        int keysCode = e.getKeyCode();

        if (keysCode == KeyEvent.VK_LEFT) {
            LazarusWorld.moveLeft = false;
        }
        if (keysCode == KeyEvent.VK_RIGHT) {
            LazarusWorld.moveRight = false;
        }
    }
}