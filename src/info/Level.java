package info;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.Timer;

import kopfrechnen2.OutputPanel;

public class Level {

	OutputPanel op;
	Graphics2D d2g;
	boolean time = false;
	public Level(OutputPanel op) {
		this.op = op;
		 
	}

	public void update() {

		if(op.levelUp) {
		switch (op.level) {
		
		case 1: 
			
			d2g = (Graphics2D) op.getGraphics();
			d2g.setFont(new Font("Arial",3,140));
			d2g.setColor(Color.magenta);
			d2g.drawString("Level "+op.level, 110,210);
			 
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			 
				e.printStackTrace();
			}
			
			new Timer(20, evt -> {
                op.txfeingabe.setEditable(true);  
                op.txfeingabe.setVisible(true);
                op.txfeingabe.requestFocus();
                ((Timer) evt.getSource()).stop();
            }).start();
         
		  
			if(op.addition != null) {
				op.addition.updateLevel(30);
			}
			if (op.subtraction != null) {
				op.subtraction.updateLevel(30);
			}        
			
			if (op.multiplikation != null) {
				op.multiplikation.updateLevel(9);
			}       
		/*	if (op.division != null) {
		 *		op.division.updateLevel(10);
			}    */     
			
			break;
		
		case 2:  
			 
			 newLevel();
			
			if (op.addition != null) {
				op.addition.updateLevel(80);
			}
			if (op.subtraction != null) {
				op.subtraction.updateLevel(80);
			}               
			if (op.multiplikation != null) {
				op.multiplikation.updateLevel(10);
			}       
			if (op.division != null) {
				op.division.updateLevel(12);
			}       
			
			break;
		case 3:
			
			newLevel();
			
			if (op.addition != null) {
				op.addition.updateLevel(120);
			}
			if (op.subtraction != null) {
				op.subtraction.updateLevel(120);
			}       
			if (op.multiplikation != null) {
				op.multiplikation.updateLevel(15);
			}     
			if (op.division != null) {
				op.division.updateLevel(14);
			}       
			
			break;
		case 4:
			
			newLevel();
			
			if (op.addition != null) {
				op.addition.updateLevel(120);
			}
			if (op.subtraction != null) {
				op.subtraction.updateLevel(120);
			}       
			if (op.multiplikation != null) {
				op.multiplikation.updateLevel(9);
			}       
			if (op.division != null) {
				op.division.updateLevel(16);
			}       
			
			break;
			
		}
	        op.levelUp = false;     }
	}
	
	void newLevel() {
		
		d2g = (Graphics2D) op.getGraphics();
		d2g.setFont(new Font("Arial",3,140));
		d2g.setColor(Color.magenta);
		d2g.drawString("Level "+op.level, 110,210);
		
		op.txfeingabe.setEditable(false);
		
	    try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		 
			e.printStackTrace();
		}
	    
	    new Timer(20, evt -> {
            op.txfeingabe.setEditable(true);  
            op.txfeingabe.setVisible(true);
            op.txfeingabe.requestFocus();
            ((Timer) evt.getSource()).stop();
        }).start();
	    
		op.txfeingabe.setEditable(true);
		 
		op.entity.x = 20;
		op.player.x = 20;
		op.player.image = op.player.image2;
		op.player.step = false;
		op.player.stepX = 150;
	}
}
