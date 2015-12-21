package ru.allfound;

import javax.swing.*;

/**
 * Created by Vlad Laptev on 21.11.15.
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
