package proceduralGeneration;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/*
 * 
 * This file is literally just random shit i've been testing
 * 
 * */

public class otherRunnerForTestingCoolShit {
	

		/*
		 * Ideas for tomorrow Work on paths? Weights in specific directions.
		 */

		public otherRunnerForTestingCoolShit() {

			ArrayList<Item<Color>> list = new ArrayList<Item<Color>>();
//			Item<Color> coast = new Item<Color>(new Color(39, 153, 219)); //standard
//			Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);
//			Item<Color> grass = new Item<Color>(new Color(98, 166, 107)); //standard grass
//			Item<Color> forest = new Item<Color>(new Color(32, 107, 54)); //standard color
//			Item<Color> sand = new Item<Color>(Color.ORANGE);
//			Item<Color> ocean = new Item<Color>(Color.BLUE);
			
			
			Item<Color> ocean = new Item<Color>(new Color(0, 62, 120));
			Item<Color> coast = new Item<Color>(new Color(0, 94, 184));
			
			Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);
			Item<Color> forest = new Item<Color>(new Color(32, 107, 54));
			Item<Color> grass = new Item<Color>(new Color(98, 166, 107));
			Item<Color> sand = new Item<Color>(Color.ORANGE);
			Item<Color> grassCoast = new Item<Color>(new Color(98, 166, 107));
			
			
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

			grass.add(sand, 10);
			grass.add(grassCoast, 10);
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
			sand.add(sand, 10);
			sand.add(grassCoast, 10);
			
			grassCoast.add(grass, 50);
			grassCoast.add(coast, 5);
			grassCoast.add(sand, 10);
			grassCoast.add(grassCoast, 20);

			//coast.add(sand, 20);
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

//		Grid<String> grid = new Grid<String>(100, 100, list, 5176930254552009688L);

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
			int size;
			Grid<Color> grid;
			int gridSizeX;
			int gridSizeY;
			gridList<Grid<Color>> gridList;
			NewGUI gui;
			Grid<Color> newGrid;
			
			//Random rand = new Random();
			
			//while (i < 10) {
			startTime = System.currentTimeMillis();

			int sizeX = 6;
			int sizeY = 6;

			// Grid<Color> grid = new Grid<Color>(5, 5, list, 8096394144706093395L);
				
			//Great seed: 8096394144706093395L
			
			//#1 choice: -6834613243211531999
			grid = new Grid<Color>(sizeX, sizeY, list, -6834613243211531999L);

			System.out.println("Starting");
			
			grid = grid.generateGridSmall(sizeX, sizeY);
			

	//
	//
//			gridSizeX = 5;
//			gridSizeY = 5;
	//
//			//prospective seeds
//			/*
//			 * 6793835212766707987
//			 * -7781491848298825424
//			 * 683476537010087445 //choice #1
//			 * 
//			 * */
//			gridList = new gridList<Grid<Color>>(gridSizeX, gridSizeY);
	//
//			gridList.addGridToPos(0, 0, grid);
	//
//			
//			for (int x = 0; x < gridSizeX; x++) {
//				for (int y = 0; y < gridSizeY; y++) {
//					if (!gridList.gridExist(x, y)) {
//						
//						gridList.generateGrid(x, y);
//						
//					}
//				}
	//
//			}
	//
//			newGrid = gridList.toGrid();
//			
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
			
			//i++;
			//}
			
			System.out.println("Finished^2");

			// This method is faster when size < 100
//			
//			startTime = System.currentTimeMillis();
//			
//			grid = new Grid<Color>(5, 5, list, 8096394144706093395L);
//			
//			width = size * 2;
//			height = size * 2;
//			
//			currentWidth = 0;
//			currentHeight = 0;
//			
//			System.out.println("Starting");
//			while (currentWidth < width || currentHeight < height) {
//				if (currentWidth + 8 > width) {
//					currentWidth = width;
//				}
//				else {
//					currentWidth += 8;
//				}
//				if (currentHeight + 8  > height) {
//					currentHeight = height;
//				}
//				else {
//					currentHeight += 8;
//				}
//				
//				//grid = new Grid<Color>(currentWidth, currentHeight, grid);
//				grid = new Grid<Color>(currentWidth, currentHeight, grid);
//				try {
//					while (true) {
//						grid.placeWeightedSquare();
//					}
//				} catch (Exception e) {
//					//System.out.println(currentHeight);
//					// e.printStackTrace();
//				}
//			}
//					
//			long endTime = System.currentTimeMillis();
//			GUI<Item<Color>> window = new GUI<Item<Color>>(grid);
//			//System.out.println(grid);
//			System.out.println(endTime - startTime);

		}

	

}
