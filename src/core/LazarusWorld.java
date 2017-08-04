package src.core;

import src.commons.Globals;
import src.commons.SpawnBoxes;
import src.component.Box;
import src.component.CollisionDetector;
import src.component.KeysControl;
import src.component.Lazarus;
import src.commons.MapReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class LazarusWorld extends JComponent implements Runnable {

    private Thread thread;

    public static boolean moveLeft,moveRight,jump ,movingUp,movingLeft,movingRight,jumpingLeft,jumpingRight;

    private KeysControl keysControl;

    private Lazarus lazarus;

    private ArrayList<Box> boxes;

    public  static int startX,startY;

    int health = 20, lives = 2;

    public static  int width = 50, height = 50,jumpTop,endLeft,endRight;

    private CollisionDetector collision;

    private String[][] map;

    public LazarusWorld() throws IOException {
        this.map = MapReader.readMap(Globals.MAP1_FILENAME);
        this.boxes = new ArrayList<Box>(1000);

        setFocusable(true);

        collision = new CollisionDetector(map);

        findStartPosition();

        lazarus = new Lazarus(startX, startY, health, lives,this);

        this.keysControl = new KeysControl(this.lazarus);
        addKeyListener(keysControl);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        renderBackground(g2);
        renderMap(g2);

        drawLazarus(g2, lazarus.x, lazarus.y);

        moveBoxes();
        renderBoxes(g2);

        handleMovement(g2);
    }

    public void handleMovement(Graphics g) {
        int newX, newY;
        int oldX, oldY;

        if (jump) {
            if (jumpingLeft) {
                if (movingUp) {

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
                        movingUp = false;
                        return;
                    }
                }
                if (!movingUp) {
                    lazarus.y++;
                    if (lazarus.y == startY) {
                        jump = false;
                    }
                }
            } else if (jumpingRight) {
                if (movingUp) {

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

                        LazarusWorld.movingUp = false;
                        return;
                    }
                }
                if (!movingUp) {
                    lazarus.y++;
                    if (lazarus.y == startY) {
                        jump = false;
                    }
                }
            } else {
                if (movingUp) {

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
                        LazarusWorld.movingUp = false;
                        return;
                    }
                }
                if (!movingUp) {
                    lazarus.y++;
                    if (lazarus.y == startY) {
                        jump = false;
                    }
                }
            }
        }
        if (moveLeft) {
            if (movingLeft) {
                newX = lazarus.x - Globals.BLOCK_SIZE;
                oldX = lazarus.x;
                newY = lazarus.y;
                if (collision.validateCollision(newX, newY)) {
                    lazarus.x = oldX;
                } else {
                    lazarus.x = newX--;
                    if (lazarus.x == endLeft) {
                        movingLeft = false;
                        return;
                    }
                }
            }
        }
        if (moveRight) {
            if (movingRight) {
                newX = lazarus.x + Globals.BLOCK_SIZE;
                oldX = lazarus.x;
                newY = lazarus.y;
                if (collision.validateCollision(newX, newY)) {
                    lazarus.x = oldX;
                } else {
                    lazarus.x = newX++;
                    if (lazarus.x == endRight) {
                        movingRight = false;
                        return;
                    }
                }
            }
        }
    }

    public void renderBoxes(Graphics2D g2) {
        for(Box box : boxes) {
            Image image = Toolkit.getDefaultToolkit().getImage("resources/boxes/cardbox.png");
            g2.drawImage(image, box.getX(), box.getY(), Globals.BLOCK_SIZE, Globals.BLOCK_SIZE,this);
            g2.finalize();
        }
    }

    public void moveBoxes() {
        // Stop moving boxes that have collided with the wall
        for(Box box: boxes) {
            if (collision.validateBoxToWallCollision(box)) {
                box.stopMoving();
//                System.out.println("Y: " + box.getY() + "X: " + box.getX() + "\n");
                MapReader.BOX = map[box.getY() / Globals.BLOCK_SIZE][box.getX() / Globals.BLOCK_SIZE];
                continue;
            }
            box.moveBoxDown();
        }
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
                    startX = x;
                    startY = y;
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
                    startX = x;
                    startY = y;
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

        Timer timer = new Timer();
        timer.schedule(new SpawnBoxes(boxes, lazarus), 0, 3000);
    }

}