package core;

import commons.Globals;
import commons.MapReader;
import commons.SpawnBoxes;
import component.Box;
import component.CollisionDetector;
import component.KeysControl;
import component.Lazarus;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.List;

public class LazarusWorld extends JComponent implements Runnable {

    private Thread thread;

    public static boolean moveLeft,moveRight,jump ,movingUp,movingLeft,movingRight,jumpingLeft,jumpingRight,falling;

    private KeysControl keysControl;

    private Lazarus lazarus;

    private List<Box> boxes;

    private SpawnBoxes spawnBoxes;

    public  static int startX,startY;

    int health = 20, lives = 2;

    public static  int width = 50, height = 100,jumpTop,endLeft,endRight,startFalling;

    private CollisionDetector collision;

    private String[][] map;

    public LazarusWorld() throws IOException {
        this.map = MapReader.readMap(Globals.MAP1_FILENAME);
        this.boxes = new ArrayList<Box>();

        setFocusable(true);

        collision = new CollisionDetector(map);

        findStartPosition();

        lazarus = new Lazarus(startX, startY, health, lives,this);

        this.keysControl = new KeysControl(this.lazarus);
        addKeyListener(keysControl);

        this.spawnBoxes = new SpawnBoxes(boxes, lazarus);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // For background
        renderBackground(g2);
        renderLevel(g2);
        renderMap(g2);

        // For Lazarus
        drawLazarus(g2, lazarus.x, lazarus.y);

        // For boxes
        synchronized (boxes) {
            moveBoxes();
            renderBoxes(g2);
        }
        // Render next box
        renderNextBox(g2);

        // Read key press from user
        handleMovement(g2);
    }

    private void renderNextBox(Graphics2D g2) {
        renderBox(g2, spawnBoxes.getNextBoxType(), 0, 700);
    }

    public void handleMovement(Graphics g){
        int newX;
        if(moveRight) {

            if (movingRight) {
                newX = lazarus.x + Globals.BLOCK_SIZE;

                // lazarus can't move up if there is more than one box
                if(collision.validateLazarusCollision(newX,lazarus.y - Globals.BLOCK_SIZE)){
                    movingRight = false;
                    return;
                }

                // if there is collision then lazarus moves up one box at a time
                if (collision.validateLazarusCollision(newX, lazarus.y)) {
                    lazarus.y = lazarus.y - Globals.BLOCK_SIZE;
                    lazarus.x = newX;
                } else {
                    lazarus.x = newX++;
                }

                //start falling
                
                while(!collision.validateLazarusCollision(lazarus.x,lazarus.y+Globals.BLOCK_SIZE)){
                    lazarus.y++;
                }


                if (lazarus.x == endRight) {
                    movingRight = false;
                    return;
                }
            }
        }

        if (moveLeft) {

            if (movingLeft) {
                newX = lazarus.x - Globals.BLOCK_SIZE;

                if(collision.validateLazarusCollision(newX,lazarus.y - Globals.BLOCK_SIZE)){
                    movingLeft = false;
                    return;
                }

                if (collision.validateLazarusCollision(newX, lazarus.y)) {
                    lazarus.y = lazarus.y - Globals.BLOCK_SIZE;
                    lazarus.x = newX;
                } else {
                    lazarus.x = newX--;
                }

                while(!collision.validateLazarusCollision(lazarus.x,lazarus.y+Globals.BLOCK_SIZE)){
                    lazarus.y++;
                }

                if (lazarus.x == endLeft) {
                    movingLeft = false;
                    return;
                }
            }
        }

    }


    private void renderBoxes(Graphics2D g2) {
        for (Box box : boxes) {
            renderBox(g2, box.getBoxType(), box.getX(), box.getY());
        }
    }

