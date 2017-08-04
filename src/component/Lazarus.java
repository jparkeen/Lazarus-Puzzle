package component;

import core.LazarusWorld;

public class Lazarus {

    public int x, y;

    public int health;

    public int lives;

    public LazarusWorld lazarus;

    public Lazarus(int x,int y, int health, int lives, LazarusWorld lazarus){
        this.x = x;
        this.y = y;
        this.health = health;
        this.lives = lives;
        this.lazarus = lazarus;
    }



}