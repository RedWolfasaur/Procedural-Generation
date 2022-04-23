package proceduralGeneration;

import java.awt.Color;
import java.util.ArrayList;

public class Runner {

    /*
     * Ideas for tomorrow
     * Work on paths?
     * Weights in specific directions.
     */

    public static void main(String[] args) throws Exception {

        ArrayList<Item<Color>> list = new ArrayList<Item<Color>>();
        Item<Color> grass = new Item<Color>(Color.GREEN);
        Item<Color> forest = new Item<Color>(new Color(28, 77, 41));
        Item<Color> sand = new Item<Color>(Color.ORANGE);
        Item<Color> water = new Item<Color>(Color.BLUE);
        Item<Color> mountains = new Item<Color>(Color.LIGHT_GRAY);

// ArrayList<Item<String>> list = new ArrayList<Item<String>>();
// Item<String> grass = new Item<String>("G");
// Item<String> forest = new Item<String>("F");
// Item<String> sand = new Item<String>("S");
// Item<String> water = new Item<String>("W");
// Item<String> mountains = new Item<String>("M");

        grass.add(sand, 20);
        grass.add(grass, 50);
        grass.add(forest, 30);
        grass.add(mountains, 10);

        forest.add(grass, 40);
        forest.add(forest, 60);

        sand.add(grass, 30);
        sand.add(water, 20);
        sand.add(sand, 50);

        water.add(sand, 20);
        water.add(water, 80);

        mountains.add(grass, 20);
        mountains.add(mountains, 30);

        list.add(grass);
        list.add(sand);
        list.add(water);
        list.add(forest);
        list.add(mountains);

        // Grid<String> grid = new Grid<String>(3, 3, list);

        Grid<Color> grid = new Grid<Color>(100, 100, list);
        GUI<Item<Color>> window = new GUI<Item<Color>>(list, 15, 15);

    }

}
