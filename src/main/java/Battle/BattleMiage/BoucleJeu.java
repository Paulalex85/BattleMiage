package Battle.BattleMiage;

import com.google.gson.Gson;

public class BoucleJeu {
	
	ApacheRest rest;
	String id_partie;
	boolean canPlay = false;
	Board board;
	Gson gson;
	
	public BoucleJeu(ApacheRest r, String p) {
		this.rest = r;
		this.id_partie = p;
		gson = new Gson();  
		
		while(!GetStatut()) {
			if(!canPlay) {
				System.out.println("Ne peut pas jouer, Pause");
				try {
					Thread.sleep(200);
				}
				catch(InterruptedException ex) 
				{
				    Thread.currentThread().interrupt();
				}
			}
			else {
				System.out.println("Peut jouer");
				GetBoard();
				
				canPlay = false;
			}
		}
	}
	
	/***
	 * 
	 * @return si fin de partie
	 */
	public boolean GetStatut() {
		String statut = rest.Statut_partie(id_partie);
		if(statut == "CANPLAY") {
			canPlay = true;
			return false;
		}
		else if(statut == "CANTPLAY") {
			canPlay = false;
			return false;
		}
		else if(statut == "VICTORY") {
			System.out.println("Victoire");
			return true;
		}
		else if(statut == "DEFEAT") {
			System.out.println("Defaite");
			return true;
		}
		else if(statut == "DRAW") {
			System.out.println("Partie Nul");
			return true;
		}
		else { // CANCELLED
			System.out.println("Partie Cancel");
			return true;
		}
	}
	
	public void GetBoard () {
		String plateau = rest.Statut_partie(id_partie);
		
		board = gson.fromJson(plateau, Board.class);
	}
	
	public void Play() {
		
	}
}
