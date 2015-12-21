package ru.allfound;

/**
 * Created by Vlad Laptev on 30.10.15.
 */
public class Ship extends BaseShip{

    //длина корабля
    int length;
    int lifeShip;

    // свойство говорит, что корабль расположен вертикально
    // isVertical - true, значит вертикально
    // isVertical - false, значит горизонтально
    boolean isVertical;


    Ship(int numShip, int l, int x, int y) {
        length = l; //
        lifeShip = l;
        this.x = x;
        this.y = y;
        this.numShip = numShip;
    }

    Ship() {

    }

    public int getLifeShip() {
        return lifeShip;
    }

    public void setLifeShip(int lifeShip) {
        this.lifeShip = lifeShip;
    }

    public void setIsVertical(boolean isVertical) {
        this.isVertical = isVertical;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }
}
