package commons;

import component.Box;
import component.Lazarus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpawnBoxes extends TimerTask {

    private List<Box> boxes;

    private int boxarray[] = new int[100];

    private Lazarus lazarus;

    int count = 0;

    private static final String[] ALL_BOXES  = {MapReader.CARDBOARD_BOX, MapReader.WOOD_BOX,
            MapReader.STONE_BOX, MapReader.METAL_BOX};

    public SpawnBoxes(List<Box> boxes, Lazarus lazarus) {
        this.boxes = boxes;
        this.lazarus = lazarus;

      for (int i = 0; i < boxarray.length; i++) {
        Random rand = new Random();
        int n = rand.nextInt(4);
        boxarray[i] = n;
      }
    }

    public void run() {
        int boxIndex = boxarray[count];
        String boxType = ALL_BOXES[boxIndex];

        count++;
        int nextBoxIndex = boxarray[count];
        String nextBoxType = ALL_BOXES[nextBoxIndex];

        synchronized (boxes) {
            boxes.add(new Box(0,700, nextBoxType));
            boxes.add(new Box(lazarus.x, 0, boxType));
        }
    }
}
