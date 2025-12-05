package kopfrechnen2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import collision.Collision;
import daten.SuperZahlen;
import entity.Entity;
import entity.Player;
import exceptions.FormatEingabeException;
import exceptions.LeereEingabeException;
import info.Info;
import info.Level;
import info.Lives;
import keyhandler.KeyHandler;
import rechenarten.Addition;
import rechenarten.Division;
import rechenarten.EinMalEins;
import rechenarten.Multiplikation;
import rechenarten.Subtraction;
import stopuhr.StopUhr;
import tileManager.Tile;

public class OutputPanel extends JPanel implements ActionListener { // Game Panel

	/**
	 * 
	 */
	private static final long serialVersionUID = 5147113207552137258L;
	
	private JButton btnEnter, btnBeenden, btnStart;
	public JTextField txfeingabe;;

	public BufferedImage image, imageLinks, imageMitte, imageRechts, imageStart, imageZiel;

	public SuperZahlen[] rechenObj;
	public Lives[] lives;
	public int livesPlayer;

	public Addition addition;
	public Subtraction subtraction;
	public Multiplikation multiplikation;
	public EinMalEins einmaleins;
	public Division division;

	public Tile tile;
	public Player player;
	public Entity entity;
	public Lives live;
	public Level levelup;

	public ActionEvent e;
	public StopUhr stopuhr1;
	public KeyHandler keyH;
	public Info info;
	public Collision cCeck;
	Timer timer;

	public String test = "Object nicht = null";

	public int schwierigkeit = 1;
	public int level = 0;
	public int spritecounter = 0;
 

	public String name;
	public String eingabe;
	public int zaehler = 0, aufgabeNeu = 1;
	public int ergebniss;
	public int punkte = 0, punkteExtra = 10;
	public boolean playerStep = false, entityStep = false;
	public boolean konzentration = false;

	public boolean auswertung = false;
	public boolean drawRichtig;
	public boolean aufgabe = false;
	public boolean levelUp = false;
	public boolean zwei = true, drei = false, vier = false, fertig = false;

	private String zahl;
	private int zählerKonzentration;
	boolean eingabeNeu;

	MainPanel mp;
	
	public OutputPanel(MainPanel mp) {
 
		this.mp = mp;

		this.setSize(700, 450);
		this.setBounds(0, 0, getWidth(), getHeight());
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		this.setVisible(false);
		this.setLayout(null);
		this.setBackground(Color.lightGray);

		rechenObj = new SuperZahlen[5];
		lives = new Lives[5];

		info = new Info(this);
		tile = new Tile(this);
		player = new Player(this);
		entity = new Entity(this);
		live = new Lives(this);
		cCeck = new Collision(this);
		levelup = new Level(this);

		live.livesErstellen();

		btnEnter = new JButton("Enter");
		btnEnter.setBounds(540, 250, 130, 60);
		btnEnter.setEnabled(false);
		btnEnter.addActionListener(this);
		this.add(btnEnter);

		btnBeenden = new JButton("Beenden");
		btnBeenden.setBounds(580, 420, 100, 20);
		btnBeenden.setEnabled(false);
		btnBeenden.setBackground(Color.blue);
		btnBeenden.setForeground(Color.white);
		btnBeenden.addActionListener(this);
		this.add(btnBeenden);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(getWidth()/2-100, getHeight()/2-40, 200, 80);
		btnStart.setEnabled(true);
		btnStart.setBackground(Color.blue);
		btnStart.setForeground(Color.white);
		btnStart.addActionListener(this);
		this.add(btnStart);
 
			
			txfeingabe = new JTextField();
			txfeingabe.setBounds(90, 210, 120, 40);
			txfeingabe.setVisible(false);
			txfeingabe.setEditable(false);
			txfeingabe.setHorizontalAlignment(SwingConstants.CENTER);
			txfeingabe.addActionListener(this);
			this.add(txfeingabe);
			
			keyH = new KeyHandler();
			txfeingabe.addKeyListener(keyH);
	 
		stopuhr1 = new StopUhr(this);

		timer = new Timer(10, this);
 
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D d2g = (Graphics2D) g;
		GradientPaint gp = new GradientPaint(0,0,Color.blue, 700, 450, Color.white);
		d2g.setPaint(gp);
		d2g.fillRect(0, 0, 700, 450);
		d2g.setColor(Color.green);
		d2g.fillRect(0, 380, 700, 70);
		
		info.draw(d2g);

		if (addition != null) {
			addition.draw(d2g);
		}
		if (subtraction != null) {
			subtraction.draw(d2g);
		}
		if (multiplikation != null) {
			multiplikation.draw(d2g);
		}
		if (einmaleins != null) {
			einmaleins.draw(d2g);
		}
		if (division != null) {
			division.draw(d2g);
		}

		if (!txfeingabe.getText().equals("")) {
			d2g.drawString("= " + zahl, 230, 242);
		}

		tile.drawTile(d2g);

		entity.drawEntity(d2g);

		player.draw(d2g);

		live.drawLives(d2g);

		drawAuswertung(d2g);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		
		this.e = e;

		cCeck.collisionCheck();

		if(this.e.getSource() == btnStart) {  start();  }

		if (this.e.getSource() == btnBeenden) { // altes Panel löschen
 
              mp.op.remove(this);
              mp.op.removeAll();
              mp.op.invalidate();
              mp.op.setVisible(false);
              mp.ap.setVisible(true);
              
			System.out.println(mp.op.test);
			
			  
			
     		punkteGesammt();

			timer.stop();

		}

		if (this.txfeingabe.getText().length() < 10) {
			zahl = this.txfeingabe.getText();
		} else {
			this.txfeingabe.setText("");
		}

		if (this.e.getSource() == btnEnter || keyH.keyPressed) {

			exceptions();

			if (!eingabeNeu) {

				auswertung();
				EinMalEins.factorPlus = true;
                
				System.out.println("Hallo Thread !!!!!!");
				if (!stopuhr1.isAlive() & !stopuhr1.isInterrupted()) {
					stopuhr1.start();
				}

				if (!stopuhr1.isAlive() & stopuhr1.isInterrupted()) {

					if (stopuhr1 != null) {
						System.out.println("Stopuhr != null");
					}
					System.out.println("Status " + stopuhr1.getState() + " isAlive " + stopuhr1.isAlive());
					stopuhr1 = new StopUhr(this);
					stopuhr1.start();
				}
			}
		}

		try {
			update();
		} catch (InterruptedException e1) {
			 
			e1.printStackTrace();
		}

		repaint();

	}

