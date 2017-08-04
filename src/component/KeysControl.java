package src.component;

import src.core.LazarusWorld;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeysControl extends KeyAdapter {

    private Lazarus player;

    public KeysControl(Lazarus player) {
        this.player = player;
    }

    public void keyPressed(KeyEvent e) {

        int keysCode = e.getKeyCode();

        if (keysCode == KeyEvent.VK_LEFT) {
            LazarusWorld.startX = player.x;
            LazarusWorld.endLeft = LazarusWorld.startX - LazarusWorld.width;
            LazarusWorld.movingLeft = true;

            LazarusWorld.moveLeft = true;
        }
        if (keysCode == KeyEvent.VK_RIGHT) {
            LazarusWorld.startX = player.x;
            LazarusWorld.endRight = LazarusWorld.startX + LazarusWorld.width;
            LazarusWorld.movingRight = true;

            LazarusWorld.moveRight = true;
        }
        if (keysCode == KeyEvent.VK_SPACE) {
            LazarusWorld.startY = player.y;
            LazarusWorld.jumpTop = LazarusWorld.startY - LazarusWorld.height;

            LazarusWorld.movingUp = true;
            LazarusWorld.jump = true;

                if (LazarusWorld.jump && LazarusWorld.movingLeft) {
                    LazarusWorld.startX = player.x;
                    LazarusWorld.endLeft = LazarusWorld.startX - LazarusWorld.width;
                    LazarusWorld.jumpingLeft = true;
                }
                else if (LazarusWorld.jump && LazarusWorld.moveRight){
                    LazarusWorld.jumpingRight = true;
                }
                else {
                    LazarusWorld.jump = true;
                }
            }
        }


    public void keyReleased(KeyEvent e){
        int keysCode = e.getKeyCode();

        if (keysCode == KeyEvent.VK_LEFT){
            LazarusWorld.moveLeft = false;
        }
        if(keysCode == KeyEvent.VK_RIGHT){
            LazarusWorld.moveRight = false;
        }
        if(keysCode == KeyEvent.VK_SPACE){
            //LazarusWorld.jump = false;
        }
    }

}