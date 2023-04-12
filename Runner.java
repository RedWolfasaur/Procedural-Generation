package proceduralGeneration;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Runner {

	/*
	 * Ideas for tomorrow Work on paths? Weights in specific directions.
	 */

	public static void main(String[] args) {

		ArrayList<Item<Color>> list = new ArrayList<Item<Color>>();
		// Item<Color> grass = new Item<Color>(Color.GREEN);
		// Item<Color> grass = new Item<Color>(Color.WHITE);
		Item<Color> grass = new Item<Color>(new Color(98, 166, 107)); //standard grass
		Item<Color> forest = new Item<Color>(new Color(32, 107, 54)); //standard color
		// Item<Color> forest = new Item<Color>(new Color(28, 77, 41));
		// Item<Color> forest = new Item<Color>(new Color(98, 166, 107));
		Item<Color> sand = new Item<Color>(Color.ORANGE);
		Item<Color> ocean = new Item<Color>(Color.BLUE);
		Item<Color> coast = new Item<Color>(new Color(39, 153, 219));
		Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);
		Item<Color> error = new Item<Color>(new Color(255, 0, 195)); // error can be set to anything
		// error = sand;

		/*
		 * ArrayList<Item<String>> list = new ArrayList<Item<String>>(); Item<String>
		 * grass = new Item<String>("G"); Item<String> forest = new Item<String>("F");
		 * Item<String> sand = new Item<String>("S"); Item<String> coast = new
		 * Item<String>("W"); Item<String> mountains = new Item<String>("M");
		 * Item<String> ocean = new Item<String>("O"); Item<String> error = new
		 * Item<String>("!");
		 */

		grass.add(sand, 20);
		grass.add(grass, 50);
		grass.add(forest, 30);
		grass.add(mountains, 10);

		forest.add(grass, 40);
		forest.add(forest, 60);

		// sand.add(grass, 30);
		// sand.add(water, 20);
		// sand.add(sand, 50);
		sand.add(grass, 50);
		sand.add(coast, 5);
		sand.add(sand, 20);

		//coast.add(sand, 20);
		coast.add(sand, 30);
		coast.add(coast, 30);
		coast.add(ocean, 20);

		ocean.add(coast, 30);
		ocean.add(ocean, 40);

		mountains.add(grass, 20);
		mountains.add(mountains, 30);

		list.add(grass);
		list.add(sand);
		list.add(coast);
		list.add(forest);
		list.add(mountains);
		list.add(ocean);

		// make sure error is at the end of the list.
		for (Item item : list) {
			error.add(item);
		}

		list.add(error);

//	Grid<String> grid = new Grid<String>(100, 100, list, 5176930254552009688L);

		// some good seeds:
		// let me note, increasing the size will increase the time it takes. Should be
		// obvious though.
		// 300,300: 8096394144706093395
		// 200x200: 8206209682205551009, -627377344974473140
		// 150x150:
		// 100x100: -1918627471075256442, 5176930254552009688, 7794176660258022025,
		// 5208855863618711882

		// testing
		// GUI<Item<Color>> window = new GUI<Item<Color>>(list, 200, 200);
		// 53056. 51650

		// much faster to generate small amounts at a time.

		long startTime;
		int i = 0;
		int size;
		Grid<Color> grid;
		int gridSizeX;
		int gridSizeY;
		gridList<Grid<Color>> gridList;
		NewGUI gui;
		Grid<Color> newGrid;
		
		while (i < 10) {
		startTime = System.currentTimeMillis();

		size = 64;

		// Grid<Color> grid = new Grid<Color>(5, 5, list, 8096394144706093395L);
			
		//Great seed: 8096394144706093395L
		grid = new Grid<Color>(8, 8, list);
		
//		Grid<Color> grid2 = new Grid<Color>(8, 8, list, -1440018342256226307L);
//		
//		Grid<Color> grid3 = new Grid<Color>(8, 8, list, 4021294702084439534L);
//		
//		Grid<Color> grid4 = new Grid<Color>(8, 8, list, -8014805041337925577L);
//		
//		Grid<Color> grid5 = new Grid<Color>(8, 8, list, -4250183992250339034L);
		
		System.out.println("Starting");
		
		grid = grid.generateGridSmall(size, size);
//		grid2 = grid2.generateGridSmall(size, size);
//		grid3 = grid3.generateGridSmall(size, size);
//		grid4 = grid4.generateGridSmall(size, size);
//		grid5 = grid5.generateGridSmall(size, size);



		gridSizeX = 5;
		gridSizeY = 5;

		//prospective seeds
		/*
		 * 6793835212766707987
		 * -7781491848298825424
		 * 683476537010087445 //choice #1
		 * 
		 * */
		gridList = new gridList<Grid<Color>>(gridSizeX, gridSizeY);

		gridList.addGridToPos(0, 0, grid);
		
//		gridList.addGridToPos(1, 3, grid);
//		
//		gridList.addGridToPos(4, 1, grid);
//		
//		gridList.addGridToPos(0, 3, grid);
//		
//		gridList.addGridToPos(4, 4, grid);
		
		
		for (int x = 0; x < gridSizeX; x++) {
			for (int y = 0; y < gridSizeY; y++) {
				if (!gridList.gridExist(x, y)) {
					
					gridList.generateGrid(x, y);
					
				}
			}

		}

		newGrid = gridList.toGrid();
		
		long endTime = System.currentTimeMillis();
		System.out.println("Map Gen Done");
		System.out.println(endTime - startTime);
		System.out.println("\nCreating BI + GUI");
		startTime = System.currentTimeMillis();
		
		
		gui = new NewGUI(newGrid);
		gui.writeBI(String.format("%d_%dx%d.png", newGrid.getSeed(), newGrid.getX(), newGrid.getY()));
		
		gui.setGUI();
		
		endTime = System.currentTimeMillis();
		System.out.println("Done BI + GUI");
		System.out.println(endTime - startTime);


		endTime = System.currentTimeMillis();
		System.out.println("Finished");
		System.out.println(endTime - startTime);
		
		i++;
		}
		
		System.out.println("Finished^2");

	}

}
