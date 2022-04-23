package proceduralGeneration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;

/**
 * I'm not going to lie, this was pain.
 * Probably could be a lot better, but if it works, it works.
 */
public class GUI<T> {

    private Timer t;
    int timePassed;

    Grid grid;
    int gridX;
    int gridY;

    private Color standard = Color.RED;

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

    boolean run;

    public GUI(ArrayList<Item<Color>> items, int x, int y) {
        gridX = x;
        gridY = y;
        run = false;

        this.grid = new Grid<Color>(x, y, items);
        createFrame();
        frame.setVisible(false);
        writeItem(grid.getGrid());
        frame.setVisible(true);
        t = new Timer(1, new runEvent());

    }


    public boolean getRun() {
        return run;
    }


    public void createComponents() {
        JButton b1 = new JButton("Pause/Resume");
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
        createComponents();

    }


    public void writeItem(Item<T>[][] list) {
        int widthBlock = usedWidth / list[0].length;
        int heightBlock = usedHeight / list[0].length;
        ItemPanel shape = new ItemPanel();

        for (int y = 0; y < list.length; y++) {
            for (int x = 0; x < list[y].length; x++) {
                if (list[y][x].getCollapse() == 0) {
                    shape = new ItemPanel();
                    shape.setBounds((windowWidth - usedWidth) / 2 + (x * (widthBlock)), 100 + (y * (heightBlock)), widthBlock, heightBlock);
                    shape.setBackground((Color)list[y][x].getData());
                    panel.add(shape);
                }
// else {
// shape = new ItemPanel();
// shape.setBounds(x * (widthBlock + 5), windowHeight - (y
// * (heightBlock + 5)) + 40, widthBlock, heightBlock);
// shape.setBackground(Color.GREEN);
// panel.add(shape);
// }
            }
        }
    }


    public void placeItem() {
        frame.setVisible(false);
        try {
            grid.placeSquare();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        writeItem(grid.getGrid());
        frame.setVisible(true);
    }

    private class GUIEvent implements ActionListener {
        public GUIEvent() {

        }


        public void actionPerformed(ActionEvent e) {
            if (run) {
                run = false;
                t.stop();
                return;
            }
            t.start();
            run = true;

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
            grid = new Grid<Color>(gridX,gridY,grid);
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
            frame.setVisible(false);
            grid.clear();
            panel.removeAll();
            createComponents();

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
            t.stop();
            placeItem();
        }

    }


    private class FinishEvent implements ActionListener {
        public FinishEvent() {
        }


        public void actionPerformed(ActionEvent e) {
            t.stop();
            while (true) {
                try {
                    grid.placeSquare();
                }
                catch (Exception r) {
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
            t.stop();
            while (true) {
                try {
                    grid.placeWeightedSquare();
                }
                catch (Exception r) {
                    break;
                }
            }
            frame.setVisible(false);
            writeItem(grid.getGrid());
            frame.setVisible(true);
        }

    }


    private class runEvent implements ActionListener {
        public runEvent() {

        }


        public void actionPerformed(ActionEvent e) {
            System.out.println(timePassed);
            if (timePassed == 100) {
                try {
                    placeItem();
                }
                catch (Exception r) {
                    t.stop();
                }
                timePassed = 0;
            }
            timePassed++;
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
