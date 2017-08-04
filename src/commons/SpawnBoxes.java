package commons;

import component.Box;
import component.Lazarus;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class SpawnBoxes extends TimerTask {

    private ArrayList<Box> boxes;

    private Lazarus lazarus;

    private static final String[] ALL_BOXES  = {MapReader.CARDBOARD_BOX, MapReader.WOOD_BOX,
            MapReader.STONE_BOX, MapReader.METAL_BOX};

    public SpawnBoxes(ArrayList<Box> boxes, Lazarus lazarus) {
        this.boxes = boxes;
        this.lazarus = lazarus;
    }

    public void run() {
        // To get a random number for 4 boxes from 1 to 4
        Random rand = new Random();
        int boxIndex = rand.nextInt(4);
        String boxType = ALL_BOXES[boxIndex];
        boxes.add(new Box(lazarus.x, 0, boxType));
    }
}