	void update() throws InterruptedException {
		
		 

		if (addition != null) {
			addition.update();
		}
		if (subtraction != null) {
			subtraction.update();
		}
		if (multiplikation != null) {
			multiplikation.update();
		}
		if (einmaleins != null) {
			einmaleins.update();
		}
		if (division != null) {
			division.update();
		}

		updateStop();
 
		if (levelup != null) {
			levelup.update();
		}

		player.update();
		entity.update();
		live.update();

	}

	void start() {
		 
		    btnStart.setVisible(false);
		    this.setVisible(true);
	
			String code = mp.stp.code; 
			System.out.println("Button code " + code);

			if(code!=null) {
			
			switch (code) {
			case "Addition":

				addition = new Addition(this);
				entityStep = true;
			    
				btnEnter.setEnabled(true);
				btnBeenden.setEnabled(true);
				txfeingabe.setVisible(false);
				aufgabe = true;
				schwierigkeit = 1;
				levelUp = true;
				level = 1;
				addition.name = "addition";

				timer.start();
				break;
				
			case "Subtraction":

				subtraction = new Subtraction(this);
				entityStep = true;
				 
				btnEnter.setEnabled(true);
				btnBeenden.setEnabled(true);
				txfeingabe.setVisible(false);
			    aufgabe = true;
				schwierigkeit = 1;
				levelUp = true;
				level = 1;
				subtraction.name = "subtraction";
 
				timer.start();
				break;
				
			case "Multiplikation":
				multiplikation = new Multiplikation(this);
				entityStep = true;
				 
				btnEnter.setEnabled(true);
				btnBeenden.setEnabled(true);
				txfeingabe.setVisible(true);
				aufgabe = true;
				schwierigkeit = 1;
				levelUp = true;
				level = 1;
				multiplikation.name = "multiplikation";

				timer.start();
				break;
				
			case "EinmalEins":
				einmaleins = new EinMalEins(this);
				entityStep = true;
				 
				btnEnter.setEnabled(true);
				btnBeenden.setEnabled(true);
				txfeingabe.setVisible(true);
				aufgabe = true;
				schwierigkeit = 1;
				levelUp = true;
				level = 1;
				einmaleins.name = "einmaleins";
                mp.stp.timer.stop();
				 
                timer.start();
				break;
				
			case "Division":
				division = new Division(this);
				entityStep = true;
				 
				btnEnter.setEnabled(true);
				btnBeenden.setEnabled(true);
				txfeingabe.setVisible(true);
				aufgabe = true;
				schwierigkeit = 1;
				levelUp = true;
				level = 1;
				division.name = "division";
 
				timer.start();
				break;

			default:
			}
		}
	}

	public void drawAuswertung(Graphics2D d2g) {

		if (auswertung) {

			d2g.setFont(new Font("Arial", 0, 40));

			if (drawRichtig) {
				d2g.setColor(Color.decode("230"));
				d2g.drawString("Gut! Das Ergebniss ist richtig!", 50, 180);
			
			    
			} else {
				d2g.setColor(Color.decode("160"));
				d2g.drawString("Das Ergebniss ist leider falsch!", 50, 180);
				d2g.drawString("Richtig währe " + ergebniss, 220, 245);
 
			}
		}

	}

