package src.core;

import src.commons.Globals;
import src.component.CollisionDetector;
import src.component.KeysControl;
import src.component.LazarusObject;
import src.commons.MapReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Lazarus extends JComponent implements Runnable {

    private Thread thread;
    public static boolean moveLeft,moveRight,jump ,movingUp;
    private KeysControl keysControl;
    private LazarusObject player;
    public  static int startX,startY;
    int health = 20, lives = 2;
    int count = 0, frame = 1;
    public static  int  height = 40,jumpTop;
    private CollisionDetector collision;

    private String[][] map;

    public Lazarus() throws IOException {
        this.map = MapReader.readMap(Globals.MAP1_FILENAME);
        setFocusable(true);

        collision = new CollisionDetector(map);
        findStartPosition();

        player = new LazarusObject(Lazarus.startX,Lazarus.startY,health,lives,this);

        this.keysControl = new KeysControl(this.player);
        addKeyListener(keysControl);
    }

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        renderBackground(g2);
        renderMap(g2);
        drawLazarus(g2, player.x, player.y);
        handleMovement(g2);
    }

    public void handleMovement(Graphics g) {
            int newX, newY;
            int oldX, oldY;

            if (Lazarus.moveLeft) {
//                player.x -= Globals.BLOCK_SIZE;
                newX = player.x - Globals.BLOCK_SIZE;
                oldX = player.x;
                newY = player.y;
                if (collision.validateCollision(newX, newY, player)) {
                    player.x = oldX;
                }else {
                    player.x = newX;
                }

            }
            if (Lazarus.moveRight) {
//                player.x += Globals.BLOCK_SIZE;
                newX = player.x + Globals.BLOCK_SIZE;
                oldX = player.x;
                newY = player.y;
                if (collision.validateCollision(newX, newY, player)) {
                    player.x = oldX;
                }else {
                    player.x = newX;
                }
            }
            if (Lazarus.jump) {
               if(Lazarus.movingUp){
                   player.y--;

                   //collision with boundary
                   newX = player.x;
                   newY = player.y - Globals.BLOCK_SIZE;
                   oldY = player.y;
                   if (collision.validateCollision(newX, newY, player)) {
                       player.y = oldY;
                   }else {
                       player.y = newY;
                   }


                   if(player.y == jumpTop){
                       Lazarus.movingUp = false;
                       return;
                   }
               }
               if(!Lazarus.movingUp) {
                   player.y++;
                   if(player.y == startY){
                       Lazarus.jump = false;
                   }
               }
            }
        }

    public void renderBackground(Graphics2D g2) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/Background.png");
        g2.drawImage(image, 0, 0, Globals.BOARD_SIZE, Globals.BOARD_SIZE, this);
        g2.finalize();
    }

    public void findStartPosition(){
        for (int row = 0; row < Globals.MAX_NUMBER_OF_BLOCKS; row++) {
            for (int col = 0; col < Globals.MAX_NUMBER_OF_BLOCKS; col++) {
                String value = map[row][col];
                int y = row * Globals.BLOCK_SIZE;
                int x = col * Globals.BLOCK_SIZE;
                if (value.equals(MapReader.LAZARUS)) {
                    Lazarus.startX = x;
                    Lazarus.startY = y;
                    continue;
                }
            }
        }
    }

    private void renderMap(Graphics2D g2) {

        for (int row = 0; row < Globals.MAX_NUMBER_OF_BLOCKS; row++) {
            for (int col = 0; col < Globals.MAX_NUMBER_OF_BLOCKS; col++) {
                String value = map[row][col];
                int y = row * Globals.BLOCK_SIZE;
                int x = col * Globals.BLOCK_SIZE;
                if (value.equals(MapReader.WALL)) {
                    renderWall(g2, x, y);
                    continue;
                }
                if (value.equals(MapReader.STOP)) {
                    renderButton(g2, x, y);
                    continue;
                }
                if (value.equals(MapReader.SPACE)) {
                    // Do nothing
                    continue;
                }
                if (value.equals(MapReader.LAZARUS)) {
                    Lazarus.startX = x;
                    Lazarus.startY = y;
                    continue;
                }
            }
        }
    }

    private void renderWall(Graphics2D g2, int x, int y) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/Wall.png");
        g2.drawImage(image, x, y, Globals.BLOCK_SIZE, Globals.BLOCK_SIZE, this);
        g2.finalize();
    }

    private void renderButton(Graphics2D g2, int x, int y) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/Button.png");
        g2.drawImage(image, x, y, Globals.BLOCK_SIZE, Globals.BLOCK_SIZE, this);
        g2.finalize();
    }

    private void drawLazarus(Graphics2D g2, int x, int y) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/lazarus/Lazarus_stand.png");
        g2.drawImage(image, x, y, Globals.BLOCK_SIZE, Globals.BLOCK_SIZE, this);
        g2.finalize();
    }

   public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(15);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

}