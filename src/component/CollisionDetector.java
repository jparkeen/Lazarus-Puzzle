package component;

import commons.Globals;
import commons.MapReader;

import java.io.IOException;
import java.util.ArrayList;

public class CollisionDetector {

    private String[][] map;

    public CollisionDetector(String[][] map) throws IOException{
        this.map = map;
    }

    public boolean validateCollision(int newX, int newY, ArrayList<Box> boxes) {
        return validateLazarusToBoundaryCollision(newX, newY) || validateLazarusToWallCollision(newX, newY)
                || validateLazarustoBoxesCollision(boxes, newX, newY);
    }

    private boolean validateLazarusToBoundaryCollision(int newX, int newY){
        return (newX < 0 || newX > Globals.BOARD_SIZE - Globals.BLOCK_SIZE || newY < 0);
    }

    private boolean validateLazarusToWallCollision(int newX, int newY){
        int mapX = newX / Globals.BLOCK_SIZE;
        int mapY = newY / Globals.BLOCK_SIZE;

        String value = map[mapY][mapX];

        if(value.equals(MapReader.WALL)) {
            return true;
        }
        return false;
    }

    public boolean validateBoxToWallCollision(Box box){
        int newX = box.getX();
        int newY = box.getNextBoxDownPosition();

        int boxX = newX / (Globals.BLOCK_SIZE );
        int boxY = newY / (Globals.BLOCK_SIZE );

        String value = map[boxY][boxX];

        if(value.equals(MapReader.WALL)) {
            return true;
        }
        return false;
    }

    private boolean validateLazarustoBoxesCollision(ArrayList<Box> boxes, int newX, int newY){
        for(Box box : boxes) {
            if (newX == box.getX() && newY == box.getY()) {
                return true;
            }
        }
        return false;
    }

}