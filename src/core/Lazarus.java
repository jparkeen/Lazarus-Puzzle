package src.core;

import src.commons.Globals;
import src.commons.MapReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Lazarus extends JComponent {

//    private Thread thread;

    private String[][] map;

    public Lazarus() throws IOException {
        this.map = MapReader.readMap(Globals.MAP1_FILENAME);
//        setFocusable(true);

    }

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        renderBackground(g2);
        renderMap(g2);
    }

    public void renderBackground(Graphics2D g2) {
        Image image = Toolkit.getDefaultToolkit().getImage("resources/Background.png");
        g2.drawImage(image, 0, 0, Globals.BOARD_SIZE, Globals.BOARD_SIZE, this);
        g2.finalize();
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
                if (value.equals(MapReader.BUTTON)) {
                    renderButton(g2, x, y);
                    continue;
                }
                if (value.equals(MapReader.SPACE)) {
                    // Do nothing
                    continue;
                }
                if (value.equals(MapReader.LAZARUS)) {
                    drawLazarus(g2, x, y);
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

//    public void run() {
//        Thread me = Thread.currentThread();
//        while (thread == me) {
//            repaint();
//            try {
//                thread.sleep(15);
//            } catch (InterruptedException e) {
//                break;
//            }
//        }
//    }
//
//    public void start() {
//        thread = new Thread(this);
//        thread.setPriority(Thread.MIN_PRIORITY);
//        thread.start();
//    }

}