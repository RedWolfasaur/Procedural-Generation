package proceduralGeneration;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;

/**
 * I'm not going to lie, this was pain. Probably could be a lot better, but if
 * it works, it works.
 * 
 * A class just to show an example of how Grid and Item can be implemented. If
 * youre going to use my procgen, you can get rid of this class.F
 * 
 * 
 */
public class GUI<T> {

    int timePassed;

    Grid<Color> grid;
    int gridX;
    int gridY;

    private int windowWidth = 1000;
    private int windowHeight = 800;

    private int usedWidth = 900;
    private int usedHeight = 600;

    private JFrame frame;
    private writeArea panel;
    private JButton b7;
    private JButton b8;
    private JButton b9;
    private JButton b10;

    JTextField seedInput;
    JSlider item;

    Item<Color> worldGen;

    Random rand;

    boolean run;

    public GUI(ArrayList<Item<Color>> items, int x, int y) {
	rand = new Random();
	long seed = rand.nextLong();
	rand = new Random(seed);
	gridX = x;
	gridY = y;
	run = false;

	this.grid = new Grid<Color>(x, y, items, seed);
	createFrame();
	frame.setVisible(false);
	writeItem(grid.getGrid());
	frame.setVisible(true);

    }

    public GUI(ArrayList<Item<Color>> items, int x, int y, long seed) {
	rand = new Random(seed);
	gridX = x;
	gridY = y;
	run = false;

	this.grid = new Grid<Color>(x, y, items, seed);
	createFrame();
	frame.setVisible(false);
	writeItem(grid.getGrid());
	frame.setVisible(true);
    }

    public GUI(Grid<Color> grid) {
	long seed = grid.getSeed();
	rand = new Random(seed);
	gridX = grid.getX();
	gridY = grid.getY();
	run = false;

	this.grid = grid;
	createFrame();
	panel.setVisible(false);
	// writeItem(grid.getGrid());
	writeItem(grid.getGrid());
	panel.setVisible(true);

    }

    public boolean getRun() {
	return run;
    }

