package src.component;

import src.commons.Globals;

public class Box {

    /**
     * Current X position of box
     */
    private int x;

    /**
     * Current Y position of box
     */
    private int y;

    /**
     * True if the box has not reached the floor
     */
    private boolean moving;

    public Box(int x, int y) {
        this.x = x;
        this.y = y;
        this.moving = true;
    }

    public void moveBoxDown() {
        assert x >= 0 : "X postion of box cannot be negative";
        assert y >= 0 : "Y postion of box cannot be negative";
        y += Globals.BOX_SPEED;
    }

    public int getNextBoxDownPosition() {
        return y + Globals.BLOCK_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void stopMoving() {
        moving = false;
    }

}