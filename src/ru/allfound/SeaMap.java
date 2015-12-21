package ru.allfound;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Vlad Laptev on 21.11.15.
 */
public class SeaMap {

    final int SIZEMAP = 10;    //размер игрового поля
    final int MAXSIZESHIP = 5; //максимальный размер корабля
    final int MAXITER = 100;   //максимальное количество возможных итерраций "бесконечных" циклов

    CellMap[][] seaMap;
    public Ship[] arrayShip;
    boolean enableButton = true;

    int numberShip = 3;
    int countShip;

    public SeaMap() {
    }

    public void clearSeaMap(){
        //обнуляем массив
        for (int x = 0; x < SIZEMAP; x++) {
            for (int y = 0; y < SIZEMAP; y++) {
                //CellMap cellMap = new CellMap(x, y, 0);
                seaMap[x][y].setCel(0);
            }
        }

    }

    void setShipToSeaMap(){
        int count = 0;
        for (int i = 0; i < numberShip; i++) {
            if (setShip(i)){
                System.out.println("Установили корабль: " + (i+1));
            } else {
                System.out.println("!!!Начинаем новую расстановку кораблей!!!");
                clearSeaMap();
                count++;
                i = -1;
                if (count>MAXITER) {
                    System.out.println("!!!Корабли не установлены!!!");
                    break;
                }

            }
        }
    }

    //функция ищет своободное место и устанавливает 1 корабль, ячейка заполняется в соответствии с номером корабля
    //возвращает -1, если не удалось разместить корабль
    public boolean setShip(int numShip){

        Random random = new Random();
        //получаем случайное направление расположение корабля
        boolean isVertical = random.nextBoolean();
        //if (isVertical) System.out.println("Корабль вертикальный");
        //else System.out.println("Корабль горизонтальный");
        //получаем случайную длину корабля
        int lengthShip = random.nextInt(MAXSIZESHIP-1)+1;
        System.out.println("Длина корабля: " + lengthShip);
        arrayShip[numShip] = new Ship(numShip, lengthShip, 0, 0);
        arrayShip[numShip].setVertical(isVertical);
        arrayShip[numShip].setLength(lengthShip);
        //получаем случайную координату для размещения корабля в зависимости от направления размещения
        if (isVertical){
            if (setVerticalShip(lengthShip, numShip)) return true;

        } else {
            if (setHorizontalShip(lengthShip, numShip)) return true;

        }

        return false;
    }

    //установка верктикального корабля на случайном месте
     public boolean setVerticalShip(int lengthShip, int numShip){

         Random random = new Random();

         for (int i = 0; i < MAXITER; i++) {
             int x = random.nextInt(SIZEMAP);
             //System.out.println("Координата X: " + x);
             int y = random.nextInt(SIZEMAP-lengthShip);
             //System.out.println("Координата Y: " + y);

             if (x == 0) { //если у левой границы
                 if (checkFreeArrayForShip( x, y, 2, lengthShip+2)){
                     setShipOnMap(x, y+1, 1, lengthShip, numShip);//
                     return true;
                 }

             } else {
                 if (checkFreeArrayForShip( x-1, y, x+1, lengthShip+2)){
                     setShipOnMap(x, y+1, 1, lengthShip, numShip);//
                     return true;
                 }
             }

         }

         return false;
     }

    //установка корабля
    void setShipOnMap(int xShip, int yShip, int sizeX, int sizeY, int numShip) {

        arrayShip[numShip].setNumShip(numShip);
        arrayShip[numShip].setX(xShip);
        arrayShip[numShip].setY(yShip);

        for (int x = xShip; x < (xShip+sizeX); x++){
            for (int y = yShip; y < (yShip+sizeY); y++){
                seaMap[x][y].setCel(numShip+1);
            }
        }

    }

    //проверка свободной области для расположения корабля, если свободно - true
    //x,y - начальные координаты; sizeX,sizeY - размер области
    boolean checkFreeArrayForShip(int xArray, int yArray, int sizeX, int sizeY){
        int maxX;
        if((xArray+sizeX) >= SIZEMAP) {
            maxX = SIZEMAP - 1;
        }else {
            maxX = xArray+sizeX;
        }
        int maxY;
        if ((yArray+sizeY) >= SIZEMAP) {
            maxY = SIZEMAP - 1;
        }else {
            maxY = yArray + sizeY;
        }

        for(int x = xArray; x <= maxX; x++){
            for (int y = yArray; y <= maxY; y++){
                if(seaMap[x][y].getCell() >= 1 ) return false;
            }
        }
        return true;
    }

    //установка горизонтального корабля на случайном месте
    public boolean setHorizontalShip(int lengthShip, int numShip){

        Random random = new Random();
        for (int i = 0; i < MAXITER; i++) {
            int x = random.nextInt(SIZEMAP-lengthShip);
            //System.out.println("Координата X: " + x);
            int y = random.nextInt(SIZEMAP);
            //System.out.println("Координата Y: " + y);

            if (y == 0) { //если у верхней границы
                if (checkFreeArrayForShip( x, y, lengthShip+2, 2)){
                    setShipOnMap(x+1, y, lengthShip, 1, numShip);//
                    return true;
                }

            } else {
                if (checkFreeArrayForShip( x, y-1, lengthShip+2, y+1)){
                    setShipOnMap(x+1, y, lengthShip, 1, numShip);//
                    return true;
                }
            }

        }

        return false;
    }

    //отображение игрового поля и кораблей для консольной реализации
    public void showMap()
    {

        System.out.print("    ");
        for (int i = 0; i < SIZEMAP; i++) {
            System.out.print(i+" ");
        }
        System.out.println();

        System.out.print("  +");
        for (int i = 0; i < SIZEMAP; i++) {
            System.out.print("--");
        }
        System.out.println("-+");

        for (int x = 0; x < SIZEMAP; x++) {
            System.out.print(x+" | ");
            for (int y = 0; y < SIZEMAP; y++) {
                if(seaMap[x][y].getCell() >= 1 ) {
                    System.out.print("X ");
                } else System.out.print("~ ");
            }
            System.out.println("|");
        }

        System.out.print("  +");
        for (int i = 0; i < SIZEMAP; i++) {
            System.out.print("--");
        }
        System.out.println("-+");

    }

    public int getCountShip() {
        return countShip;
    }

    public void setCountShip(int countShip) {
        this.countShip = countShip;
    }

    public boolean isEnableButton() {
        return enableButton;
    }

    public void setEnableButton(boolean enableButton) {
        this.enableButton = enableButton;
    }

    public int getNumberShip() {
        return numberShip;
    }

    public void setNumberShip(int numberShip) {
        this.numberShip = numberShip;
    }
}
