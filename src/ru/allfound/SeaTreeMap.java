package ru.allfound;

import java.util.*;

/**
 * Created by vvv on 26.11.15.
 * "Модификация Морского боя":
 1) Перевести инициализацию игрового поля на Map (выбрать и обосновать вариант Map-ы)
 2) Перевести список кораблей на List
 3) Усложнить структуру корабля, переведя клетки корабля на LinkedList
 4) Реализовать сортировку кораблей, с помощью Comparable на основе их длины.
 5) Реализовать сортировку кораблей, с помощью Comparator на основе их текущей жизни.
 */
public class SeaTreeMap {
    final int SIZEMAP = 10;    //размер игрового поля
    final int MAXSIZESHIP = 5; //максимальный размер корабля
    final int MAXITER = 500;   //максимальное колчисетво возможных итерраций "бесконечных" циклов
    //int sumShip; //количество кораблей
    //private CellMap[][] seaMap;
    //private Ship[] arrayShip;

    List<Ship> listShip;
    Iterator listIterator;

    //HashMap<Integer,CellMap> hashMap;
    TreeMap<Integer,CellMap> treeMap;

    public SeaTreeMap(int numberShip) {
        listShip = new LinkedList<Ship>();
        listIterator = listShip.listIterator(listShip.size());
        //hashMap = new HashMap<Integer,CellMap>();
        treeMap = new TreeMap<Integer,CellMap>();

        int x = 0, y = 0;
        for (int i = 0; i < SIZEMAP*SIZEMAP; i++) {
            if (x == SIZEMAP) {
                x = 0; y++;
            }
            //treeMap.put(i, new CellMap(x, y, 0));
            x++;
        }

        // нет такой функции previous()?????????????
        //while (listIterator.previous());
    }

    public void clearSeaMap() {
        //обнуляем массив
        int x = 0, y = 0;
        for (Map.Entry<Integer, CellMap> entry : treeMap.entrySet() ) {
            entry.getValue().setCel(0);
        }
    }


}
