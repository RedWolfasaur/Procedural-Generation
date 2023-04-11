package proceduralGeneration;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NewGUI extends JPanel{
	
	private BufferedImage bufferedImage;
	private long seed;
	
	public NewGUI(Grid<Color> newGrid) {
		
		seed = newGrid.getSeed();

		bufferedImage = new BufferedImage(newGrid.getX(), newGrid.getY(),
		        BufferedImage.TYPE_INT_RGB);
		
		for (int x = 0; x < newGrid.getX(); x++) {
		    for (int y = 0; y < newGrid.getY(); y++) {
		    	if (newGrid.getGrid()[y][x].getData() == null) {
		    		bufferedImage.setRGB(x, y, Color.GRAY.getRGB());
		    		continue;
		    	}
		        bufferedImage.setRGB(x, y, newGrid.getGrid()[y][x].getData().getRGB());
		    }
		}
	}
	
	public NewGUI(BufferedImage buf, long seed) {
		this.seed = seed;
		bufferedImage = buf;
	}
	
	public void paint(Graphics g) {
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.BLACK);
		g.drawString("Seed: "+ Long.toString(seed), 10, 20);
	    g.drawImage(bufferedImage, 20,20, 1400, 800, this);
	   }
	
	public boolean writeBI(String fileName) {
		try {
			ImageIO.write(bufferedImage, "png", new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean setGUI() {
		JFrame frame = new JFrame();
				
	    frame.getContentPane().add(new NewGUI(bufferedImage, seed));

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1600, 900);
	    frame.setVisible(true);
		return true;
	}
}
