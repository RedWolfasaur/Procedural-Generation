package proceduralGeneration;

import java.util.ArrayList;
import java.util.Random;

public class Grid<T> {
    // fields
    // grid array as [y][x]
    private Item<T>[][] gridArray;
    // holds all possible items
    private ArrayList<Item<T>> itemList;

    /**
     * Makes a grid.
     */
    public Grid(int x, int y, ArrayList<Item<T>> items) {
        gridArray = new Item[y][x];
        itemList = items;

        for (int u = 0; u < gridArray.length; u++) {
            for (int c = 0; c < gridArray[u].length; c++) {
                gridArray[u][c] = new Item<>(null, c, u, itemList.size(),
                    itemList);
            }
        }
    }


    /**
     * Creates a brand new grid from a pre existing grid. It works.
     */
    public Grid(int x, int y, Grid oldGrid) {
        gridArray = new Item[y][x];
        itemList = oldGrid.getItems();

        for (int u = 0; u < gridArray.length; u++) {
            for (int c = 0; c < gridArray[u].length; c++) {
                try {
                    gridArray[u][c] = oldGrid.getGrid()[u - (x - oldGrid
                        .getY())][c];
                    oldGrid.getGrid()[u - (x - oldGrid.getY())][c].setXY(c, u);
                }
                catch (Exception e) {
                    // e.printStackTrace();
                    gridArray[u][c] = new Item<>(null, c, u, itemList.size(),
                        itemList);
                }
            }
        }
        for (int u = 0; u < gridArray.length; u++) {
            for (int c = 0; c < gridArray[u].length; c++) {
                if (gridArray[u][c].getCollapse() == 0) {
                    calculateSurroundings(c, u);
                }
            }
        }
    }


    public ArrayList<Item<T>> getItems() {
        return itemList;
    }


    public void clear() {
        for (int u = 0; u < gridArray.length; u++) {
            for (int c = 0; c < gridArray[u].length; c++) {
                gridArray[u][c] = new Item<>(null, c, u, itemList.size(),
                    itemList);
            }
        }
    }


    public int getX() {
        return gridArray[0].length;
    }


    public int getY() {
        return gridArray.length;
    }


