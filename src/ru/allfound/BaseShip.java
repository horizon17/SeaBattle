package ru.allfound;

/**
 * Created by vvv on 30.10.15.
 */
public class BaseShip {
    //номер корабля прописывается в каждую ячейку
    //если -1, то ячейка корабля уничтожена
    int numShip;
    //координаты
    int x;
    int y;

    // Конструктор - отвечает за создание объекта
    // У него нет возвращаемого значения, имя совпадает с именем класса
    // Внутри него указываем, какие свойства заданы
    // Использовать его можно так Ship ship = new Ship(4, 5, 3)
    // тогда номер корабля будет 4, а координаты (x=5, y=3)
    BaseShip(int numShip, int x, int y) {
        this.numShip = numShip; //
        this.x = x;
        this.y = y;
    }

    BaseShip() {

    }

    public int getNumShip() {
        return numShip;
    }

    public void setNumShip(int numShip) {
        this.numShip = numShip;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
