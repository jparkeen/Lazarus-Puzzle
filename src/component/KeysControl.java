package src.component;

import src.core.Lazarus;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeysControl extends KeyAdapter {

    private LazarusObject player;

    private ArrayList<Boxes> boxes;

    public KeysControl(LazarusObject player, ArrayList<Boxes> boxes) {
        this.player = player;
        this.boxes = boxes;
    }

    public void keyPressed(KeyEvent e) {

        int keysCode = e.getKeyCode();

        if (keysCode == KeyEvent.VK_LEFT){
            Lazarus.moveLeft = true;
            boxes.add(new Boxes(player.x, player.y));
        }
        if(keysCode == KeyEvent.VK_RIGHT){
            Lazarus.moveRight = true;
            boxes.add(new Boxes(player.x, player.y));
        }
        if(keysCode == KeyEvent.VK_SPACE){
            Lazarus.startY = player.y;
            Lazarus.jumpTop = Lazarus.startY - Lazarus.height;
            Lazarus.movingUp = true;
            Lazarus.jump = true;
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
           // Lazarus.jump = false;
        }
    }

}