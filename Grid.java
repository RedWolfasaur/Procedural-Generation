package proceduralGeneration;

import java.util.ArrayList;
import java.util.Random;

public class Grid<T> {
    // fields
    // grid array as [y][x]
    private Item<T>[][] gridArray;
    // holds all possible items.
    // THGE LAST IS AN ERROR HOZLDER
    private ArrayList<Item<T>> itemList;

    private long seed;

    Random rand;

    /**
     * Makes a grid.
     */
    public Grid(int x, int y, ArrayList<Item<T>> items) {
	rand = new Random();

	gridArray = new Item[y][x];
	itemList = items;
	ArrayList<Item<T>> t = new ArrayList<Item<T>>();
	t.addAll(itemList.subList(0, itemList.size() - 1));
	for (int u = 0; u < gridArray.length; u++) {
	    for (int c = 0; c < gridArray[u].length; c++) {

		gridArray[u][c] = new Item<>(null, c, u, itemList.size() - 1, t);
	    }
	}
    }

    public Grid(int x, int y, ArrayList<Item<T>> items, long seed) {
	this(x, y, items);
	this.seed = seed;
	rand = new Random(seed);
    }

    /**
     * Creates a brand new grid from a pre existing grid. It works.
     */
    public Grid(int x, int y, Grid oldGrid) {
	gridArray = new Item[y][x];
	itemList = oldGrid.getItems();
	ArrayList<Item<T>> t = new ArrayList<Item<T>>();
	t.addAll(itemList.subList(0, itemList.size() - 1));

	seed = oldGrid.getSeed();
	rand = new Random(seed);

	for (int u = 0; u < gridArray.length; u++) {
	    for (int c = 0; c < gridArray[u].length; c++) {
		try {
		    gridArray[u][c] = oldGrid.getGrid()[u - (x - oldGrid.getY())][c];
		    oldGrid.getGrid()[u - (x - oldGrid.getY())][c].setXY(c, u);
		} catch (Exception e) {
		    // e.printStackTrace();
		    gridArray[u][c] = new Item<>(null, c, u, itemList.size() - 1, t);
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

    public long getSeed() {
	return seed;
    }

    public ArrayList<Item<T>> getItems() {
	return itemList;
    }

    public void clear() {
	ArrayList<Item<T>> t = new ArrayList<Item<T>>();
	t.addAll(itemList.subList(0, itemList.size() - 1));
	for (int u = 0; u < gridArray.length; u++) {
	    for (int c = 0; c < gridArray[u].length; c++) {
		gridArray[u][c] = new Item<T>(null, c, u, itemList.size(), t);
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
     * return a list of items that can be around if item is null, that means it is
     * out of bounds [Item 1, Item 2, Item 3, Item 4] [North, East, South, West]
     * 
     * @param x
     * @param y
     * @return
     */
    public void calculateSurroundings(int x, int y) {
	boolean[] dirCheck = testDirection(x, y);
	// if looking at a place that has stuff, we're looking around it
	if (gridArray[y][x].getCollapse() == 0) {

	    if (dirCheck[0] && !(gridArray[y + 1][x].getCollapse() == 0)) {
		calculateSurroundings(x, y + 1);
	    }
	    if (dirCheck[1] && !(gridArray[y][x + 1].getCollapse() == 0)) {
		calculateSurroundings(x + 1, y);
	    }
	    if (dirCheck[2] && !(gridArray[y - 1][x].getCollapse() == 0)) {
		calculateSurroundings(x, y - 1);
	    }
	    if (dirCheck[3] && !(gridArray[y][x - 1].getCollapse() == 0)) {
		calculateSurroundings(x - 1, y);
	    }

	    return;
	}

	ArrayList<Item<T>> acceptableNeighbors = new ArrayList<Item<T>>();
	acceptableNeighbors.addAll(itemList.subList(0, itemList.size() - 1));

	if (dirCheck[0]) {
	    acceptableNeighbors.retainAll(gridArray[y + 1][x].getAcceptableNeighbors());
	}
	if (dirCheck[1]) {
	    acceptableNeighbors.retainAll(gridArray[y][x + 1].getAcceptableNeighbors());
	}
	if (dirCheck[2]) {
	    acceptableNeighbors.retainAll(gridArray[y - 1][x].getAcceptableNeighbors());
	}
	if (dirCheck[3]) {
	    acceptableNeighbors.retainAll(gridArray[y][x - 1].getAcceptableNeighbors());
	}

	if (gridArray[y][x].getAcceptableNeighbors().size() > acceptableNeighbors.size()
		&& !(acceptableNeighbors.containsAll(gridArray[y][x].getAcceptableNeighbors()))) {
	    gridArray[y][x].setAcceptableNeighbors(acceptableNeighbors);

	    calculateSurroundingsOfUncollapse(x, y, true);
	}

    }

    /**
     * 
     * 
     * @param x
     * @param y
     * @param prevChange
     */
    public void calculateSurroundingsOfUncollapse(int x, int y, boolean prevChange) {
	boolean[] dirCheck = testDirection(x, y);
	// if looking at a place that has stuff, were looking around it
	if (gridArray[y][x].getCollapse() == 0 || prevChange) {
	    if (dirCheck[0] && !(gridArray[y + 1][x].getCollapse() == 0)) {
		calculateSurroundingsOfUncollapse(x, y + 1, false);

	    }
	    if (dirCheck[1] && !(gridArray[y][x + 1].getCollapse() == 0)) {
		calculateSurroundingsOfUncollapse(x + 1, y, false);

	    }
	    if (dirCheck[2] && !(gridArray[y - 1][x].getCollapse() == 0)) {
		calculateSurroundingsOfUncollapse(x, y - 1, false);

	    }
	    if (dirCheck[3] && !(gridArray[y][x - 1].getCollapse() == 0)) {
		calculateSurroundingsOfUncollapse(x - 1, y, false);

	    }
	}

	ArrayList<Item<T>> acceptableNeighbors = new ArrayList<Item<T>>();
	ArrayList<Item<T>> requiredValues = new ArrayList<Item<T>>();
	requiredValues.addAll(itemList.subList(0, itemList.size() - 1));

	if (dirCheck[0]) {
	    if (gridArray[y + 1][x].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y + 1][x].getAcceptableNeighbors());
	    } else {
		acceptableNeighbors.addAll(gridArray[y + 1][x].getAcceptableNeighbors());
	    }
	}
	if (dirCheck[1]) {
	    if (gridArray[y][x + 1].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y][x + 1].getAcceptableNeighbors());
	    } else {
		acceptableNeighbors.addAll(gridArray[y][x + 1].getAcceptableNeighbors());
	    }
	}
	if (dirCheck[2]) {
	    if (gridArray[y - 1][x].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y - 1][x].getAcceptableNeighbors());
	    } else {
		acceptableNeighbors.addAll(gridArray[y - 1][x].getAcceptableNeighbors());
	    }
	}
	if (dirCheck[3]) {
	    if (gridArray[y][x - 1].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y][x - 1].getAcceptableNeighbors());
	    } else {
		acceptableNeighbors.addAll(gridArray[y][x - 1].getAcceptableNeighbors());
	    }
	}