    public void createComponents() {
	JTextPane seedLabel = new JTextPane();
	seedLabel.setText("Current Seed: " + grid.getSeed());
	seedLabel.setBounds(10, 0, 200, 30);
	seedLabel.setEditable(false);
	seedLabel.setBackground(null);
	seedLabel.setBorder(null);
	panel.add(seedLabel);

	JButton b1 = new JButton("Seed Menu");
	b1.setBounds((windowWidth / 2) - 50 + 100, 30, 100, 30);
	b1.setBackground(Color.LIGHT_GRAY);
	b1.setMnemonic(KeyEvent.VK_M);
	panel.add(b1);

	JButton b2 = new JButton("Exit");
	b2.setBounds((windowWidth / 2) - 50 - 100, 30, 100, 30);
	b2.setBackground(Color.LIGHT_GRAY);
	b2.setMnemonic(KeyEvent.VK_M);
	panel.add(b2);

	JButton b3 = new JButton("Finish");
	b3.setBounds((windowWidth / 2) - 50 - 300, 30, 100, 30);
	b3.setBackground(Color.LIGHT_GRAY);
	b3.setMnemonic(KeyEvent.VK_M);
	panel.add(b3);

	JButton b4 = new JButton("Add Piece");
	b4.setBounds((windowWidth / 2) - 50 + 200, 30, 100, 30);
	b4.setBackground(Color.LIGHT_GRAY);
	b4.setMnemonic(KeyEvent.VK_M);
	panel.add(b4);

	JButton b5 = new JButton("Finish Weighted");
	b5.setBounds((windowWidth / 2) - 50 - 200, 30, 100, 30);
	b5.setBackground(Color.LIGHT_GRAY);
	b5.setMnemonic(KeyEvent.VK_M);
	panel.add(b5);

	JButton b6 = new JButton("Reset");
	b6.setBounds((windowWidth / 2) - 50, 30, 100, 30);
	b6.setBackground(Color.LIGHT_GRAY);
	b6.setMnemonic(KeyEvent.VK_M);
	panel.add(b6);

	b7 = new JButton("Increase Size (+1)");
	b7.setBounds((windowWidth / 2) - (2 * 150 / 2), 70, 150, 30);
	b7.setBackground(Color.LIGHT_GRAY);
	b7.setMnemonic(KeyEvent.VK_M);
	panel.add(b7);

	b8 = new JButton("Increase Size (-1)");
	b8.setBounds((windowWidth / 2), 70, 150, 30);
	b8.setBackground(Color.LIGHT_GRAY);
	b8.setMnemonic(KeyEvent.VK_M);
	panel.add(b8);

	b9 = new JButton("Increase Size (+5)");
	b9.setBounds((windowWidth / 2) - (2 * 150 / 2) - 150, 70, 150, 30);
	b9.setBackground(Color.LIGHT_GRAY);
	b9.setMnemonic(KeyEvent.VK_M);
	panel.add(b9);

	b10 = new JButton("Increase Size (-5)");
	b10.setBounds((windowWidth / 2) + 150, 70, 150, 30);
	b10.setBackground(Color.LIGHT_GRAY);
	b10.setMnemonic(KeyEvent.VK_M);
	panel.add(b10);

	JButton b11 = new JButton("Create World");
	b11.setBounds((windowWidth / 2) - 50 + 300, 30, 150, 30);
	b11.setBackground(Color.LIGHT_GRAY);
	b11.setMnemonic(KeyEvent.VK_M);
	panel.add(b11);

	frame.add(panel);

	b1.addActionListener(new GUIEvent());
	b2.addActionListener(new ExitEvent());
	b3.addActionListener(new FinishEvent());
	b4.addActionListener(new placePieceEvent());
	b5.addActionListener(new FinishWeightedEvent());
	b6.addActionListener(new clearEvent());
	b7.addActionListener(new resizeEvent());
	b8.addActionListener(new resizeEvent());
	b9.addActionListener(new resizeEvent());
	b10.addActionListener(new resizeEvent());
	b11.addActionListener(new seedWorld());

	frame.setSize(windowWidth, windowHeight);
	frame.setLayout(null);
	frame.setVisible(true);
    }

    public void createFrame() {
	// Create and set up the window.
	frame = new JFrame("Testing Procedural Generation with Graphics");
	panel = new writeArea();
	panel.setLayout(null);

	panel.setBounds(0, 0, windowWidth, windowHeight);
	panel.setBackground(Color.GRAY);
	panel.setFocusable(true);
	panel.addKeyListener(new NavigationListener());

	createComponents();

    }

    public void createNewGrid() {
	rand = new Random();
	long seed = rand.nextLong();
	createNewGrid(seed);
    }

    public void createNewGrid(long seed) {
	rand = new Random(seed);
	frame.setVisible(false);
	grid.clear();
	grid = new Grid<Color>(gridX, gridY, grid.getItems(), seed);
	panel.removeAll();
	createComponents();
    }

    public void writeItem(Item<Color>[][] list) {
	int widthBlock = (int) Math.round(((double) usedWidth / (double) list[0].length));
	int heightBlock = (int) Math.round(((double) usedHeight / (double) list.length));
	ItemPanel shape = new ItemPanel();
	Item<Color> item;

	panel.removeAll();
	createComponents();

	Iterator<Item<Color>> iter = grid.gridIterator();
	while (iter.hasNext()) {
	    item = iter.next();
	    if (item.getCollapse() == 0) {
		shape = new ItemPanel();
		shape.setBounds(20 + ((1 + item.getX()) * (widthBlock)), 100 + (item.getY() * (heightBlock)),
			widthBlock, heightBlock);
		shape.setBackground(item.getData());
		panel.add(shape);
	    }
	}
    }

