package ru.allfound;

import javax.swing.*;

/*
 * CellMap.java    v.1.0 21.11.2015
 *
 * Copyright (c) 2015 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class CellMap {
    int x;
    int y;
    int cell;
    private JButton button;
    private boolean enableButton = true;
    public CellMap(int x, int y, int cell, JButton button) {
        this.x = x;
        this.y = y;
        this.cell = cell;
        this.button = button;
    }

    public CellMap() {
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public boolean isEnableButton() {
        return enableButton;
    }

    public void setEnableButton(boolean enableButton) {
        this.enableButton = enableButton;
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

    public int getCell() {
        return cell;
    }

    public void setCel(int cell) {
        this.cell = cell;
    }
}
