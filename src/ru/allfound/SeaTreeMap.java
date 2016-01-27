package ru.allfound;

import java.util.*;

/*
 * SeaTreeMap.java    v.1.0 26.11.2015
 *
 * Copyright (c) 2015 Vladislav Laptev,
 * All rights reserved. Used by permission.
 */

public class SeaTreeMap {
    final int SIZEMAP = 10;    //размер игрового поля
    List<Ship> listShip;
    Iterator listIterator;
    TreeMap<Integer,CellMap> treeMap;

    public SeaTreeMap(int numberShip) {
        listShip = new LinkedList<Ship>();
        listIterator = listShip.listIterator(listShip.size());
        treeMap = new TreeMap<Integer,CellMap>();
        int x = 0, y = 0;
        for (int i = 0; i < SIZEMAP*SIZEMAP; i++) {
            if (x == SIZEMAP) {
                x = 0; y++;
            }
            x++;
        }
    }

    public void clearSeaMap() {
        //обнуляем массив
        int x = 0, y = 0;
        for (Map.Entry<Integer, CellMap> entry : treeMap.entrySet() ) {
            entry.getValue().setCel(0);
        }
    }
}
