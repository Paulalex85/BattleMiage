package Battle.BattleMiage;

import java.util.Random;

import com.google.gson.Gson;

public class BoucleJeu {
	
	ApacheRest rest;
	String id_partie;
	boolean canPlay = false;
	Board board;
	Gson gson;
	private Random randomGenerator;
	public final int NB_PLAYERS = 3;
	int lvl_bot;
	
	StratDeLaMorKiTu stratjambon;
	
	public BoucleJeu(ApacheRest r, String p) {
		this.rest = r;
		this.id_partie = p;
		gson = new Gson();
		this.randomGenerator = new Random();
		stratjambon = new StratDeLaMorKiTu( this.lvl_bot);
		
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
	public void afficherBoard(Board board)
	{
		System.out.println("Tours restants : "+board.nbrTurnsLeft);
		for(int i = 0; i<2;i++){
			for (int j = 0; j<3; j++)
			{
				
				if(board.playerBoards[i].fighters != null && board.playerBoards[i].fighters.length > 2){
					//System.out.println(board.playerBoards[i].fighters[j].fighterClass);
					System.out.println("--------------------------------------------------");
					System.out.println("Team : "+board.playerBoards[i].playerName);
					System.out.println("Perso : "+board.playerBoards[i].fighters[j].fighterClass);
					System.out.println("Vie : "+board.playerBoards[i].fighters[j].currentLife);
					System.out.println("Mana : "+board.playerBoards[i].fighters[j].currentMana);
					System.out.println("Status : "+board.playerBoards[i].fighters[j].states);
					System.out.println("");
				}
			}
		}
	}
	public void GetBoard () {
		String plateau = rest.Plateau_jeu(id_partie);
		
		board = gson.fromJson(plateau, Board.class);
		if(board != null)
		afficherBoard(board);
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
			//stratAnasse();
			
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
			case 0 : retour = rest.Joue_Coup(this.id_partie, "PALADIN"); System.out.println("Choisi Orc");break;
			case 1 : retour = rest.Joue_Coup(this.id_partie, "ORC");System.out.println("Choisi Guard");break;
			case 2 : retour = rest.Joue_Coup(this.id_partie, "ARCHER");System.out.println("Choisi Priest");break;
			default : retour = rest.Joue_Coup(this.id_partie, "ORC");System.out.println("Choisis Orc");
		}
		
		Analyse_Joue_Coup(retour);
	}
	
	public void Action_personnage() {
		String action = "";
		//action = strat_paul.Action_Joueur(this.board);
		action = stratjambon.Action_bot_J(board);
		
		System.out.println("Coup : " + action);
		String retour = rest.Joue_Coup(this.id_partie, action);
		Analyse_Joue_Coup(retour);
	}
	
	public String startQuentin() {
		String action = "";
		for(int i = 0; i < 3; i++) {
			int  j = randomGenerator.nextInt(3);
			while(this.board.playerBoards[1].fighters[j].isDead) {
				j = randomGenerator.nextInt(3);
			}
			
			action += "A" + Integer.toString(i+1) + ",ATTACK,E" + Integer.toString(j+1);
			if(i != 2) {
				action += "$";
			}
		}
		
		return action;
	}
	
	public void stratAnasse() {
		String action = "";
		
		// chaque itération correspond à un tours de l'un de nos 3 personnages
		for(int i = 0; i < NB_PLAYERS; i++) {
			EpicHero currentPlayer = this.board.playerBoards[0].fighters[i];
			int currentPlayerNumber = i+1;
			//int  targetNumber = randomGenerator.nextInt(NB_PLAYERS) +1;
			int targetNumber = selectFirstBotAlive(this.board.playerBoards[1].fighters);
			
			// Si pas de Mana : On attaque
			if (currentPlayer.currentMana == 0) {
				action += currentPlayer.attack(currentPlayerNumber, targetNumber);
			} else {
				// Sinon stratégie différente selon la classe
				switch (currentPlayerNumber) {
				// L'ogre
				case 1:
					if(currentPlayer.currentLife > currentPlayer.maxAvailableLife*0.9 ) {
						action += currentPlayer.yell(currentPlayerNumber, targetNumber);
					} else {
						action += currentPlayer.attack(currentPlayerNumber, targetNumber);
					}
					break;
				case 2: // Le garde
					if(currentPlayer.currentLife > currentPlayer.maxAvailableLife*0.7 ) {
						action += currentPlayer.attack(currentPlayerNumber, targetNumber);
					} else {
						action += currentPlayer.protectHimself(currentPlayerNumber);
					}
					break;
				case 3: // Le prête
					action += currentPlayer.healFighter(currentPlayerNumber, targetNumber, this.board.playerBoards[0].fighters);
					break;
				default:
					break;
				}
			}
			
			
			if(i != 2) {
				action += "$";
			}
		}
		
		System.out.println("Coup total du tours: " + action);
		String retour = rest.Joue_Coup(this.id_partie, action);
		Analyse_Joue_Coup(retour);
	}
	
