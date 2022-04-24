package proceduralGeneration;

import java.awt.Color;
import java.util.ArrayList;

public class Runner {

    /*
     * Ideas for tomorrow Work on paths? Weights in specific directions.
     */

    public static void main(String[] args) throws FullGridException {

	ArrayList<Item<Color>> list = new ArrayList<Item<Color>>();
	//Item<Color> grass = new Item<Color>(Color.GREEN);
	Item<Color> grass = new Item<Color>(Color.WHITE);
	//Item<Color> forest = new Item<Color>(new Color(28, 77, 41));
	Item<Color> forest = new Item<Color>(new Color(98, 166, 107));
	Item<Color> sand = new Item<Color>(Color.ORANGE);
	Item<Color> ocean = new Item<Color>(Color.BLUE);
	Item<Color> coast = new Item<Color>(new Color(39, 153, 219));
	Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);
	Item<Color> error = new Item<Color>(new Color(255, 0, 195));

//	ArrayList<Item<String>> list = new ArrayList<Item<String>>();
//	Item<String> grass = new Item<String>("G");
//	Item<String> forest = new Item<String>("F");
//	Item<String> sand = new Item<String>("S");
//	Item<String> water = new Item<String>("W");
//	Item<String> mountains = new Item<String>("M");
//	Item<String> error = new Item<String>("!");

	grass.add(sand, 20);
	grass.add(grass, 50);
	grass.add(forest, 30);
	grass.add(mountains, 10);

	forest.add(grass, 40);
	forest.add(forest, 60);

	//sand.add(grass, 30);
	//sand.add(water, 20);
	//sand.add(sand, 50);
	sand.add(grass, 50);
	sand.add(coast, 5);
	sand.add(sand, 40);

	coast.add(sand, 20);
	coast.add(coast, 90);
	coast.add(ocean, 30);
	
	ocean.add(coast, 30);
	ocean.add(ocean, 80);
	ocean.add(sand, 0);

	mountains.add(grass, 20);
	mountains.add(mountains, 30);

	list.add(grass);
	list.add(sand);
	list.add(coast);
	list.add(forest);
	list.add(mountains);
	list.add(ocean);

	for (Item item : list) {
	    error.add(item);
	}

	
	list.add(error);

//	Grid<String> grid = new Grid<String>(5, 5, list);

//	 Grid<Color> grid = new Grid<Color>(100, 100, list);

	// somegood seeds: 10, 
	//200x200: 381516884956544432, -3793075882536655115
	//150x150: 4279102694807672599
	GUI<Item<Color>> window = new GUI<Item<Color>>(list, 150, 150);

//	System.out.println(grid);
//	grid.placeWeightedSquare();
//	System.out.println(grid);
//	grid.placeWeightedSquare();
//	System.out.println(grid);
//	grid.placeWeightedSquare();
//	System.out.println(grid);
//	grid.placeWeightedSquare();
//	System.out.println(grid);
//	grid.placeWeightedSquare();
//	System.out.println(grid);
//
//	try {
//	    grid.add(1, 0, 0);
//	    grid.add(0, 0, 1);
//	    grid.add(0, 0, 2);
//	    grid.add(0, 1, 0);
//	    grid.add(0, 1, 1);
//	    grid.add(0, 1, 2);
//	    grid.add(0, 2, 0);
//	    grid.add(0, 2, 1);
//	    grid.add(0, 2, 2);
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
//
//	System.out.println(grid);
//	try {
//	    while (true) {
//		grid.placeWeightedSquare();
//	    }
//	    
//	} catch (Exception e) {
//	    System.out.println(grid);
//	    e.printStackTrace();
//	}
//	
//	grid = new Grid<String>(5, 5, grid);
//	System.out.println(grid);
//	//System.out.println(grid.getGrid()[1][1].getAcceptableNeighbors());
//	
//	try {
//		grid.placeWeightedSquare();
//	    
//	} catch (Exception e) {
//	    System.out.println(grid);
//	    e.printStackTrace();
//	}
//	System.out.println(grid);
//	System.out.println(grid.getGrid()[1][1].getAcceptableNeighbors());

    }

}
