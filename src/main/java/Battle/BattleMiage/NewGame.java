package Battle.BattleMiage;

public class NewGame {
	ApacheRest rest;
	Param param;
	
	String id_partie = "";
	public NewGame(Param tests) {
		this.param = tests;
		this.rest = new ApacheRest("http://challengemiage.codeandplay.date/epic-ws/epic");
		
		//TODO gerer si vs joueur ou vs bot avec param
		id_partie = rest.Init_versus_bot(1);
	}
	
	
}
