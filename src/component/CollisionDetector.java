package src.component;

import src.commons.Globals;
import src.commons.MapReader;

import java.io.IOException;

public class CollisionDetector {

    private String[][] map;

    public CollisionDetector(String[][] map) throws IOException{
        this.map = map;
    }

    public boolean validateCollision(int newX, int newY, LazarusObject lazarus) {
        return validateBoundaryCollision(newX, newY) || validateWallCollision(newX, newY);
    }

    public boolean validateBoundaryCollision(int newX, int newY){
        if(newX < 0 || newX > Globals.BOARD_SIZE - Globals.BLOCK_SIZE || newY < 0) {
            return true;
        }
        return false;
    }


    public boolean validateWallCollision(int newX, int newY){
        int mapX = newX / Globals.BLOCK_SIZE;
        int mapY = newY / Globals.BLOCK_SIZE;

        String value = map[mapY][mapX];

        if(value.equals(MapReader.WALL)) {
            return true;
        }
        return false;
    }

}
