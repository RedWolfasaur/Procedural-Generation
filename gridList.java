package proceduralGeneration;

import java.awt.Color;
import java.util.Iterator;

public class gridList<T> {

	public Grid<T>[][] gridArray;

	public gridList(int x, int y) {

		gridArray = new Grid[y][x];

	}

	public boolean gridExist(int x, int y) {
		return (gridArray[y][x] != null);
	}

	public boolean addGridToPos(int x, int y, Grid grid) {
		if (gridArray[y][x] != null) {
			return false;
		}
		gridArray[y][x] = grid;
		return true;
	}

	public boolean generateGrid(int x, int y) {
		if (gridArray[y][x] != null) {
			return false;
		}

		int sizeX = 64;
		int sizeY = 64;
		gridArray[y][x] = new Grid<T>(64, 64, gridArray[0][0].getItems(), gridArray[0][0].getSeed());

		boolean[] dir = { false, false, false, false }; // {l, r, d, u}

		try {
			gridArray[y][x - 1].getX(); // just to see if it's in range
			sizeX += 1;
			dir[0] = true;
		} catch (Exception e) {
			// System.out.println(e);
		}

		try {
			gridArray[y][x + 1].getX(); // just to see if it's in range
			sizeX += 1;
			dir[1] = true;
		} catch (Exception e) {
			// System.out.println(e);
		}

		try {
			gridArray[y - 1][x].getX(); // just to see if it's in range
			sizeY += 1;
			dir[2] = true;
		} catch (Exception e) {
			// System.out.println(e);
		}
		try {
			gridArray[y + 1][x].getX(); // just to see if it's in range
			sizeY += 1;
			dir[3] = true;
		} catch (Exception e) {
			// System.out.println(e);
		}

		Grid<T> newGrid = new Grid<T>(sizeX, sizeY, gridArray[0][0].getItems(), gridArray[0][0].getSeed());

		if (dir[0]) {
			for (int i = 0; i < gridArray[y][x - 1].getY(); i++) {
				try {
					newGrid.add(gridArray[y][x - 1].getGrid()[i][gridArray[y][x - 1].getX() - 1], 0, i);
				} catch (ItemExistsException e) {
					// e.printStackTrace();
				}
			}
		}

		if (dir[1]) {
			for (int i = 0; i < gridArray[y][x + 1].getY(); i++) {
				try {
					newGrid.add(gridArray[y][x + 1].getGrid()[i][gridArray[y][x + 1].getX() - 1], newGrid.getX() - 1, i);
				} catch (ItemExistsException e) {
					// e.printStackTrace();
				}
			}
		}

		if (dir[2]) {
			for (int i = 0; i < gridArray[y - 1][x].getX(); i++) {
				try {
					newGrid.add(gridArray[y - 1][x].getGrid()[gridArray[y - 1][x].getY() - 1][i], i, 0);
				} catch (ItemExistsException e) {
					//e.printStackTrace();
				}
			}
		}

		if (dir[3]) {
			for (int i = 0; i < gridArray[y + 1][x].getX(); i++) {
				try {

					newGrid.add(gridArray[y + 1][x].getGrid()[gridArray[y + 1][x].getY() - 1][i], i, newGrid.getY() - 1);
				} catch (ItemExistsException e) {
					// e.printStackTrace();
				}
			}
		}

		newGrid.generateGrid();
				
		if (dir[0] == false && dir[1] == false && dir[2]  == false &&  dir[3] == false) {
			gridArray[y][x] = newGrid;
		}

		if (dir[0]) {
			for (int gridX = 1; gridX < 65; gridX++) {
				for (int gridY = 0; gridY < 64; gridY++) {

					gridArray[y][x].forceAdd(newGrid.getGrid()[gridY][gridX], gridX - 1, gridY);

				}
			}
		}

		else if (dir[1]) {
			for (int gridX = 0; gridX < newGrid.getX() - 1; gridX++) {
				for (int gridY = 0; gridY < 64; gridY++) {

					gridArray[y][x].forceAdd(newGrid.getGrid()[gridY][gridX], gridX, gridY);

				}
			}
		}

		if (dir[2]) {
			for (int gridY = 1; gridY < 65; gridY++) {
				for (int gridX = 0; gridX < 64; gridX++) {

					gridArray[y][x].forceAdd(newGrid.getGrid()[gridY][gridX], gridX, gridY - 1);

				}
			}
		} else if (dir[3]) {
			for (int gridY = 0; gridY < newGrid.getY() - 1; gridY++) {
				for (int gridX = 0; gridX < 64; gridX++) {

					gridArray[y][x].forceAdd(newGrid.getGrid()[gridY][gridX], gridX, gridY);

				}
			}
		}

		return true;

	}

	public Grid toGrid() {
		Grid newGrid = new Grid(64 * gridArray[0].length, 64 * gridArray.length, gridArray[0][0].getItems(),
				gridArray[0][0].getSeed());

		// iterates through gridList
		for (int x = 0; x < gridArray[0].length; x++) {
			for (int y = 0; y < gridArray.length; y++) {

				// iterates through the grid.
				for (int gridX = 0; gridX < 64; gridX++) {
					for (int gridY = 0; gridY < 64; gridY++) {
						if (gridArray[y][x] == null) {
							newGrid.forceAdd(new Item(null, gridX, gridY, gridArray[0][0].getItems()), (x * 64) + gridX,
									(y * 64) + gridY);
							continue;
						}
						newGrid.forceAdd(gridArray[y][x].getGrid()[gridY][gridX], (x * 64) + gridX, (y * 64) + gridY);

					}
				}

			}

		}

		return newGrid;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int y = 0; y < gridArray.length; y++) {
			sb.append("|");
			for (int x = 0; x < gridArray[y].length; x++) {
				if (gridArray[y][x] == null) {
					sb.append(" " + "][ ");
					continue;
				}
				sb.append(gridArray[y][x].toString() + "][ ");
			}
			sb.replace(sb.length() - 2, sb.length(), "");
			sb.append("|\n");
		}
		return sb.toString();
	}
}