	public int selectFirstBotAlive(EpicHero[] enemies) {
		for(int i = 0; i < enemies.length; i++) {
			if (!enemies[i].isDead) {
				return i+1;
			}
		}
		
		return 1;
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
	
	public void Selection_personnage2()
	{
		int current_nb;
		if(board.playerBoards[0].fighters == null) {
			current_nb = 0;
		}else {
			current_nb = board.playerBoards[0].fighters.length;
		}
		String retour = "";
		switch(current_nb) {
			case 0 : retour = rest.Joue_Coup(this.id_partie, "CHAMAN"); System.out.println("Choisi Chaman");break;
			case 1 : 
				String tmp = reflectPerso(this.board.playerBoards[1].fighters[0].fighterClass,"ARCHER",this.board.playerBoards[0].fighters);
				retour = rest.Joue_Coup(this.id_partie,tmp) ;System.out.println("Choisi "+tmp);break;
				
			case 2 : 
				String tmp2 = reflectPerso(this.board.playerBoards[1].fighters[1].fighterClass,"ARCHER",this.board.playerBoards[0].fighters);
				retour = rest.Joue_Coup(this.id_partie, tmp2);System.out.println("Choisi "+tmp2);break;
			default : retour = rest.Joue_Coup(this.id_partie, "ORC");System.out.println("Choisis Orc");
		}
		
		Analyse_Joue_Coup(retour);
	}
	
	public String reflectPerso(String CoupEnnemi,String OrcArcher,EpicHero[] NOUS)
	{
		//if(NOUS.length >2){
		//	System.out.println(NOUS[0] + ";"+NOUS[1]);
		//}
		EpicHero[] NOUSt = {null,null,null};
		for(int i = 0; i< NOUS.length; i++)
		{
			NOUSt[i] = NOUS[i];
		}
	
		if((CoupEnnemi.equalsIgnoreCase("ORC") || CoupEnnemi.equalsIgnoreCase("PRIEST"))&& 
		((NOUSt[0] != null && NOUSt[0].fighterClass != "PALADIN") ||
		(NOUSt[1] != null && NOUSt[1].fighterClass != "PALADIN"))) return "PALADIN";
		
		if(CoupEnnemi.equalsIgnoreCase("GUARD") && 
		(NOUSt[0] != null && !NOUSt[0].fighterClass.equalsIgnoreCase(OrcArcher)) ||
		(NOUSt[1] != null && !NOUSt[1].fighterClass.equalsIgnoreCase(OrcArcher))) return OrcArcher;
		
		if(CoupEnnemi.equalsIgnoreCase("PALADIN") && (
		(NOUSt[0] != null && !NOUSt[0].fighterClass.equalsIgnoreCase(OrcArcher)) &&
		(NOUSt[1] != null && !NOUSt[1].fighterClass.equalsIgnoreCase(OrcArcher)))) return OrcArcher;
		
		if(CoupEnnemi.equalsIgnoreCase("CHAMAN") && 
		(NOUSt[0] != null && !NOUSt[0].fighterClass.equalsIgnoreCase("ORC")) ||
		(NOUSt[1] != null && !NOUSt[1].fighterClass.equalsIgnoreCase("ORC"))) return "ORC";
		
		
		if(CoupEnnemi.equalsIgnoreCase("ARCHER") && 
		(NOUSt[0] != null && !NOUSt[0].fighterClass.equalsIgnoreCase("GUARD")) ||
		(NOUSt[1] != null && !NOUSt[1].fighterClass.equalsIgnoreCase("GUARD"))) return "GUARD";
		
		if(OrcArcher.equalsIgnoreCase("ORC")) return "ARCHER";
		if(OrcArcher.equalsIgnoreCase("ARCHER")) return "ORC";
		return "ERREUR";
	}
}