	int size = acceptableNeighbors.size();
	for (int i = 0; i < size; i++) {
	    acceptableNeighbors.addAll(acceptableNeighbors.get(i).getAcceptableNeighbors());
	}

	ArrayList<Item<T>> newList = new ArrayList<Item<T>>();
	newList.addAll(itemList.subList(0, itemList.size() - 1));
	newList.retainAll(acceptableNeighbors);

//	if (((gridArray[y][x].getCollapse() != itemList.size() - 1 && gridArray[y][x].getCollapse() != 0))
//		|| requiredValues.size() == 1) {
//	    gridArray[y][x].setAcceptableNeighbors(requiredValues);
//	}
	gridArray[y][x].setAcceptableNeighbors(requiredValues); // if the program starts bugging, uncomment above and
								// remove this

	if (gridArray[y][x].getCollapse() == 0) {
	    if (!(requiredValues.containsAll(gridArray[y][x].getAcceptableNeighbors()))) {

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
     */
    public void add(int entry, int x, int y) throws ItemExistsException {
	add(itemList.get(entry), (x), y);
    }

    public void add(Item<T> item, int x, int y) throws ItemExistsException {
	if (gridArray[y][x].getCollapse() == 0) {
	    System.out.println("BREAK BREAK BREAK");
	    throw new ItemExistsException("Already an item where trying to add. Failure in add method");
	}
	gridArray[y][x] = item;
	gridArray[y][x].setXY(x, y);
	gridArray[y][x].setCollapse(0);
	calculateSurroundings(x, y);
    }

    public ArrayList<Item<T>> findLowest() throws FullGridException {
	int lowest = Integer.MAX_VALUE;
	for (int y = 0; y < gridArray.length; y++) {
	    for (int x = 0; x < gridArray[y].length; x++) {
		if (gridArray[y][x].getCollapse() < lowest && gridArray[y][x].getCollapse() != 0) {
		    lowest = gridArray[y][x].getCollapse();
		    if (lowest == 1) {
			break;
		    }
		}
	    }
	}

	if (lowest == Integer.MAX_VALUE) {
	    throw new FullGridException("Grid is full.");
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

    public void placeSquare() throws FullGridException {
	// for a random start
	Item<T> toAdd = null;
	ArrayList<Item<T>> randList = findLowest();
	toAdd = randList.get(rand.nextInt(randList.size()));
	if (toAdd.getAcceptableNeighbors().size() == 0 && toAdd.getCollapse() != 0) {
	    try {
		add(itemList.size() - 1, toAdd.getX(), toAdd.getY());
	    } catch (ItemExistsException e) {
		e.printStackTrace();
	    }
	}
	try {
	    if (toAdd.getAcceptableNeighbors().size() < 1) {
		try {
		    add(itemList.size() - 1, toAdd.getX(), toAdd.getY());
		} catch (Exception e1) {
		    e1.printStackTrace();
		}
	    }
	    add(toAdd.getAcceptableNeighbors().get(rand.nextInt(toAdd.getAcceptableNeighbors().size())), toAdd.getX(),
		    toAdd.getY());

	} catch (ItemExistsException e3) {
	    toAdd.setCollapse(0);
	    return;
	} catch (Exception e) {
	    try {
		add(itemList.size() - 1, toAdd.getX(), toAdd.getY());
	    } catch (Exception e1) {
		e1.printStackTrace();
	    }
	    e.printStackTrace();
	}

	if (findLowest().get(0).getCollapse() == 1) {
	    for (Item<T> it : findLowest()) {
		try {
		    add(it.getAcceptableNeighbors().get(rand.nextInt(it.getAcceptableNeighbors().size())), it.getX(),
			    it.getY());

		} catch (Exception e) {
		    try {
			add(itemList.size() - 1, it.getX(), it.getY());
		    } catch (ItemExistsException e3) {
			return;
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }

		}

	    }
	}

    }

    public void placeWeightedSquare() throws FullGridException {
	// for a random start
	Item<T> toAdd = null;
	ArrayList<Item<T>> randList = findLowest();
	toAdd = randList.get(rand.nextInt(randList.size()));
	// toAdd = randList.get(0);
	int x = toAdd.getX();
	int y = toAdd.getY();

	ArrayList<Item<T>> acceptableNeighbors = new ArrayList<Item<T>>();

	boolean[] dirCheck = testDirection(x, y);

	if (dirCheck[0]) {
	    acceptableNeighbors.addAll(gridArray[y + 1][x].getAcceptableNeighborsWeighted());
	}
	if (dirCheck[1]) {
	    acceptableNeighbors.addAll(gridArray[y][x + 1].getAcceptableNeighborsWeighted());
	}
	if (dirCheck[2]) {
	    acceptableNeighbors.addAll(gridArray[y - 1][x].getAcceptableNeighborsWeighted());
	}
	if (dirCheck[3]) {
	    acceptableNeighbors.addAll(gridArray[y][x - 1].getAcceptableNeighborsWeighted());
	}

	acceptableNeighbors.retainAll(toAdd.acceptableNeighbors);

	try {

	    add(acceptableNeighbors.get(rand.nextInt(acceptableNeighbors.size())), x, y);
	} catch (Exception e) {
	    try {
		placeSquare();
	    } catch (Exception e1) {
		try {
		    add(itemList.size() - 1, x, y);
		} catch (Exception e2) {
		    e2.printStackTrace();
		}
	    }
	}

	if (findLowest().size() == 0) {
	    throw new FullGridException("Full Grid");
	}
	if (findLowest().get(0).getCollapse() == 1) {
	    for (Item<T> it : findLowest()) {
		try {
		    if (it.getData() == null) {
			dirCheck = testDirection(x, y);
			if (dirCheck[0]) {
			    acceptableNeighbors.addAll(gridArray[y + 1][x].getAcceptableNeighborsWeighted());
			}
			if (dirCheck[1]) {
			    acceptableNeighbors.addAll(gridArray[y][x + 1].getAcceptableNeighborsWeighted());
			}
			if (dirCheck[2]) {
			    acceptableNeighbors.addAll(gridArray[y - 1][x].getAcceptableNeighborsWeighted());
			}
			if (dirCheck[3]) {
			    acceptableNeighbors.addAll(gridArray[y][x - 1].getAcceptableNeighborsWeighted());
			}

			break;
		    }
		    add(it.getAcceptableNeighbors().get(rand.nextInt(it.getAcceptableNeighbors().size())), it.getX(),
			    it.getY());

		} catch (Exception e) {
		    try {
			add(itemList.size() - 1, x, y);
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }
		}

	    }
	}

    }

}
