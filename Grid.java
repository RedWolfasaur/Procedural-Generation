package proceduralGeneration;

import java.util.ArrayList;
import java.util.Iterator;
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

    Item<T> prevModified;

    /**
     * Makes a grid.
     */
    public Grid(int x, int y, ArrayList<Item<T>> items) {
	rand = new Random();
	seed = rand.nextLong();
	rand = new Random(seed);

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
	this(x, y, oldGrid, 0);
    }

    /**
     * 0-3, 0 correspond to up right, 1 corrspond right, down
     * (as in the map will add new space up/downs)
     */
    public Grid(int x, int y, Grid oldGrid, int dir) {
	gridArray = new Item[y][x];
	itemList = oldGrid.getItems();
	ArrayList<Item<T>> t = new ArrayList<Item<T>>();
	t.addAll(itemList.subList(0, itemList.size() - 1));

	seed = oldGrid.getSeed();
	rand = new Random(seed);

	// this is what i want
	switch (dir) {
	case 0:
	    for (int u = 0; u < gridArray.length; u++) {
		for (int c = 0; c < gridArray[u].length; c++) {
		    try {
			gridArray[u][c] = oldGrid.getGrid()[u - (y - oldGrid.getY())][c];
			oldGrid.getGrid()[u - (y - oldGrid.getY())][c].setXY(c, u);
		    } catch (Exception e) {
			
			gridArray[u][c] = new Item<>(null, c, u, itemList.size() - 1, t);
			
		    }
		}
		
	    }
	    break;
	case 1:
	    for (int u = 0; u < gridArray.length; u++) {
		for (int c = 0; c < gridArray[u].length; c++) {
		    try {
			gridArray[u][c] = oldGrid.getGrid()[u][c - (x - oldGrid.getX())];
			oldGrid.getGrid()[u][c - (x - oldGrid.getX())].setXY(c, u);
		    } catch (Exception e) {
			// e.printStackTrace();
			gridArray[u][c] = new Item<>(null, c, u, itemList.size(), t);
			
		    }
		}
	    }
	    break;
	}
	// checks the number stuff
	
	for (int u = 0; u < gridArray.length; u++) {
	    for (int c = 0; c < gridArray[u].length; c++) {
		
		if (gridArray[u][c].getCollapse() == 0) {
		    calculateSurroundings(c, u);
		}
		if (c == oldGrid.getGrid()[0].length) {
		    break;
		}
	    }
	    if (u == oldGrid.getGrid().length) {
		break;
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
	    acceptableNeighbors.retainAll(gridArray[y + 1][x].acceptableNeighborsWeighted);
	}
	if (dirCheck[1]) {
	    acceptableNeighbors.retainAll(gridArray[y][x + 1].getAcceptableNeighbors());
	    acceptableNeighbors.retainAll(gridArray[y][x + 1].acceptableNeighborsWeighted);
	}
	if (dirCheck[2]) {
	    acceptableNeighbors.retainAll(gridArray[y - 1][x].getAcceptableNeighbors());
	    acceptableNeighbors.retainAll(gridArray[y - 1][x].acceptableNeighborsWeighted);
	}
	if (dirCheck[3]) {
	    acceptableNeighbors.retainAll(gridArray[y][x - 1].getAcceptableNeighbors());
	    acceptableNeighbors.retainAll(gridArray[y][x - 1].acceptableNeighborsWeighted);
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
		requiredValues.retainAll(gridArray[y + 1][x].getAcceptableNeighborsWeighted());
	    } else {
		acceptableNeighbors.addAll(gridArray[y + 1][x].getAcceptableNeighbors());
	    }
	}
	if (dirCheck[1]) {
	    if (gridArray[y][x + 1].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y][x + 1].getAcceptableNeighborsWeighted());
	    } else {
		acceptableNeighbors.addAll(gridArray[y][x + 1].getAcceptableNeighbors());
	    }
	}
	if (dirCheck[2]) {
	    if (gridArray[y - 1][x].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y - 1][x].getAcceptableNeighborsWeighted());
	    } else {
		acceptableNeighbors.addAll(gridArray[y - 1][x].getAcceptableNeighbors());
	    }
	}
	if (dirCheck[3]) {
	    if (gridArray[y][x - 1].getCollapse() == 0) {
		requiredValues.retainAll(gridArray[y][x - 1].getAcceptableNeighborsWeighted());
	    } else {
		acceptableNeighbors.addAll(gridArray[y][x - 1].getAcceptableNeighbors());
	    }
	}

	int size = acceptableNeighbors.size();
	for (int i = 0; i < size; i++) {
	    acceptableNeighbors.addAll(acceptableNeighbors.get(i).getAcceptableNeighborsWeighted());
	}

	gridArray[y][x].setAcceptableNeighbors(requiredValues);

	if (gridArray[y][x].getCollapse() == 0
		&& !(requiredValues.containsAll(gridArray[y][x].getAcceptableNeighbors()))) {
	    calculateSurroundingsOfUncollapse(x, y, true);
	}

    }

    /**
     * [N, E, S, W]
     */
    public boolean[] testDirection(int x, int y) {
	boolean[] list = new boolean[4];
	list[0] = (y + 1 < gridArray.length);
	list[1] = (x + 1 < gridArray[0].length);
	list[2] = (y - 1 >= 0);
	list[3] = (x - 1 >= 0);
	return list;

    }

    /**
     */
    public void add(int entry, int x, int y) throws ItemExistsException {
	add(itemList.get(entry), x, y);
    }

    public void add(Item<T> item, int x, int y) throws ItemExistsException {
	if (gridArray[y][x].getCollapse() == 0) {
	    throw new ItemExistsException("Already an item where trying to add. Failure in add method");
	}
	gridArray[y][x] = new Item<T>(item);

	gridArray[y][x].setXY(x, y);
	gridArray[y][x].setCollapse(0);
	calculateSurroundings(x, y);
	prevModified = gridArray[y][x];
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

    public void remove(int x, int y) {
	ArrayList<Item<T>> t = new ArrayList<Item<T>>();
	t.addAll(itemList.subList(0, itemList.size() - 1));
	
	/*
	boolean[] dirCheck = testDirection(x, y);
	if (dirCheck[0] && (gridArray[y + 1][x].getData() != null)) {
	    gridArray[y][x] = new Item<T>(gridArray[y + 1][x].getData(), x, y + 1, itemList.size() - 1, t);
	    calculateSurroundings(x, y + 1);

	}
	if (dirCheck[1] && (gridArray[y][x+1].getData() != null)) {
	    gridArray[y][x + 1] = new Item<T>(gridArray[y][x+1].getData(), x + 1, y, itemList.size() - 1, t);
	    calculateSurroundings(x + 1, y);

	}
	if (dirCheck[2] && (gridArray[y - 1][x].getData() != null)) {
	    gridArray[y - 1][x] = new Item<T>(gridArray[y - 1][x].getData(), x, y - 1, itemList.size() - 1, t);
	    calculateSurroundings(x, y-1);

	}
	if (dirCheck[3] && (gridArray[y][x - 1].getData() != null)) {
	    gridArray[y][x - 1] = new Item<T>(gridArray[y][x - 1].getData(), x - 1, y, itemList.size() - 1, t);
	    calculateSurroundings(x - 1, y);
	}
	
	*/
	boolean[] dirCheck = testDirection(x, y);
	if (dirCheck[0]) {
	    gridArray[y + 1][x] = new Item<T>(null, x, y + 1, itemList.size() - 1, t);
	    calculateSurroundings(x, y + 1);

	}
	if (dirCheck[1]) {
	    gridArray[y][x + 1] = new Item<T>(null, x + 1, y, itemList.size() - 1, t);
	    calculateSurroundings(x + 1, y);

	}
	if (dirCheck[2]) {
	    gridArray[y - 1][x] = new Item<T>(null, x, y - 1, itemList.size() - 1, t);
	    calculateSurroundings(x, y - 1);

	}
	if (dirCheck[3]) {
	    gridArray[y][x - 1] = new Item<T>(null, x - 1, y, itemList.size() - 1, t);
	    calculateSurroundings(x - 1, y);
	}
	
	
	

    }

    public void error(Item<T> toAdd) throws ItemExistsException {

	ArrayList<Item<T>> t = new ArrayList<Item<T>>();
	t.addAll(itemList.subList(0, itemList.size() - 1));
	remove(toAdd.getX(), toAdd.getY());
	// add(itemList.size() - 1, toAdd.getX(), toAdd.getY());
    }

    public void placeSquare() throws FullGridException {
	// for a random start
	Item<T> toAdd = null;
	ArrayList<Item<T>> randList = findLowest();
	toAdd = randList.get(rand.nextInt(randList.size()));
	int x = toAdd.getX();
	int y = toAdd.getY();

	if (toAdd.getAcceptableNeighbors().size() == 0 && toAdd.getCollapse() != 0) {
	    try {
		error(toAdd);
		// throw new FullGridException("356");
	    } catch (ItemExistsException e) {
		e.printStackTrace();
		// throw new FullGridException("359");
	    }
	}
	try {
	    if (toAdd.getAcceptableNeighbors().size() < 1) {
		try {
		    error(toAdd);
		    // throw new FullGridException("366");

		} catch (Exception e1) {
		    e1.printStackTrace();
		    // throw new FullGridException("369");
		}
	    }
	    add(toAdd.getAcceptableNeighbors().get(rand.nextInt(toAdd.getAcceptableNeighbors().size())), toAdd.getX(),
		    toAdd.getY());

	} catch (ItemExistsException e3) {
	    toAdd.setCollapse(0);
	    return;
	} catch (Exception e) {
	    try {
		error(toAdd);
		// throw new FullGridException("381");
	    } catch (Exception e1) {
		e1.printStackTrace();
		// throw new FullGridException("384");
	    }
	    // e.printStackTrace();
	}

	if (findLowest().get(0).getCollapse() == 1) {
	    for (Item<T> it : findLowest()) {
		try {
		    add(it.getAcceptableNeighbors().get(rand.nextInt(it.getAcceptableNeighbors().size())), it.getX(),
			    it.getY());

		} catch (Exception e) {
		    try {
			error(it);
		    } catch (ItemExistsException e3) {
			return;
		    } catch (Exception e2) {
			e2.printStackTrace();
			// throw new FullGridException("AA");
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
		    e1.printStackTrace();
		    // throw new FullGridException("451");
		} catch (Exception e2) {
		    e2.printStackTrace();
		    // throw new FullGridException("AA");
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
		    add(it.getAcceptableNeighbors().get(rand.nextInt(it.getAcceptableNeighbors().size())), x, y);

		} catch (Exception e) {
		    try {
			error(it);
			// throw new FullGridException("AA");
		    } catch (Exception e2) {
			e2.printStackTrace();
		    }
		}

	    }
	}

    }

    public Iterator<Item<T>> gridIterator() {
	return new GridIterator();
    }

    private class GridIterator implements Iterator<Item<T>> {
	int currentX;
	int currentY;

	int maxX;
	int maxY;

	public GridIterator() {
	    currentX = -1;
	    currentY = 0;
	    maxX = gridArray[0].length;
	    maxY = gridArray.length;
	}

	public boolean hasNext() {
	    if (currentX + 1 == maxX && currentY + 1 == maxY) {
		return false;
	    }
	    return true;
	}

	@Override
	public Item<T> next() {
	    currentX++;
	    if (currentX == maxX) {
		currentX = 0;
		currentY++;
	    }

	    return gridArray[currentY][currentX];
	}

    }
}
