package commons;

import component.Box;
import component.Lazarus;

import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class SpawnBoxes extends TimerTask {

    private List<Box> boxes;

    private Lazarus lazarus;

    private String[] ALL_BOXES;

    /**
     * Next box type to fall down after current box
     */
    private String nextBoxType;

    public SpawnBoxes(List<Box> boxes, Lazarus lazarus) {
        this.boxes = boxes;
        this.lazarus = lazarus;
        this.ALL_BOXES = MapReader.ALL_BOX_SET.toArray(new String[MapReader.ALL_BOX_SET.size()]);
        this.nextBoxType = ALL_BOXES[getRandomIndex()];
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(ALL_BOXES.length);
    }

    public void run() {
        synchronized (boxes) {
            // Add box to drop
            boxes.add(new Box(lazarus.x, 0, nextBoxType));

            // Set next box
            nextBoxType = ALL_BOXES[getRandomIndex()];
        }
    }

    public String getNextBoxType() {
        return this.nextBoxType;
    }
}