    public Item<T>[][] getGrid() {
        return gridArray;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < gridArray.length; y++) {
            sb.append("[");
            for (int x = 0; x < gridArray[y].length; x++) {

                sb.append(gridArray[y][x].toString() + ", ");
            }
            sb.replace(sb.length() - 2, sb.length(), "");
            sb.append("]\n");
        }
        return sb.toString();
    }


    /**
     * return a list of items that can be around
     * if item is null, that means it is out of bounds
     * [Item 1, Item 2, Item 3, Item 4]
     * [North, East, South, West]
     * 
     * @param x
     * @param y
     * @return
     */
    public void calculateSurroundings(int x, int y) {
        boolean[] dirCheck = testDirection(x, y);
        // if looking at a place that has stuff, were looking around it
        if (gridArray[y][x].getCollapse() == 0) {
            for (int i = 0; i < 4; i++) {
                if (!dirCheck[i]) {
                    continue;
                }
                switch (i) {
                    case 0:
                        if (gridArray[y + 1][x].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundings(x, y + 1);
                        break;
                    case 1:
                        if (gridArray[y][x + 1].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundings(x + 1, y);
                        break;
                    case 2:
                        if (gridArray[y - 1][x].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundings(x, y - 1);
                        break;
                    case 3:
                        if (gridArray[y][x - 1].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundings(x - 1, y);
                        break;
                }
            }
            return;
            }
    
            ArrayList<Item<T>> acceptableNeighbors = new ArrayList<Item<T>>();
    
            for (Item ite : itemList) {
                acceptableNeighbors.add(ite);
            }
            for (int i = 0; i < 4; i++) {
                if (!dirCheck[i]) {
                    continue;
                }
                switch (i) {
                    case 0:
                        acceptableNeighbors.retainAll(gridArray[y + 1][x]
                            .getAcceptableNeighbors());
                        break;
                    case 1:
                        acceptableNeighbors.retainAll(gridArray[y][x + 1]
                            .getAcceptableNeighbors());
                        break;
                    case 2:
                        acceptableNeighbors.retainAll(gridArray[y - 1][x]
                            .getAcceptableNeighbors());
                        break;
                    case 3:
                        acceptableNeighbors.retainAll(gridArray[y][x - 1]
                            .getAcceptableNeighbors());
                        break;
                }
            }
    
            for (Item<T> it : gridArray[y][x].getAcceptableNeighbors()) {
                if (!acceptableNeighbors.contains(it)) {
                    gridArray[y][x].setAcceptableNeighbors(acceptableNeighbors);
    
                    calculateSurroundingsOfUncollapse(x, y, true);
    
                }
            }

    }


    /**
     * overload
     * 
     * @param x
     * @param y
     * @param prevChange
     */
    public void calculateSurroundingsOfUncollapse(
        int x,
        int y,
        boolean prevChange) {
        boolean[] dirCheck = testDirection(x, y);
        // if looking at a place that has stuff, were looking around it
        if (gridArray[y][x].getCollapse() == 0 || prevChange) {
            for (int i = 0; i < 4; i++) {
                if (!dirCheck[i]) {
                    continue;
                }
                switch (i) {
                    case 0:
                        if (gridArray[y + 1][x].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundingsOfUncollapse(x, y + 1, false);
                        break;
                    case 1:
                        if (gridArray[y][x + 1].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundingsOfUncollapse(x + 1, y, false);
                        break;
                    case 2:
                        if (gridArray[y - 1][x].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundingsOfUncollapse(x, y - 1, false);
                        break;
                    case 3:
                        if (gridArray[y][x - 1].getCollapse() == 0) {
                            break;
                        }
                        calculateSurroundingsOfUncollapse(x - 1, y, false);
                        break;
                }
            }
            return;
            }
    
            ArrayList<Item<T>> acceptableNeighbors = new ArrayList<Item<T>>();
    
            for (Item<T> ite : itemList) {
                acceptableNeighbors.add(ite);
            }
    
            for (int i = 0; i < 4; i++) {
                if (!dirCheck[i]) {
                    continue;
                }
                switch (i) {
                    case 0:
                        acceptableNeighbors.retainAll(gridArray[y + 1][x]
                            .getAcceptableNeighbors());
                        break;
                    case 1:
                        acceptableNeighbors.retainAll(gridArray[y][x + 1]
                            .getAcceptableNeighbors());
                        break;
                    case 2:
                        acceptableNeighbors.retainAll(gridArray[y - 1][x]
                            .getAcceptableNeighbors());
                        break;
                    case 3:
                        acceptableNeighbors.retainAll(gridArray[y][x - 1]
                            .getAcceptableNeighbors());
                        break;
                }
            }
            int size = acceptableNeighbors.size();
            for (int i = 0; i < size; i++) {
                acceptableNeighbors.addAll(acceptableNeighbors.get(i)
                    .getAcceptableNeighborsWeighted());
            }
    
            ArrayList<Item<T>> newList = (ArrayList<Item<T>>)itemList.clone();
            newList.retainAll(acceptableNeighbors);
    
            if (gridArray[y][x].getCollapse()==0)
            
            for (Item<T> it : gridArray[y][x].getAcceptableNeighbors()) {
                if (!newList.contains(it)) {
                    gridArray[y][x].setAcceptableNeighbors(newList);
    
                    calculateSurroundingsOfUncollapse(x, y, true);
    
                }
            }

    }


    /**
     * [N, E, S, W]
     */
    public boolean[] testDirection(int x, int y) {
        boolean[] list = new boolean[4];
        list[0] = (y + 1 < gridArray.length);
        list[1] = (x + 1 < gridArray[y].length);
        list[2] = (y - 1 >= 0);
        list[3] = (x - 1 >= 0);
        return list;

    }

    /**
     * This is pain. I would change it to match the one below, but that would be
     * more pain
     */
    public void add(int entry, int x, int y) throws Exception {
        add(itemList.get(entry), (x - 1), gridArray.length - (y));
    }


    public void add(Item<T> item, int x, int y) throws Exception {
        if (gridArray[y][x].getCollapse() == 0) {
            System.out.println("BREAK BREAK BREAK");
            throw new Exception(
                "Already an item where trying to add. Failure in add method");
        }
        gridArray[y][x] = item;
        gridArray[y][x].setXY(x, y);
        gridArray[y][x].setCollapse(0);
        calculateSurroundings(x, y);
    }


    public ArrayList<Item<T>> findLowest() {
        int lowest = Integer.MAX_VALUE;
        for (int y = 0; y < gridArray.length; y++) {
            for (int x = 0; x < gridArray[y].length; x++) {
                if (gridArray[y][x].getCollapse() < lowest && gridArray[y][x]
                    .getCollapse() != 0) {
                    lowest = gridArray[y][x].getCollapse();
                }
            }
        }

        ArrayList<Item<T>> lowList = new ArrayList<Item<T>>();
        for (int y = 0; y < gridArray.length; y++) {
            for (int x = 0; x < gridArray[y].length; x++) {
                if (gridArray[y][x].getCollapse() == lowest) {
                    lowList.add(gridArray[y][x]);
                }
            }
        }

        return lowList;
    }


    public void placeSquare() throws Exception {
        // for a random start
        Random rand = new Random();
        Item<T> toAdd = null;
        ArrayList<Item<T>> randList = findLowest();
        toAdd = randList.get(rand.nextInt(randList.size()));
        try {

            add(toAdd.getAcceptableNeighbors().get(rand.nextInt(toAdd
                .getAcceptableNeighbors().size())), toAdd.getX(), toAdd.getY());
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (findLowest().get(0).getCollapse() == 1) {
            for (Item<T> it : findLowest()) {
                try {
                    if (it.getAcceptableNeighbors().size() < 0) {
                        System.out.println(it.getAcceptableNeighborsWeighted());
                        System.exit(0);
                    }
                    add(it.getAcceptableNeighbors().get(rand.nextInt(it.getAcceptableNeighbors().size())), it.getX(), it.getY());
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public void placeWeightedSquare() throws Exception {

        // for a random start
        Random rand = new Random();
        Item<T> toAdd = null;
        ArrayList<Item<T>> randList = findLowest();
        // toAdd = randList.get(rand.nextInt(randList.size()));
        toAdd = randList.get(0);
        int x = toAdd.getX();
        int y = toAdd.getY();

        ArrayList<Item<T>> acceptableNeighbors = new ArrayList<Item<T>>();

        boolean[] dirCheck = testDirection(x, y);
        for (int i = 0; i < 4; i++) {
            if (!dirCheck[i]) {
                continue;
            }
            switch (i) {
                case 0:
                    acceptableNeighbors.addAll(gridArray[y + 1][x]
                        .getAcceptableNeighborsWeighted());
                    break;
                case 1:
                    acceptableNeighbors.addAll(gridArray[y][x + 1]
                        .getAcceptableNeighborsWeighted());
                    break;
                case 2:
                    acceptableNeighbors.addAll(gridArray[y - 1][x]
                        .getAcceptableNeighborsWeighted());
                    break;
                case 3:
                    acceptableNeighbors.addAll(gridArray[y][x - 1]
                        .getAcceptableNeighborsWeighted());
                    break;
            }
        }

        acceptableNeighbors.retainAll(toAdd.acceptableNeighbors);
        
        
        
        try {

            add(acceptableNeighbors.get(rand.nextInt(acceptableNeighbors
                .size())), x, y);
        }
        catch (Exception e) {
            placeSquare();
        }

        if (findLowest().get(0).getCollapse() == 1) {
            for (Item<T> it : findLowest()) {
                try {
                    if (it.getData() == null) {
                        add(new Item<T>(null, it.getX(), it.getY(), itemList.size(), itemList), it.getX(), it.getY());
                        break;
                    }
                    add(it.getAcceptableNeighbors().get(rand.nextInt(it.getAcceptableNeighbors().size())), it.getX(), it.getY());
                    
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
