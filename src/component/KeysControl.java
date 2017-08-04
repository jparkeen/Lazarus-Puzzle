package src.component;

import src.core.Lazarus;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeysControl extends KeyAdapter {

    private LazarusObject player;

    public KeysControl(LazarusObject player) {
        this.player = player;
    }

    public void keyPressed(KeyEvent e) {

        int keysCode = e.getKeyCode();

        if (keysCode == KeyEvent.VK_LEFT) {
            Lazarus.startX = player.x;
            Lazarus.endLeft = Lazarus.startX - Lazarus.width;
            Lazarus.movingLeft = true;

            Lazarus.moveLeft = true;
        }
        if (keysCode == KeyEvent.VK_RIGHT) {
            Lazarus.startX = player.x;
            Lazarus.endRight = Lazarus.startX + Lazarus.width;
            Lazarus.movingRight = true;

            Lazarus.moveRight = true;
        }
        if (keysCode == KeyEvent.VK_SPACE) {
            Lazarus.startY = player.y;
            Lazarus.jumpTop = Lazarus.startY - Lazarus.height;

            Lazarus.movingUp = true;
            Lazarus.jump = true;

                if (Lazarus.jump && Lazarus.movingLeft) {
                    Lazarus.startX = player.x;
                    Lazarus.endLeft = Lazarus.startX - Lazarus.width;
                    Lazarus.jumpingLeft = true;
                }
                else if (Lazarus.jump && Lazarus.moveRight){
                    Lazarus.jumpingRight = true;
                }
                else {
                    Lazarus.jump = true;
                }
            }
        }


    public void keyReleased(KeyEvent e){
        int keysCode = e.getKeyCode();

        if (keysCode == KeyEvent.VK_LEFT){
           Lazarus.moveLeft = false;
        }
        if(keysCode == KeyEvent.VK_RIGHT){
            Lazarus.moveRight = false;
        }
        if(keysCode == KeyEvent.VK_SPACE){
            //Lazarus.jump = false;
        }
    }

}