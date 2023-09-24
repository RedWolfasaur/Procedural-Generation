package proceduralGeneration;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Runner {

	/*
	 * Weights in specific directions is being worked on.
	 * 	-If I ever get some free time to finish that, i will
	 */

	public static void main(String[] args) {

		ArrayList<Item<Color>> list = new ArrayList<Item<Color>>();

		Item<Color> ocean = new Item<Color>(new Color(0, 62, 120));
		Item<Color> coast = new Item<Color>(new Color(0, 94, 184));
		
		Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);
		Item<Color> forest = new Item<Color>(new Color(32, 107, 54));
		Item<Color> grass = new Item<Color>(new Color(98, 166, 107));
		Item<Color> sand = new Item<Color>(Color.ORANGE);
		Item<Color> grassCoast = new Item<Color>(new Color(98, 166, 107));
		
		
		Item<Color> error = new Item<Color>(new Color(255, 0, 195)); // error can be set to anything

		grass.add(sand, 10);
		grass.add(grassCoast, 10);
		grass.add(grass, 50);
		grass.add(forest, 30);
		grass.add(mountains, 10);

		forest.add(grass, 40);
		forest.add(forest, 60);

		sand.add(grass, 50);
		sand.add(coast, 5);
		sand.add(sand, 10);
		sand.add(grassCoast, 10);
		
		grassCoast.add(grass, 50);
		grassCoast.add(coast, 5);
		grassCoast.add(sand, 10);
		grassCoast.add(grassCoast, 20);

		coast.add(sand, 15);
		coast.add(grassCoast, 15);
		coast.add(coast, 30);
		coast.add(ocean, 20);

		ocean.add(coast, 30);
		ocean.add(ocean, 35);

		mountains.add(grass, 20);
		mountains.add(mountains, 30);
		
		
		
		list.add(grass);
		list.add(sand);
		list.add(coast);
		list.add(forest);
		list.add(mountains);
		list.add(ocean);
		list.add(grassCoast);

		// make sure error is at the end of the list.
		for (Item item : list) {
			error.add(item);
		}

		list.add(error);

		// some good seeds:
		// let me note, increasing the size will increase the time it takes. Should be
		// obvious though.
		// 300,300: 8096394144706093395
		// 200x200: 8206209682205551009, -627377344974473140
		// 150x150:
		// 100x100: -1918627471075256442, 5176930254552009688, 7794176660258022025,
		// 5208855863618711882

		long startTime;
		int size;
		Grid<Color> grid;
		int gridSizeX;
		int gridSizeY;
		gridList<Grid<Color>> gridList;
		NewGUI gui;
		Grid<Color> newGrid;
				
		startTime = System.currentTimeMillis();

		//Edit these to change the size of the grid !!!!!
		int sizeX = 6;
		int sizeY = 6;

		grid = new Grid<Color>(sizeX, sizeY, list, -6834613243211531999L);

		System.out.println("Starting");
		
		grid = grid.generateGridSmall(sizeX, sizeY);
		

//		//prospective seeds
//		/*
//		 * 6793835212766707987
//		 * -7781491848298825424
//		 * 683476537010087445 //choice #1
//		 * 
//		 * */

		long endTime = System.currentTimeMillis();
		System.out.println("Map Gen Done");
		System.out.println(endTime - startTime);
		System.out.println("\nCreating BI + GUI");
		startTime = System.currentTimeMillis();
		
		
		gui = new NewGUI(grid);
		gui.writeBI(String.format("C:\\Users\\Wesley\\Downloads\\Test\\%dx%d_%d.png", grid.getX(), grid.getY(), grid.getSeed()));
		
		gui.setGUI();
		
		endTime = System.currentTimeMillis();
		System.out.println("Done BI + GUI");
		System.out.println(endTime - startTime);


		endTime = System.currentTimeMillis();
		System.out.println("Finished");
		System.out.println(endTime - startTime);

		// This method is faster when size < 100
//		
//		startTime = System.currentTimeMillis();
//		
//		grid = new Grid<Color>(5, 5, list, 8096394144706093395L);
//		
//		width = size * 2;
//		height = size * 2;
//		
//		currentWidth = 0;
//		currentHeight = 0;
//		
//		System.out.println("Starting");
//		while (currentWidth < width || currentHeight < height) {
//			if (currentWidth + 8 > width) {
//				currentWidth = width;
//			}
//			else {
//				currentWidth += 8;
//			}
//			if (currentHeight + 8  > height) {
//				currentHeight = height;
//			}
//			else {
//				currentHeight += 8;
//			}
//			
//			//grid = new Grid<Color>(currentWidth, currentHeight, grid);
//			grid = new Grid<Color>(currentWidth, currentHeight, grid);
//			try {
//				while (true) {
//					grid.placeWeightedSquare();
//				}
//			} catch (Exception e) {
//				//System.out.println(currentHeight);
//				// e.printStackTrace();
//			}
//		}
//				
//		long endTime = System.currentTimeMillis();
//		GUI<Item<Color>> window = new GUI<Item<Color>>(grid);
//		//System.out.println(grid);
//		System.out.println(endTime - startTime);

	}

}
