package proceduralGeneration;

import java.awt.Color;
import java.util.ArrayList;

public class Runner {

    /*
     * Ideas for tomorrow Work on paths? Weights in specific directions.
     */

    public static void main(String[] args) {

	ArrayList<Item<Color>> list = new ArrayList<Item<Color>>();
	//Item<Color> grass = new Item<Color>(Color.GREEN);
	//Item<Color> grass = new Item<Color>(Color.WHITE);
	Item<Color> grass = new Item<Color>(new Color(98, 166, 107));
	Item<Color> forest = new Item<Color>(new Color(28, 77, 41));
	//Item<Color> forest = new Item<Color>(new Color(98, 166, 107));
	Item<Color> sand = new Item<Color>(Color.ORANGE);
	Item<Color> ocean = new Item<Color>(Color.BLUE);
	Item<Color> coast = new Item<Color>(new Color(39, 153, 219));
	Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);
	Item<Color> error = new Item<Color>(new Color(255, 0, 195)); //error can be set to anything
	//error = sand; 
	
//	ArrayList<Item<String>> list = new ArrayList<Item<String>>();
//	Item<String> grass = new Item<String>("G");
//	Item<String> forest = new Item<String>("F");
//	Item<String> sand = new Item<String>("S");
//	Item<String> coast = new Item<String>("W");
//	Item<String> mountains = new Item<String>("M");
//	Item<String> ocean = new Item<String>("O");
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
	
	ocean.add(coast, 20);
	ocean.add(ocean, 80);
	//ocean.add(sand, 0); //this helps prevent some errors + not be giant fuck-off islands.
	//ocean.add(grass, 0); 

	mountains.add(grass, 20);
	mountains.add(mountains, 30);

	list.add(grass);
	list.add(sand);
	list.add(coast);
	list.add(forest);
	list.add(mountains);
	list.add(ocean);

	//make sure error is at the end of the list.
	for (Item item : list) {
	    error.add(item);
	}

	
	list.add(error);

	//Grid<String> grid = new Grid<String>(5, 5, list);

//	 Grid<Color> grid = new Grid<Color>(100, 100, list);

	// somegood seeds: 10, 
	//200x200: 381516884956544432, -3793075882536655115, -4195626676420015224
	//150x150: 4279102694807672599, 42456485255286715
	 
	 //testing -1918627471075256442
	GUI<Item<Color>> window = new GUI<Item<Color>>(list, 100, 100, -1918627471075256442L);

//	System.out.println(grid);
//	try {
//	    grid.add(5, 0, 0);
//	    grid.add(2, 0, 1);
//	} catch (ItemExistsException e) {
//	    e.printStackTrace();
//	}
//	//System.out.println(grid.getGrid()[0][0].getAcceptableNeighbors());
//	//System.out.println(grid.getGrid()[0][0].getAcceptableNeighborsWeighted());
//	//System.out.println(grid.getGrid()[0][0].getCollapse());
//	System.out.println(grid);
	
    }

}
