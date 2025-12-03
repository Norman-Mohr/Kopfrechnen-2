package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import kopfrechnen2.OutputPanel;

public class Player {

	OutputPanel op;
	BufferedImage image, image1, image2, image3;
	public int x, stepX=150;
	public int y;
	private int spriteCounter;
	public boolean collisionZiel = false;
	public boolean bewegen = true, step = false;

	public Player(OutputPanel op) {
		this.op = op;
		getImage();
		x = 20;
		y = 350;
		image = image2;
		System.out.println("Hallo das ist eine Änderung.");
		System.out.println("Hallo das ist eine Änderung 2.");
		System.out.println("Hallo das ist eine Änderung 3.");
		System.out.println("Hallo das ist eine Änderung 4.");
	}

	public void draw(Graphics2D d2g) {
		d2g.drawImage(image, x, y, 70, 60, this.op);
	}

	public void getImage() {
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/picture/CharcRechts.1.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/picture/CharcRechts.2.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/picture/CharcRechts.3.png"));

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void update() {
 
		if(step) {
			x += 1;  spriteCounter();   int r = 4;
		if(x > 20+stepX) {  step = false;  stepX+=150;  }  
		}
		
		if (collisionZiel) {
			op.punkte = op.punkte + op.punkteExtra;
			op.entity.x = 20;
			op.player.x = 20;
			collisionZiel = false;
			image = image2;
			stepX = 0;
		}
	}

	public void spriteCounter() {
		 spriteCounter++; 
		 if(spriteCounter==15) {  image = image1;  }
		 if(spriteCounter==30) {  image = image2;  }
		 if(spriteCounter==45) {  image = image3;  }
		 if(spriteCounter==60) {  spriteCounter=0; }
	}

}
