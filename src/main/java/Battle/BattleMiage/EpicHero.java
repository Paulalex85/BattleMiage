package Battle.BattleMiage;

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
	
	public EpicHero() {
		
	}
}
