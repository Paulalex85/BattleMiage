package Battle.BattleMiage;

public class StratDeLaMorKiTu {
	
	Board board;
	int index_tour;
	int index_cible;
	int lvl_bot;
	
	public StratDeLaMorKiTu(int lvl_bot) {
		index_tour = 0;
		index_cible = -1;
		this.lvl_bot = lvl_bot;
	}
	
	//tour 0 ils attendent pour avoir 2 pa ou si le guard et le pretre ont 2 alors ils les utilisent 
	//tour 1 l'orc lance son sort sur le guard ou un des 2 autres, les 2 autres attaque le meme mec
	//tour 2 les 3 attaque la cible 
	//boucle sur le début 
	
	public String Action_Joueur(Board board) {
		this.board = board;
		String action = "";

		//ENNEMIES
		int index_orc, index_priest, index_guard;
		index_orc = getIndexClassAdversaire("ORC");
		index_priest = getIndexClassAdversaire("PRIEST");
		index_guard = getIndexClassAdversaire("GUARD");
		
		// bot 1 a 3
		if(lvl_bot >= 1 && lvl_bot <= 3) {
			if( !board.playerBoards[1].fighters[index_guard].isDead) {
				index_cible = index_guard +1;
			} else if(!board.playerBoards[1].fighters[index_orc].isDead) {
				index_cible = index_orc +1;
			}
			else {
				index_cible = index_priest +1;
			}
		}
		else { // 4 et 5, marche pas 
			if(index_guard +1 == index_cible) {
				if(!board.playerBoards[1].fighters[index_orc].isDead) {
					index_cible = index_orc +1;
				} else if (!board.playerBoards[1].fighters[index_priest].isDead) {
					index_cible = index_priest +1;
				} else {
					index_cible = index_guard +1;
				}
			}
			else {
				if(!board.playerBoards[1].fighters[index_guard].isDead ) {
					index_cible = index_guard +1;
				}
				else {
					index_cible = index_orc +1;
				}
			}
		}
		
		
		
		
		
		//FAIT LA STRAT
		if(index_tour == 0) {
			if(board.playerBoards[0].fighters[0].currentMana >= 2 && !board.playerBoards[0].fighters[0].isDead) { // garde
				boolean done = false;
				if(board.playerBoards[0].fighters[1].states != null && !board.playerBoards[0].fighters[1].isDead) { // check l'orc
					for(int i = 0 ; i <  board.playerBoards[0].fighters[1].states.size() ; i++ ) {
						if(board.playerBoards[0].fighters[1].states.get(i).type.equals("SCARED")){
							done = true;
							action += "A1,PROTECT,A2";
							break;
						}
					}
				}
				else if(board.playerBoards[0].fighters[2].states != null  && !board.playerBoards[0].fighters[2].isDead) { // check le pretre
					for(int i = 0 ; i <  board.playerBoards[0].fighters[2].states.size() ; i++ ) {
						if(board.playerBoards[0].fighters[2].states.get(i).type.equals("SCARED")){
							done = true;
							action += "A1,PROTECT,A3";
							break;
						}
					}
				}
				
				if(!done) {
					//si il a moins de vie que les 2 autres boloss il se protege
					if(board.playerBoards[0].fighters[0].maxAvailableLife - board.playerBoards[0].fighters[0].currentLife > 
							board.playerBoards[0].fighters[1].maxAvailableLife - board.playerBoards[0].fighters[1].currentLife && 
							board.playerBoards[0].fighters[0].maxAvailableLife - board.playerBoards[0].fighters[0].currentLife > 
							board.playerBoards[0].fighters[2].maxAvailableLife - board.playerBoards[0].fighters[2].currentLife && 
						!board.playerBoards[0].fighters[1].isDead && !board.playerBoards[0].fighters[2].isDead) {
						action += "A1,DEFEND,A1";
					}
					else if(board.playerBoards[0].fighters[1].maxAvailableLife -board.playerBoards[0].fighters[1].currentLife > board.playerBoards[0].fighters[2].maxAvailableLife - board.playerBoards[0].fighters[2].currentLife && 
							!board.playerBoards[0].fighters[1].isDead) {
						action += "A1,PROTECT,A2";
					}
					else if (!board.playerBoards[0].fighters[2].isDead) {
						action += "A1,PROTECT,A3";
					}
					else {
						action += "A1,DEFEND,A1";
					}
				}
				
			}else {
				action += "A1,REST,A1";
			}
			
			
			if(board.playerBoards[0].fighters[1].currentMana >= 1) {  // l'orc
				index_tour++;
			}
			action += "$A2,REST,A2";
			
			
			//pretre
			if(board.playerBoards[0].fighters[2].currentMana >= 2 && !board.playerBoards[0].fighters[2].isDead) {
				int index_to_heal = 0, diff_life = -1;
				for(int i = 0; i < 3; i++) {
					if(board.playerBoards[0].fighters[i].maxAvailableLife - board.playerBoards[0].fighters[i].currentLife > diff_life && !board.playerBoards[0].fighters[i].isDead ) {
						index_to_heal = i+1;
						diff_life = board.playerBoards[0].fighters[i].maxAvailableLife - board.playerBoards[0].fighters[i].currentLife;
					}
				}
				
				action += "$A3,HEAL,A" + Integer.toString(index_to_heal);
			}
			else {
				action += "$A3,REST,A3";
			}
			
			
		}
		else if (index_tour == 1) {
			action = "A1,ATTACK,E" + Integer.toString(index_cible) + 
					"$A2,YELL,E" + Integer.toString(index_cible) + 
					"$A3,ATTACK,E" + Integer.toString(index_cible);
			index_tour++;
		}
		else {
			action = "A1,ATTACK,E" + Integer.toString(index_cible) + 
					"$A2,ATTACK,E" + Integer.toString(index_cible) + 
					"$A3,ATTACK,E" + Integer.toString(index_cible);
			index_tour = 0;
		}


		return action;
	}
	
	public int getIndexClassAdversaire(String nom_classe) {
		for(int i = 0; i < 3; i++) {
			if(board.playerBoards[1].fighters[i].fighterClass.equals(nom_classe)) {
				return index_cible = i;
			}
		}
		return 0;
	}
}