	void exceptions() throws LeereEingabeException, FormatEingabeException {

		try {
			 
			if (txfeingabe.getText().equals("")) {
				throw new LeereEingabeException("Bitte geben Sie das Ergebniss ein!");
			} else {
				this.eingabe = txfeingabe.getText();
				eingabeNeu = false;
			}
 
			if (!txfeingabe.getText().matches("-?\\d+")) {
				throw new FormatEingabeException("Bitte geben Sie nur Zahlen ein");
			} else {
				this.eingabe = txfeingabe.getText();
				eingabeNeu = false;
			}
			  
		   

		} catch (LeereEingabeException e) {
			System.out.println("Keine Eingabe : " + e.getMessage());
			txfeingabe.requestFocus();
			eingabeNeu = true;
		}

		catch (FormatEingabeException e) {
			System.out.println("Falsches Zeichenformat : " + e.getMessage());
			txfeingabe.setText(null);
			txfeingabe.requestFocus();
			eingabeNeu = true;
		}
	}

	public void auswertung() {

		if (addition != null) {
			ergebniss = addition.ergebniss;
		}
		if (subtraction != null) {
			ergebniss = subtraction.ergebniss;
		}
		if (multiplikation != null) {
			ergebniss = multiplikation.ergebniss;
		}
		if (einmaleins != null) {
			ergebniss = einmaleins.ergebniss;
		}
		
		if (division != null) {
			ergebniss = division.ergebniss;
		}

		int eingabeParse = Integer.parseInt(eingabe);
		if (eingabeParse == ergebniss) {
			drawRichtig = true;
			punkte += 1;
			System.out.println("funktionitert!");
			auswertung = true;
			aufgabe = false;
			player.step = true;
		 
		} else {
			drawRichtig = false;
			zählerKonzentration++;
			System.out.println("funktionitert!");
			auswertung = true;
			aufgabe = false;
		}

		zaehler();

		btnEnter.setEnabled(false);
		txfeingabe.setEditable(false);
		txfeingabe.setText(null);
		txfeingabe.requestFocus();

	}

	void zaehler() {

		if (addition != null || subtraction != null) {

			if (aufgabeNeu == 5) {
				drei = true;
				zwei = false;
				schwierigkeit = 2;
			}
			if (aufgabeNeu == 10) {
				vier = true;
				drei = false;
				schwierigkeit = 3;
			}
			if (aufgabeNeu == 15) {
				fertig = true;
				vier = false;
				schwierigkeit = 1;
			}
			 
			if (fertig) {
				level++;
				levelUp = true;
				fertig = false;
				zwei = true;
				zaehler = 0;
				aufgabeNeu = 0;
			}

		}
		if (multiplikation != null & level < 4) {
			if (aufgabeNeu == 10) {
				fertig = true;
				schwierigkeit = 1;
			}
			 
			if (fertig) {
				level++;
				levelUp = true;
				fertig = false;
				zaehler = 0;
				aufgabeNeu = 0;
			}

		}

	 

		if (division != null) {
			if (aufgabeNeu == 10) {
				fertig = true;
			}
		 
			if (fertig) {
				level++;
				levelUp = true;
				fertig = false;
				zwei = true;
				zaehler = 0;
				aufgabeNeu = 0;
			}
		}

		if (zählerKonzentration == 3) {
			konzentration = true;
			zählerKonzentration = 0;
		} // Konzentration
		System.out.println("Stufe " + zaehler);

	}

	public void updateStop() {

		if (stopuhr1 != null) {
			System.out.println("Zeit Hallo! " + stopuhr1.time);
			if (auswertung) {
				if (stopuhr1.time >= 1) {
					stopuhr1.time = 0;
					auswertung = false;
					aufgabe = true;
					stopuhr1.interrupt();
					btnEnter.setEnabled(true);
					txfeingabe.setEditable(true);
					txfeingabe.requestFocus();
					konzentration = false;
                    zaehler++;
                    ++aufgabeNeu;  
				}
			}
		}

	}

	public void punkteGesammt() {

		if (addition != null) {
			if (this.addition.addition) {
				mp.ap.punkteAdd += punkte;
			}
		}
		if (subtraction != null) {
			if (this.subtraction.subtraction) {
				mp.ap.punkteSub += punkte;
			}
		}
		if (multiplikation != null) {
			if (this.multiplikation.multiplikation) {
				mp.ap.punkteMult += punkte;
			}
		}
		if (einmaleins != null) {
			if (this.einmaleins.einmaleins) {
				mp.ap.punkteEinMal += punkte;
			}
		}
		if (division != null) {
			if (this.division.division) {
				mp.ap.punkteDiv += punkte;
			}
		}

		mp.ap.punkteGesammt = mp.ap.punkteAdd + mp.ap.punkteSub + mp.ap.punkteMult + mp.ap.punkteEinMal
				+ mp.ap.punkteDiv;

		System.out.println("Punkte Addition = " + mp.ap.punkteAdd);
		System.out.println("Punkte Subtraction = " + mp.ap.punkteSub);
		System.out.println("Punkte Multiplikation = " + mp.ap.punkteAdd);
		System.out.println("Punkte EinMalEins = " + mp.ap.punkteSub);
		System.out.println("Punkte Division = " + mp.ap.punkteSub);
		System.out.println("-----------------------------------------------");
		System.out.println("Punkte gesammt = " + mp.ap.punkteGesammt);
		punkte = 0;
	}
}
