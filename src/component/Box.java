package component;

import commons.Globals;
import commons.MapReader;

public class Box {

    /**
     * Current X position of box
     */
    private int x;

    /**
     * Current Y position of box
     */
    private int y;

    private String type;

    public Box(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void moveBoxDown() {
        assert x >= 0 : "X postion of box cannot be negative";
        assert y >= 0 : "Y postion of box cannot be negative";
        y += Globals.BOX_SPEED;
    }

    public int getNextBoxDownPosition() {
        return y + Globals.BLOCK_SIZE;
    }

    public static int getBoxPriority(String boxType) {
        int boxPriority = 0;
        if(boxType.equals(MapReader.CARDBOARD_BOX))  boxPriority = 0;
        if(boxType.equals(MapReader.WOOD_BOX)) boxPriority = 1;
        if(boxType.equals(MapReader.STONE_BOX)) boxPriority = 2;
        if(boxType.equals(MapReader.METAL_BOX)) boxPriority = 3;
        return boxPriority;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getBoxType() {
        return type;
    }

}