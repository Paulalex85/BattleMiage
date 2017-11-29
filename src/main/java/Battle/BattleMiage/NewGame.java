package Battle.BattleMiage;

public class NewGame {
	ApacheRest rest;
	Param param;
	
	String id_partie = "";
	public NewGame(Param tests, String difficulte) { // vs bot 
		this.param = tests;
		this.rest = new ApacheRest("http://prod.codeandplay.date/epic-ws/epic");
		
		//TODO gerer si vs joueur ou vs bot avec param
		id_partie = rest.Init_versus_bot(difficulte);
		BoucleJeu boucle = new BoucleJeu(rest, id_partie, Integer.parseInt(difficulte));
	}
	
	public NewGame(Param tests) { // vs team 
		this.param = tests;
		this.rest = new ApacheRest("http://prod.codeandplay.date/epic-ws/epic");
		
		//TODO gerer si vs joueur ou vs bot avec param
		id_partie = rest.Init_versus_joueur();
	}
	
	
}