    private void renderBox(Graphics2D g2, String boxType, int newX, int newY) {
        Image image = null;
        if(boxType.equals(MapReader.CARDBOARD_BOX)) {
            image = Toolkit.getDefaultToolkit().getImage("resources/boxes/cardbox.png");
        } else if(boxType.equals(MapReader.WOOD_BOX)) {
            image = Toolkit.getDefaultToolkit().getImage("resources/boxes/woodbox.png");
        } else if(boxType.equals(MapReader.STONE_BOX)) {
            image = Toolkit.getDefaultToolkit().getImage("resources/boxes/stonebox.png");
        } else if(boxType.equals(MapReader.METAL_BOX)) {
            image = Toolkit.getDefaultToolkit().getImage("resources/boxes/metalbox.png");
        } else {
            System.err.println("Unknown Block Type : " + boxType);
        }
        g2.drawImage(image, newX, newY, Globals.BLOCK_SIZE, Globals.BLOCK_SIZE, this);
        g2.finalize();
    }

    public void moveBoxes() {
        Iterator<Box> itr = boxes.iterator();
        Box box;
        while(itr.hasNext()) {
            box = itr.next();
            if (collision.validateBoxToWallCollision(box)) {
                map[box.getY() / Globals.BLOCK_SIZE][box.getX() / Globals.BLOCK_SIZE] = box.getBoxType();
                itr.remove();
            } else if (collision.validateBoxToBoxCollision(box)) {
                // If there is box to box collision there are three possiblilities
                // 1. Heavy box (Priority higher) is on top of light box (Priority lower)
                // 2. Light box (Priority lower) is on top of heavy box (Priority higher)
                // 3. Both boxes of same type (same priority lower)
                stopBoxToBoxOnCollision(box, itr);
            } else {
                box.moveBoxDown();
            }
        }
    }

    /**
     * At this point the collision has already happned with another and we need to take some action.
     * Stops the box if it collides with another stationary box already on the floor
     */
    private void stopBoxToBoxOnCollision(Box currentBox, Iterator<Box> itr) {
        int newX = currentBox.getX();
        int newY = currentBox.getNextBoxDownPosition();
        String bottomBoxType = collision.getMapping(newX, newY);
        // Get box priorities
        int bottomBoxPriority = Box.getBoxPriority(bottomBoxType);
        int currentBoxPriority = currentBox.getBoxPriority(currentBox.getBoxType());

        if(bottomBoxPriority >= currentBoxPriority) {
            // Dont break bottom box and stop current box
            map[currentBox.getY() / Globals.BLOCK_SIZE][currentBox.getX() / Globals.BLOCK_SIZE] = currentBox.getBoxType();
            itr.remove();
        } else {
            // Break bottom box
            map[newY / Globals.BLOCK_SIZE][newX / Globals.BLOCK_SIZE] = MapReader.SPACE;
        }
    }

    public void renderBackground(Graphics2D g2) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/Background.png");
        g2.drawImage(image, 0, 0, Globals.BOARD_SIZE, Globals.BOARD_SIZE, this);
        g2.finalize();
    }

    public void renderLevel(Graphics2D g2) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/levels/level1.png");
        g2.drawImage(image, 250, 70, 300, 115, this);
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
                if (MapReader.ALL_BOX_SET.contains(value)) {
                    renderBox(g2, value, x , y);
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
        if (collision.validateLazarustoBoxesCollision(lazarus.x, lazarus.y)) {
            Image image = Toolkit.getDefaultToolkit().getImage("resources/lazarus/Lazarus_squished.gif");
            g2.drawImage(image, x, y, Globals.BLOCK_SIZE, Globals.BLOCK_SIZE, this);
            g2.finalize();
        } else {
            Image image = Toolkit.getDefaultToolkit().getImage("resources/lazarus/Lazarus_stand.png");
            g2.drawImage(image, x, y, Globals.BLOCK_SIZE, Globals.BLOCK_SIZE, this);
            g2.finalize();
        }
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

         // Spawn boxes by using timer
        Timer timer = new Timer();
        timer.schedule(spawnBoxes, 0, 2000);
    }

}