    public void typeSeed(Item<Color> item) {
	if (item == null) {
	    item = grid.getItems().get(0);
	}
	double itemsToPlace = (gridX * gridY) * (1.0 / 12.0);
	for (int i = 0; i < itemsToPlace; i++) {
	    try {
		grid.add(item, rand.nextInt(gridX), rand.nextInt(gridY));
	    } catch (ItemExistsException e) {
		i--;
	    }
	}

	frame.setVisible(false);
	writeItem(grid.getGrid());
	frame.setVisible(true);
    }

    public void placeItem() {
	frame.setVisible(false);
	try {
	    grid.placeWeightedSquare();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	writeItem(grid.getGrid());
	frame.setVisible(true);
    }

    public void seedSettings() {
	frame.setVisible(false);
	seedInput = new JTextField("Input Seed");
	seedInput.setBounds(225, 2, 100, 25);
	panel.add(seedInput);

	JButton seedAccept = new JButton();
	seedAccept.setBounds(325, 2, 25, 25);
	seedAccept.setBackground(Color.LIGHT_GRAY);
	seedAccept.addActionListener(new changeSeed());
	panel.add(seedAccept);

	int start = 0;
	if (item != null) {
	    start = item.getValue();
	}
	item = new JSlider(0, grid.getItems().size() - 2, start);
	item.setBounds(400, 0, 400, 25);
	// item.setMajorTickSpacing(1);
	// item.setPaintTicks(true);
	item.setBackground(Color.GRAY);

	Hashtable labelTable = new Hashtable();

	labelTable.put(0, new JLabel("Grass"));
	labelTable.put(1, new JLabel("Sand"));
	labelTable.put(2, new JLabel("Water"));
	labelTable.put(3, new JLabel("Forest"));
	labelTable.put(4, new JLabel("Mountain"));
	item.setLabelTable(labelTable);
	item.setPaintLabels(true);

	panel.add(item);

	JButton itemAccept = new JButton();
	itemAccept.setBounds(800, 2, 25, 25);
	itemAccept.setBackground(Color.LIGHT_GRAY);
	itemAccept.addActionListener(new changeItem());

	panel.add(itemAccept);

	panel.setVisible(true);
    }

    private class NavigationListener implements KeyListener {
	private boolean moving = false;

	@Override
	public void keyReleased(KeyEvent e) {
	    int loc = e.getKeyCode();
	    if ((loc == KeyEvent.VK_KP_LEFT || loc == KeyEvent.VK_LEFT)) {
		gridX += 5;
		grid = new Grid<Color>(gridX, gridY, grid, 1);
	    }
	    if ((loc == KeyEvent.VK_KP_UP || loc == KeyEvent.VK_UP)) {
		gridY += 5;
		grid = new Grid<Color>(gridX, gridY, grid, 0);
	    }
	    if ((loc == KeyEvent.VK_KP_RIGHT || loc == KeyEvent.VK_RIGHT)) {
		gridX += 5;
		grid = new Grid<Color>(gridX, gridY, grid, 0);
	    }
	    if ((loc == KeyEvent.VK_KP_DOWN || loc == KeyEvent.VK_DOWN)) {
		gridY += 5;
		grid = new Grid<Color>(gridX, gridY, grid, 1);
	    }

	    while (true) {
		try {
		    grid.placeWeightedSquare();

		} catch (Exception r) {
		    break;
		}
	    }

	    frame.setVisible(false);
	    panel.removeAll();
	    createComponents();
	    writeItem(grid.getGrid());
	    moving = false;

	}

	@Override
	public void keyTyped(KeyEvent e) {
	    // System.out.println(e.getKeyChar());

	}

	@Override
	public void keyPressed(KeyEvent e) {
	    // System.out.println(e.getKeyChar());

	}
    }

    private class GUIEvent implements ActionListener {
	public GUIEvent() {

	}

	public void actionPerformed(ActionEvent e) {
	    seedSettings();

	}

    }

    private class changeItem implements ActionListener {
	public changeItem() {

	}

	public void actionPerformed(ActionEvent e) {
	    worldGen = grid.getItems().get(item.getValue());
	    createNewGrid(Long.parseLong(seedInput.getText()));

	}

    }

    private class changeSeed implements ActionListener {
	public changeSeed() {

	}

	public void actionPerformed(ActionEvent e) {
	    worldGen = grid.getItems().get(item.getValue());
	    createNewGrid(Long.parseLong(seedInput.getText()));

	}

    }

    private class resizeEvent implements ActionListener {
	public resizeEvent() {
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource().equals(b7)) {
		gridX++;
		gridY++;
	    }
	    if (e.getSource().equals(b8)) {
		gridX--;
		gridY--;
	    }
	    if (e.getSource().equals(b9)) {
		gridX = gridX + 5;
		gridY = gridY + 5;
	    }
	    if (e.getSource().equals(b10)) {
		gridX = gridX - 5;
		gridY = gridY - 5;
	    }

	    grid = new Grid<Color>(gridX, gridY, grid);
	    frame.setVisible(false);
	    panel.removeAll();
	    createComponents();
	    writeItem(grid.getGrid());

	}

    }

