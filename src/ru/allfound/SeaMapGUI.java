package ru.allfound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/*
 * SeaMapGUI.java    v.1.1 01.12.2015
 *
 * Copyright (c) 2016 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class SeaMapGUI extends SeaMap {
    public final static int SIZEFRAMEY = 550;
    public static final int DELAY = 100;
    JLabel[] labelsShip;
    JLabel labelMessage;
    FindNextKick findNextKick;
    boolean shipAttacked = false; //флаг нажатия кнопки игроком
    boolean shipPCOut   = false; // корабли ПК уничтожены

    public SeaMapGUI(JLabel labelMessage, int numShip, boolean enableButton) {
        numberShip = numShip;
        countShip = numberShip;
        this.labelMessage = labelMessage;
        this.enableButton = enableButton;
        labelsShip = new JLabel[SIZEMAP];
        for (int i = 0; i < SIZEMAP; i++) {
            labelsShip[i] = new JLabel();
        }
        findNextKick = new FindNextKick();
        seaMap = new CellMap[SIZEMAP][SIZEMAP];
        arrayShip = new Ship[numberShip];
        //обнуляем массив
        for (int x = 0; x < SIZEMAP; x++) {
            for (int y = 0; y < SIZEMAP; y++) {
                seaMap[x][y] = new CellMap(x, y, 0, new JButton());
                seaMap[x][y].setEnableButton(enableButton);

                JButton button = seaMap[x][y].getButton();
                ActionListener actionListener = new buttonActionListener(seaMap[x][y]);
                button.addActionListener(actionListener);
            }
        }
    }

    private class buttonActionListener implements ActionListener {
        private CellMap cellMap;

        public buttonActionListener(CellMap cellMap) {
            super();
            this.cellMap = cellMap;
        }

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            int cell = cellMap.getCell();
            if (cell > 0) {
                button.setBackground(Color.ORANGE);
                int lifeShip = arrayShip[cell-1].getLifeShip();
                lifeShip--;
                if (lifeShip > 0){
                    arrayShip[cell-1].setLifeShip(lifeShip);
                    labelMessage.setText("Сообщения: кораблю " + cell + " нанесен урон");
                } else {
                    arrayShip[cell-1].setLifeShip(0);
                    showDestroyedShip(arrayShip[cell-1]); //показываем корабль и область вокруг него, если корабль уничтожен
                    labelMessage.setText("Сообщения: корабль " + cell + " уничтожен");
                    countShip--;
                    if (countShip == 0){ //если все корабли уничтожены
                        labelMessage.setText("Сообщения: все корабли уничтожены");
                        shipPCOut = true;
                        //скрываем все кнопки
                        for (int x = 0; x < SIZEMAP; x++){
                            for (int y = 0; y < SIZEMAP; y++){
                                seaMap[x][y].getButton().setEnabled(false);
                            }
                        }
                    }
                }
            } else {
                labelMessage.setText("Сообщения: мимо");
            }
            shipAttacked = true;
        }
    }

    //показываем корабль и область вокруг него, если корабль уничтожен
    public void showDestroyedShip(Ship ship) {
        int x = ship.x;
        int y = ship.y;
        int numShip = ship.numShip;
        int length = ship.length;
        int lifeShip = ship.lifeShip;
        boolean isVertical = ship.isVertical;
        int maxX = 0;
        int maxY = 0;
        if (x > 0) x--; //если не у левой границы
        if (y > 0) y--; //если не у верхней границы
        if(isVertical){
            if (ship.x < SIZEMAP-1) {//если не у правой границы
                maxX = ship.x;
                maxX++;
            } else {
                maxX = ship.x;
            }
            if (ship.y + length < SIZEMAP){//если не у нижней границы
                maxY = ship.y;
                maxY = maxY + length;
            } else {
                maxY = ship.y;
                maxY = maxY + length - 1;
            }
        }else{
            if (ship.x + length < SIZEMAP){//если не у правой границы
                maxX = ship.x;
                maxX = maxX + length;
            } else {
                maxX = ship.x;
                maxX = maxX + length - 1;
            }
            if (ship.y < SIZEMAP-1){//если не у нижней границы
                maxY = ship.y;
                maxY++;
            } else {
                maxY = ship.y;
            }
        }
        //скрываем кнопки по периметру корабля
        for (int cX = x; cX <= maxX; cX++){
            for (int cY = y; cY <= maxY; cY++){
                seaMap[cX][cY].getButton().setEnabled(false);
                seaMap[cX][cY].setCel(-1);
            }
        }
        //закрашиваем корабль красным
        if (isVertical) {
            for (int i = 0; i < length; i++) {
                seaMap[ship.x][ship.y + i].getButton().setBackground(Color.RED);
            }
        } else {
            for (int i = 0; i < length; i++) {
                seaMap[ship.x + i][ship.y].getButton().setBackground(Color.RED);
            }
        }
        //обнуляем соответствующий корабль в списке
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < arrayShip[numShip].getLength(); j++) {
            sb.append("-");
        }
        labelsShip[numShip].setText(sb.toString());
    }

    public int getNumberShip() {
        return numberShip;
    }

    public void setNumberShip(int numberShip) {
        this.numberShip = numberShip;
    }

    public boolean isEnableButton() {
        return enableButton;
    }

    public void setEnableButton(boolean enableButton) {
        this.enableButton = enableButton;
    }

    public void showShips(Container pane, int offsetX, int offsetY){
        for (int i = 0; i < numberShip; i++) {
            int numShip = arrayShip[i].getNumShip();
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < arrayShip[i].getLength(); j++) {
                //sb.append(numShip+1);
                sb.append("X");
                //sb.append(" ");
            }
            labelsShip[numShip].setText(sb.toString());
            pane.add(labelsShip[numShip]);
            Insets insets = pane.getInsets();
            Dimension size = labelsShip[numShip].getPreferredSize();
            labelsShip[numShip].setBounds(offsetX + insets.left, offsetY+ 5 + (40 * i) + insets.top,
                    size.width, size.height + 15);
        }
    }

    public void showMap(Container pane, int offsetX, int offsetY, boolean shipVisible) {
        pane.setLayout(null);
        //выводим номера по оси Х
        for (int i = 0; i < SIZEMAP; i++) {
            JLabel label = new JLabel();
            label.setText(Integer.toString(i+1));
            pane.add(label);
            Insets insets = pane.getInsets();
            Dimension size = label.getPreferredSize();
            label.setBounds(offsetX + 20 + (40 * i) + insets.left, offsetY-25 + insets.top,
                    size.width, size.height + 15);
        }
        //выводим номера по оси Y
        for (int i = 0; i < SIZEMAP; i++) {
            JLabel label = new JLabel();
            label.setText(Integer.toString(i+1));
            pane.add(label);
            Insets insets = pane.getInsets();
            Dimension size = label.getPreferredSize();
            label.setBounds(offsetX - 20 + insets.left, offsetY + 5 + (40 * i) + insets.top,
                    size.width, size.height + 15);
        }
        //выводим массив кнопок
        for (int x = 0; x < SIZEMAP; x++) {
            for (int y = 0; y < SIZEMAP; y++) {
                if(seaMap[x][y].getCell() >= 1 ) {
                    JButton button = seaMap[x][y].getButton();
                    if(shipVisible) {
                        button.setText("  ");
                        button.setBackground(Color.BLUE);
                    }else {
                        button.setText("  ");
                    }
                    pane.add(button);
                    Insets insets = pane.getInsets();
                    Dimension size = button.getPreferredSize();
                    button.setBounds(offsetX + (40 * x) + insets.left, offsetY + (40 * y) + insets.top,
                                    size.width, size.height + 15);
                    if (!seaMap[x][y].isEnableButton()) button.setEnabled(false);
                } else {
                    JButton button = seaMap[x][y].getButton();
                    button.setText("  ");
                    pane.add(button);
                    Insets insets = pane.getInsets();
                    Dimension size = button.getPreferredSize();
                    button.setBounds(offsetX + (40 * x) + insets.left, offsetY + (40 * y) + insets.top,
                                    size.width, size.height + 15);
                    if (!seaMap[x][y].isEnableButton()) button.setEnabled(false);
                }
            }
        }
    }
    //true - удар нанесен, false - все корабли уничтожены
    public boolean toAttack(){
        if (enableButton){
            while (shipAttacked == false) {//ожидаем нанесения удара игроком (нажатие кнопки)
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            shipAttacked = false;
            if (shipPCOut){
                return true;
            }
        } else { //ПК наносит ответный удар
            int x = 0;
            int y = 0;
            for (int i = 0; i < MAXITER; i++) {
                findNextKick.nextKick();
                x = findNextKick.nextX;
                y = findNextKick.nextY;
                int cell = seaMap[x][y].getCell();
                    if (cell > 0) { //если там корабль
                        int lifeShip = arrayShip[cell-1].getLifeShip();
                        lifeShip--;
                        if (lifeShip > 0){//если корабль еще не уничтожен
                            arrayShip[cell-1].setLifeShip(lifeShip);
                            seaMap[x][y].getButton().setBackground(Color.ORANGE);
                            labelMessage.setText("Сообщения: кораблю " + cell + " нанесен урон");
                            seaMap[x][y].setCel(-1);//отмечаем ячейку
                            findNextKick.shipKick = true;
                            findNextKick.pastKick = false;
                            return false;
                        } else { //если корабль уничтожен
                            arrayShip[cell-1].setLifeShip(0);
                            seaMap[x][y].getButton().setBackground(Color.RED);
                            labelMessage.setText("Сообщения: корабль " + cell + " уничтожен");
                            seaMap[x][y].setCel(-1);
                            showDestroyedShip(arrayShip[cell-1]); //показываем корабль и область вокруг него, если корабль уничтожен
                            countShip--;
                            if (countShip == 0){ //если все корабли уничтожены
                                labelMessage.setText("Сообщения: все корабли уничтожены");
                                return true;
                            }
                            findNextKick.resetFlags();
                            return false;
                        }
                    }
                    if (cell == 0) { //если мимо
                        if (findNextKick.shipKick) {
                            findNextKick.pastKick = true;
                        }
                        seaMap[x][y].getButton().setText("x");
                        seaMap[x][y].setCel(-1);
                        labelMessage.setText("Сообщения: мимо");
                        return false;
                    }
                if (findNextKick.shipKick) {
                    findNextKick.pastKick = true;
                }
                //}
            }
        }
        return false;
    }

    private class FindNextKick {
        boolean shipKick    = false; //если было попадание
        boolean pastKick    = false; //если мимо после последующего удара
        private final static int nullKick    = 0; //удар нанесли впервые
        private final static int leftKick    = 1; //удар нанесли влево
        private final static int rightKick   = 2;
        private final static int upKick      = 3;
        private final static int downKick    = 4;
        private int directionKick = nullKick; //направление удара
        private int firstX; // координаты первой точки для удара
        private int firstY;
        private int nextX;
        private int nextY;

        public FindNextKick() {
        }

        private void resetFlags() {
            shipKick        = false; //если было попадание
            pastKick        = false; //если мимо после последующего удара
            directionKick   = nullKick; //направление удара
        }

        public void nextKick(){
            if (shipKick) { //если было попадание
                if (pastKick) { //если промазали на последующем ударе
                    switch (directionKick) {
                        case leftKick: //если ударяли слева
                            if (firstX < (SIZEMAP - 1)) { //если в пределах поля, то пробуем справа
                                nextX = firstX + 1;
                                nextY = firstY;
                                directionKick = rightKick;
                                break;
                            };
                            if (firstY > 0) { //если в пределах поля, то пробуем вверх
                                nextY = firstY - 1;
                                nextX = firstX;
                                directionKick = upKick;
                                break;
                            }
                            if (firstY < (SIZEMAP - 1)) {
                                nextY = firstY + 1;
                                nextX = firstX;
                                directionKick = downKick;
                                break;
                            }
                                break;
                        case rightKick: //если удараяли справа
                            if (firstY > 0) { //если в пределах поля, то пробуем вверх
                                nextY = firstY - 1;
                                nextX = firstX;
                                directionKick = upKick;
                                break;
                            }
                            if (firstY < (SIZEMAP - 1)) {
                                nextY = firstY + 1;
                                nextX = firstX;
                                directionKick = downKick;
                                break;
                            }
                            break;
                        case upKick:
                            if (firstY < (SIZEMAP - 1)) {
                                nextY = firstY + 1;
                                nextX = firstX;
                                directionKick = downKick;
                                break;
                            }
                            break;
                        case downKick:
                            break;
                    }
                } else { //если попали
                    switch (directionKick) {
                        case nullKick: //если первое попадание
                            if (nextX > 0) {
                                nextX--; //ударяем влево
                                directionKick = leftKick;
                            } else {
                                nextX++; //ударяем вправо
                                directionKick = rightKick;
                            }
                            break;
                        case leftKick:
                            if (nextX > 0) {
                                nextX--; //ударяем влево
                            } else {
                                nextX = firstX + 1;
                                directionKick = rightKick;
                            }
                            break;
                        case rightKick:
                            if (nextX < (SIZEMAP - 1)) { //если в пределах поля
                                nextX++;
                            } else { //если за границей поля, то ударяем с другой стороны
                                nextX = firstX - 1;
                                directionKick = leftKick;
                            }
                            break;
                        case upKick:
                            if (nextY > 0) {
                                nextY--; //ударяем вверх
                            } else {
                                nextY = firstY + 1;
                                directionKick = downKick;
                            }
                            break;
                        case downKick:
                            if (nextY < (SIZEMAP - 1)) { //если в пределах поля
                                nextY++;
                            } else { //если за границей поля, то ударяем с другой стороны
                                nextY = firstY - 1;
                                directionKick = upKick;
                            }
                            break;
                    }
                }
            } else { //если не попадали
                Random random = new Random();
                nextX = random.nextInt(SIZEMAP);
                nextY = random.nextInt(SIZEMAP);
                //сохраняем начальную точку удара
                firstX = nextX;
                firstY = nextY;
            }
        }
    }

    //сброс для начала игры
    public void resetSeaMap() {
        shipAttacked = false; //флаг нажатия кнопки игроком
        shipPCOut   = false; // корабли ПК уничтожены
        System.exit(0);
    }
}
