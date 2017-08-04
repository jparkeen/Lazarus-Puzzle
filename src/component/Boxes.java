package src.component;

import src.commons.Globals;

public class Boxes {

    private int x;

    public int y;

    public Boxes(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveBoxes() {
        int newX = x;
        int oldY = y;
        y = y + Globals.BOX_SPEED;


//        Point p = getNextPosition();
//        this.x = p.x;
//        this.y = 0;
//        y += Globals.BLOCK_SIZE;
    }

    public Point getNextPosition() {
        int newX = this.x;
        int newY = this.y + Globals.BLOCK_SIZE;
        return new Point(newX, newY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}