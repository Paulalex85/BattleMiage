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
					Thread.sleep(500);
				}
				catch(InterruptedException ex) 
				{
				    Thread.currentThread().interrupt();
				}
			}
			else {
				System.out.println("Peut jouer");
				GetBoard();
				Play();
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
		if(new String("CANPLAY").equals(statut)) {
			canPlay = true;
			return false;
		}
		else if(new String("CANTPLAY").equals(statut)) {
			canPlay = false;
			return false;
		}
		else if(new String("VICTORY").equals(statut)) {
			System.out.println("Victoire");
			return true;
		}
		else if(new String("DEFEAT").equals(statut)) {
			System.out.println("Defaite");
			return true;
		}
		else if(new String("DRAW").equals(statut)) {
			System.out.println("Partie Nul");
			return true;
		}
		else { // CANCELLED
			System.out.println("Partie Cancel");
			return true;
		}
	}
	
	public void GetBoard () {
		String plateau = rest.Plateau_jeu(id_partie);
		
		board = gson.fromJson(plateau, Board.class);
		System.out.println("Board chargé");
	}
	
	public void Play() {
		//détermine si joue ou sélectionne un perso 
		if(board.playerBoards[0].fighters == null || board.playerBoards[0].fighters.length < 3) {
			System.out.println("Selection personnage");
			Selection_personnage();
		}
		else if (board.playerBoards[0].fighters.length == 3){
			System.out.println("Set des actions des personnages");
			Action_personnage();
		}
		else {
			System.out.println("ERREUR : PLAY, LONGUEUR FIGHTERS");
		}
	}
	
	public void Selection_personnage() {
		int current_nb;
		if(board.playerBoards[0].fighters == null) {
			current_nb = 0;
		}else {
			current_nb = board.playerBoards[0].fighters.length;
		}
		String retour = "";
		switch(current_nb) {
			case 0 : retour = rest.Joue_Coup(this.id_partie, "ORC"); System.out.println("Choisi Orc");break;
			case 1 : retour = rest.Joue_Coup(this.id_partie, "GUARD");System.out.println("Choisi Guard");break;
			case 2 : retour = rest.Joue_Coup(this.id_partie, "PRIEST");System.out.println("Choisi Priest");break;
			default : retour = rest.Joue_Coup(this.id_partie, "ORC");System.out.println("Choisis Orc");
		}
		
		Analyse_Joue_Coup(retour);
	}
	
	public void Action_personnage() {
		
	}
	
	public void Analyse_Joue_Coup(String s) {
		if(new String("OK").equals(s)) {
			System.out.println("Coup accepté");
		}
		else if(new String("FORBIDDEN").equals(s)) {
			System.out.println("ERREUR : Coup interdit !!");
		}
		else if (new String("NOTYET").equals(s)) {
			System.out.println("Pas au tour du joueur");
		}
		else {
			System.out.println("Game Over");
		}
	}
}
