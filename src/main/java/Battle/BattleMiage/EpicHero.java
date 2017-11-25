package Battle.BattleMiage;

import java.util.ArrayList;
import java.util.List;

public class EpicHero {
	
	public String fighterClass;
	public int orderNumberInTeam;
	public boolean isDead;
	public int maxAvailableMana;
	public int maxAvailableLife;
	public List<State> states;
	public String fighterID;
	public int currentMana, currentLife;
	
	private ArrayList<String> moovesAvailable;
	
	public EpicHero() {
		moovesAvailable = new ArrayList<String>();
		moovesAvailable.add("ATTACK");
		moovesAvailable.add("DEFEND");
		moovesAvailable.add("YELL");
		moovesAvailable.add("PROTECT");
		moovesAvailable.add("HEAL");
	}
	
	public String attack(int self, int target) {
		return "A" + Integer.toString(self) + "," + moovesAvailable.get(0) + ",E" + Integer.toString(target);
	}
	
	public String defend(int self, int target) {
		return "A" + Integer.toString(self) + "," + moovesAvailable.get(1) + ",E" + Integer.toString(target);
	}
	
	public String yell(int self, int target) {
		return "A" + Integer.toString(self) + "," + moovesAvailable.get(2) + ",E" + Integer.toString(target);
	}
	
	public String protect(int self, int target) {
		return "A" + Integer.toString(self) + "," + moovesAvailable.get(3) + ",A" + Integer.toString(target);
	}
	
	public String protectHimself(int self) {
		return "A" + Integer.toString(self) + "," + moovesAvailable.get(3) + ",A" + Integer.toString(self);
	}
	
	public String healFighter(int self, int targetNumber, EpicHero[] fighters) {
		for (int i = 0; i < fighters.length; i++) {
			if(!fighters[i].isDead  && fighters[i].currentLife < fighters[i].maxAvailableLife/2 ) {
				return "A" + Integer.toString(self) + "," + moovesAvailable.get(4) + ",A" + Integer.toString(i+1);
			}
		}
		return this.attack(self, targetNumber);
	}
}
