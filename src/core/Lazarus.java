package src.core;

import src.commons.Globals;
import src.component.Boxes;
import src.component.CollisionDetector;
import src.component.KeysControl;
import src.component.LazarusObject;
import src.commons.MapReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Lazarus extends JComponent implements Runnable {

    private Thread thread;

    public static boolean moveLeft,moveRight,jump ,movingUp,movingLeft,movingRight,jumpingLeft,jumpingRight;

    private KeysControl keysControl;

    private LazarusObject lazarus;

    private Boxes box;

//    private ArrayList<Boxes> boxes;

    public  static int startX,startY;

    int health = 20, lives = 2;

//    int count = 0, frame = 1;

    public static  int width = 50, height = 50,jumpTop,endLeft,endRight;

    private CollisionDetector collision;

    private String[][] map;

    public Lazarus() throws IOException {
        this.map = MapReader.readMap(Globals.MAP1_FILENAME);
//        this.boxes = new ArrayList<Boxes>(1000);

        setFocusable(true);

        collision = new CollisionDetector(map);

        findStartPosition();

        lazarus = new LazarusObject(Lazarus.startX,Lazarus.startY,health,lives,this);

        box = new Boxes(lazarus.x, 0);

        this.keysControl = new KeysControl(this.lazarus);
        addKeyListener(keysControl);
    }

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        renderBackground(g2);
        renderMap(g2);
        drawLazarus(g2, lazarus.x, lazarus.y);
        renderBoxes(g2);
        moveBoxes();
        handleMovement(g2);
    }

    public void handleMovement(Graphics g) {
        int newX, newY;
        int oldX, oldY;


        if (Lazarus.jump) {
            if (Lazarus.jumpingLeft) {
                if (Lazarus.movingUp) {

                    //collision with boundary
                    newX = lazarus.x;
                    newY = lazarus.y - Globals.BLOCK_SIZE;
                    oldY = lazarus.y;

                    if (collision.validateCollision(newX, newY)) {
                        lazarus.y = oldY;

                    } else {
                        lazarus.y = newY--;
                        lazarus.x = newX--;
                    }

                    if (lazarus.y == jumpTop) {
                        Lazarus.movingUp = false;

                        return;
                    }
                }
                if (!Lazarus.movingUp) {
                    lazarus.y++;
                    if (lazarus.y == startY) {

                        Lazarus.jump = false;

                    }
                }
            } else if (Lazarus.jumpingRight) {
                if (Lazarus.movingUp) {

                    //collision with boundary
                    newX = lazarus.x;
                    newY = lazarus.y - Globals.BLOCK_SIZE;
                    oldY = lazarus.y;

                    if (collision.validateCollision(newX, newY)) {
                        lazarus.y = oldY;

                    } else {
                        lazarus.y = newY--;
                        lazarus.x = newX++;

                    }

                    if (lazarus.y == jumpTop) {
                        Lazarus.movingUp = false;

                        return;
                    }
                }
                if (!Lazarus.movingUp) {
                    lazarus.y++;
                    if (lazarus.y == startY) {
                        Lazarus.jump = false;

                    }
                }
            } else {
                if (Lazarus.movingUp) {

                    //collision with boundary
                    newX = lazarus.x;
                    newY = lazarus.y - Globals.BLOCK_SIZE;
                    oldY = lazarus.y;

                    if (collision.validateCollision(newX, newY)) {
                        lazarus.y = oldY;

                    } else {
                        lazarus.y = newY--;

                    }

                    if (lazarus.y == jumpTop) {
                        Lazarus.movingUp = false;


                        return;
                    }
                }
                if (!Lazarus.movingUp) {
                    lazarus.y++;
                    if (lazarus.y == startY) {

                        Lazarus.jump = false;

                    }
                }
            }
        }
        if (Lazarus.moveLeft) {
            if (Lazarus.movingLeft) {
                newX = lazarus.x - Globals.BLOCK_SIZE;
                oldX = lazarus.x;
                newY = lazarus.y;
                if (collision.validateCollision(newX, newY)) {
                    lazarus.x = oldX;
                } else {
                    lazarus.x = newX--;
                    if (lazarus.x == endLeft) {
                        Lazarus.movingLeft = false;
                        return;
                    }
                }
            }
        }
        if (Lazarus.moveRight) {
            if (Lazarus.movingRight) {
                newX = lazarus.x + Globals.BLOCK_SIZE;
                oldX = lazarus.x;
                newY = lazarus.y;
                if (collision.validateCollision(newX, newY)) {
                    lazarus.x = oldX;
                } else {
                    lazarus.x = newX++;
                    if (lazarus.x == endRight) {
                        Lazarus.movingRight = false;
                        return;
                    }
                }
            }
        }
    }

    public void renderBoxes(Graphics2D g2) {
            Image image = Toolkit.getDefaultToolkit().getImage("resources/boxes/cardbox.png");
            g2.drawImage(image, box.getX(), box.getY(), this);
            g2.finalize();
    }

    public void moveBoxes() {
        if (collision.validateBoxestoWallCollision(box)
                || collision.validateLazarustoBoxesCollision(box, lazarus)) {
                box.y = box.getY();
            } else {
                box.moveBoxes();
            }


//        Iterator<Boxes> iter = boxes.iterator();
//        while (iter.hasNext()) {
//            Boxes box = iter.next();
//            if (collision.validateBoxestoWallCollision(box)) {
//                iter.remove();
//            } else {
//                box.moveBoxes();
//            }
//        }
    }

    public void renderBackground(Graphics2D g2) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/Background.png");
        g2.drawImage(image, 0, 0, Globals.BOARD_SIZE, Globals.BOARD_SIZE, this);
        g2.finalize();
    }

    public void findStartPosition() {
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