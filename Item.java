package proceduralGeneration;

import java.util.ArrayList;

public class Item<T> {
    T data;
    int x;
    int y;
    int collapse;
    int weight;

    ArrayList<Item<T>> acceptableNeighbors;
    ArrayList<Item<T>> acceptableNeighborsWeighted;

    public Item(T entry, int x, int y) {
        data = entry;
        this.x = x;
        this.y = y;
        acceptableNeighbors = new ArrayList<Item<T>>();
        acceptableNeighborsWeighted = new ArrayList<Item<T>>();
        collapse = 0;
    }


    public Item(T entry) {
        data = entry;
        acceptableNeighbors = new ArrayList<Item<T>>();
        acceptableNeighborsWeighted = new ArrayList<Item<T>>();
    }


    public Item(T entry, int x, int y, int co, ArrayList<Item<T>> list) {
        data = entry;
        collapse = co;
        this.x = x;
        this.y = y;
        acceptableNeighbors = list;
        acceptableNeighborsWeighted = new ArrayList<Item<T>>();
    }
    
    public void clear() {
	data = null;
	acceptableNeighbors = new ArrayList<Item<T>>();
        acceptableNeighborsWeighted = new ArrayList<Item<T>>();
    }
    
    public T getData() {
        return data;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Item<T> item) {
        acceptableNeighbors.add(item);
    }
    
    public void add(Item<T> item, int weight) {
        acceptableNeighbors.add(item);
        for (int i = 0; i < weight; i++) {
            acceptableNeighborsWeighted.add(item);
        }
    }


    public void setCollapse(int co) {
        collapse = co;
    }


    public int getCollapse() {
        return collapse;
    }


    public ArrayList<Item<T>> getAcceptableNeighbors() {
        return acceptableNeighbors;
    }
    
    public ArrayList<Item<T>> getAcceptableNeighborsWeighted() {
        return acceptableNeighborsWeighted;
    }


    public void setAcceptableNeighbors(ArrayList<Item<T>> acN) {
        acceptableNeighbors = acN;
        collapse = acN.size();
        if (collapse == 0) {
            collapse++;
        }
    }
    
    public void printArrayList(ArrayList<Item<T>> acN) {
        for (Item<T> x : acN) {
            System.out.println(x);
        }
    }


    public String toString() {
        if (collapse == 0) {
            return data.toString();
        }
        return "" + collapse;
    }
}
