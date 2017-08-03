package src.component;

import src.commons.Globals;
import src.commons.MapReader;
import src.core.Lazarus;

import java.io.IOException;

public class CollisionDetector {

    private String[][] map;

    public CollisionDetector(String[][] map) throws IOException{
        this.map = map;
    }

    public boolean validateCollision(int newX, int newY) {
        return validateBoundaryCollision(newX, newY) || validateWallCollision(newX, newY);
    }

    private boolean validateBoundaryCollision(int newX, int newY){
        if(newX < 0 || newX > Globals.BOARD_SIZE - Globals.BLOCK_SIZE || newY < 0) {
            return true;
        }
        return false;
    }

    private boolean validateWallCollision(int newX, int newY){
        int mapX = newX / Globals.BLOCK_SIZE;
        int mapY = newY / Globals.BLOCK_SIZE;

        String value = map[mapY][mapX];

        if(value.equals(MapReader.WALL)) {
            return true;
        }
        return false;
    }

    public boolean validateBoxestoWallCollision(Boxes box){
        Point p = box.getNextPosition();
        int newX = p.x;
        int newY = p.y;

        int boxX = newX / (Globals.BLOCK_SIZE );
        int boxY = newY / (Globals.BLOCK_SIZE );

        String value = map[boxY][boxX];

        if(value.equals(MapReader.WALL)) {
            return true;
        }
        return false;
    }

    public boolean validateLazarustoBoxesCollision(Boxes box, LazarusObject lazarus){
//        Point p = box.getNextPosition();
        int newMinX = box.getX();
        int newMinY = box.y;
        int newMaxX = box.getX() + Globals.BLOCK_SIZE;
        int newMaxY = box.y + Globals.BLOCK_SIZE;

        int minLazX = lazarus.x;
        int maxLazX = lazarus.x + Globals.BLOCK_SIZE;
        int minLazY = lazarus.y;
        int maxLazY = lazarus.y + Globals.BLOCK_SIZE;

        if(Lazarus.moveRight) {
            return (minLazX < newMaxX && newMaxX < maxLazX && newMinY == minLazY);
        }
        if(Lazarus.moveLeft) {
            return (minLazX < newMinX && newMinX < maxLazX && newMinY == minLazY);
        }
        return false;



//        int lazX = lazarus.x;
//        int lazY = lazarus.y;
//
//        if(Lazarus.moveRight){
//            lazX += Globals.BLOCK_SIZE;
//            if(lazX == boxX && lazY == boxY) {
//                return true;
//            }
//        }
//        return false;
    }

}