    private class clearEvent implements ActionListener {
	public clearEvent() {

	}

	public void actionPerformed(ActionEvent e) {
	    createNewGrid();

	}

    }

    private class ExitEvent implements ActionListener {
	public ExitEvent() {

	}

	public void actionPerformed(ActionEvent e) {
	    System.exit(0);
	}

    }

    private class placePieceEvent implements ActionListener {
	public placePieceEvent() {

	}

	public void actionPerformed(ActionEvent e) {
	    placeItem();
	}

    }

    private class FinishEvent implements ActionListener {
	public FinishEvent() {
	}

	public void actionPerformed(ActionEvent e) {
	    while (true) {
		try {
		    grid.placeSquare();
		} catch (Exception r) {
		    break;
		}
	    }
	    frame.setVisible(false);
	    writeItem(grid.getGrid());
	    frame.setVisible(true);

	}

    }

    private class FinishWeightedEvent implements ActionListener {
	public FinishWeightedEvent() {
	}

	public void actionPerformed(ActionEvent e) {
	    long startTime = System.currentTimeMillis();
	    
	    int currentWidth = 50;
	    int currentHeight = 50;
	    
	    int width = grid.getX();
	    int height = grid.getY();
	    
	    grid = new Grid<Color>(50, 50, grid);

	    System.out.println("Starting");
	    while (currentWidth != width && currentHeight != height) {
		currentWidth += 50;
		currentHeight += 50;
		grid = new Grid<Color>(currentWidth, currentHeight, grid);
		try {
		    while (true) {
			grid.placeWeightedSquare();
		    }
		} catch (Exception e2) {
		    // e2.printStackTrace();
		}
	    }

	    frame.setVisible(false);
	    writeItem(grid.getGrid());
	    frame.setVisible(true);
	    long endTime = System.currentTimeMillis();
	    System.out.println(endTime - startTime);

	}

    }

    private class runEvent implements ActionListener {
	public runEvent() {

	}

	public void actionPerformed(ActionEvent e) {

	    placeItem();

	}

    }

    private class seedWorld implements ActionListener {
	public seedWorld() {

	}

	public void actionPerformed(ActionEvent e) {
	    typeSeed(worldGen);
	}

    }

    private class writeArea extends JPanel {
	// fields
	public int xSize = 600;
	public int ySize = 400;

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    int width = this.getWidth();
	    int height = this.getHeight();
	    g.setColor(Color.GRAY);
	    g.fillRect(0, 0, width, height);

	}

	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(xSize, ySize);
	}

    }

    private class ItemPanel extends JPanel {
	// fields
	public int xSize = 600 / 15;
	public int ySize = 400 / 15;

	// public void paintComponent(Graphics g) {
	// super.paintComponent(g);
	// int width = this.getWidth();
	// int height = this.getHeight();
	// g.setColor(Color.GRAY);
	// g.fillRect(0, 0, width, height);
	//
	// g.setColor(Color.GREEN);
	// g.fillRect(10, 10, 100, 30);

	// }

	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(xSize, ySize);
	}

	public void setColor() {

	}

    }

}
