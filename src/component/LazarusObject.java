package src.component;

import src.core.Lazarus;


public class LazarusObject {
    public int x,y;
    public int health,lives;
    public Lazarus lazarus;


    public LazarusObject(int x,int y,int health,int lives,Lazarus lazarus){
        this.x = x;
        this.y = y;
        this.health = health;
        this.lives = lives;
        this.lazarus = lazarus;
    }
}
