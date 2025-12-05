package daten;

import java.util.Random;

import kopfrechnen2.OutputPanel;

public class SuperZahlen {
 
	public int[] zahlenGesammt = new int[400];
	public int[] zahlen = new int[30];
	
	public int[] zahl1 = new int[30];
	public int[] zahl2 = new int[30];
	public int[] zahl3 = new int[30];
	public int[] zahl4 = new int[30];
	public int[] zahl5 = new int[30]; 
	
	public int a;
	public int b;
	public int c;
	public int d;
	public int e;
 
	public int ergebniss;
	public String name;
	public boolean addition=false, subtraction=false, multiplikation=false;
 
	OutputPanel op;
	public int punkte = 0;
	 
	 
	public static Random rand = new Random();

	 

	public SuperZahlen(OutputPanel op) {
		 this.op = op;
	}
 
	
	public int[] zahlenGesammt() {
		
		for(int a=0; a<400; a++) {
			zahlenGesammt[a]=a;
		}
		return zahlenGesammt;
	}
	
	public int[] zufallVonBis(int von, int bis) {
 
		int length = bis-von;
		zahlen = new int[length];
		
		for (int z = 0; z < length; z++) {
			zahlen[z] = rand.nextInt(bis) + von;
			for (int t = 0; t < z; t++) {
				if (zahlen[t] == zahlen[z]) {
					z--;
				}

			}
		}
		return zahlen;
	}
	

	public int[] zufallszahlen(int length) {

		zahlen = new int[length];
		
		for (int z = 0; z < length; z++) {
			zahlen[z] = rand.nextInt(length) + 1;
			for (int t = 0; t < z; t++) {
				if (zahlen[t] == zahlen[z]) {
					z--;
				}

			}
		}
		return zahlen;
	}
	
	
	
	
	public void updateLevel(int index) {
		 
			a = 0; b = 0; c = 0; d = 0; e = 0;
			zahl1 = new int[index];
			zahl2 = new int[index];
			zahl3 = new int[index];
			zahl4 = new int[index];
			zahl5 = new int[index];
			zahl1 = zufallszahlen(index);
			zahl2 = zufallszahlen(index);
			zahl3 = zufallszahlen(index);
			zahl4 = zufallszahlen(index);
			zahl5 = zufallszahlen(index);
			
			a = zahl1[0];
			b = zahl2[0];
			 
	}
	 
}
