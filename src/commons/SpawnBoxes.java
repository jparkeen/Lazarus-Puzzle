package src.commons;

import src.component.Box;
import src.component.Lazarus;

import java.util.ArrayList;
import java.util.TimerTask;

public class SpawnBoxes extends TimerTask {

    private ArrayList<Box> boxes;

    private Lazarus lazarus;

    public SpawnBoxes(ArrayList<Box> boxes, Lazarus lazarus) {
        this.boxes = boxes;
        this.lazarus = lazarus;
    }

    public void run() {
        boxes.add(new Box(lazarus.x, 0));
    }
}
