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
	
	public String Action_bot_J(Board board) {
		this.board = board;
		String action = "";
		
		int index_1 = -1;
		int index_2 = -1;
		int index_3 = -1; // définit l'ordre
		
		if(getIndexClassAdversaire("ORC") != -1 && index_3 == -1) {
			if( index_1 == -1 ) {
				index_1 = getIndexClassAdversaire("ORC");
			}
		}
		if(getIndexClassAdversaire("ARCHER") != -1 && index_3 == -1) {
			if( index_1 == -1 ) {
				index_1 = getIndexClassAdversaire("ARCHER");
			}else if(index_2 == -1) {
				index_2 = getIndexClassAdversaire("ARCHER");
			}
			else {
				index_3= getIndexClassAdversaire("ARCHER");
			}
		}
		if(getIndexClassAdversaire("CHAMAN") != -1 && index_3 == -1) {
			if( index_1 == -1 ) {
				index_1 = getIndexClassAdversaire("CHAMAN");
			}else if(index_2 == -1) {
				index_2 = getIndexClassAdversaire("CHAMAN");
			}
			else {
				index_3= getIndexClassAdversaire("CHAMAN");
			}
		}
		if(getIndexClassAdversaire("PALADIN") != -1 && index_3 == -1) {
			if( index_1 == -1 ) {
				index_1 = getIndexClassAdversaire("PALADIN");
			}else if(index_2 == -1) {
				index_2 = getIndexClassAdversaire("PALADIN");
			}
			else {
				index_3= getIndexClassAdversaire("PALADIN");
			}
		}
		if(getIndexClassAdversaire("PRIEST") != -1 && index_3 == -1) {
			if( index_1 == -1 ) {
				index_1 = getIndexClassAdversaire("PRIEST");
			}else if(index_2 == -1) {
				index_2 = getIndexClassAdversaire("PRIEST");
			}
			else {
				index_3= getIndexClassAdversaire("PRIEST");
			}
		}
		if(getIndexClassAdversaire("GUARD") != -1 && index_3 == -1) {
			if( index_1 == -1 ) {
				index_1 = getIndexClassAdversaire("GUARD");
			}else if(index_2 == -1) {
				index_2 = getIndexClassAdversaire("GUARD");
			}
			else {
				index_3= getIndexClassAdversaire("GUARD");
			}
		}
		
		if( !board.playerBoards[1].fighters[index_1].isDead) {
			index_cible = index_1 +1;
		} else if ( !board.playerBoards[1].fighters[index_2].isDead) {
			index_cible = index_2 +1;
		}else {
			index_cible= index_3 +1;
		}
		
		if(index_tour == 0) {
			
			
			
			if(board.playerBoards[0].fighters[1].currentMana >= 1) {  // l'orc
				index_tour++;
			}
			
			action = "A1,REST,A1$A2,REST,A2";
			
			if(board.playerBoards[0].fighters[2].currentMana >=2) {
				int min = board.playerBoards[1].fighters[0].currentLife;
				int index_jambon = 0;
				for (int i = 0; i < 3; i++) {
					if(!board.playerBoards[1].fighters[i].isDead && min > board.playerBoards[1].fighters[i].currentLife) {
						min = board.playerBoards[1].fighters[i].currentLife;
						index_jambon = i;
					}
				}
				
				action += "$A3,FIREBOLT,E"+ Integer.toString(index_jambon +1);
			}
			else {
				action += "$A3,REST,A3";
			}
			
		
		}else if (index_tour == 1) { // paladin , orc, archer
			action = "A1,ATTACK,E" + Integer.toString(index_cible) + 
					"$A2,YELL,E" + Integer.toString(index_cible) + 
					"$A3,ATTACK,E" + Integer.toString(index_cible);
			index_tour++;
		}
		else { // paladin , orc, archer
			String action_paladin = "";
			int guard_ennemy = getIndexClassAdversaire("GUARD");
			int chaman_ennemy = getIndexClassAdversaire("CHAMAN");
			if(board.playerBoards[0].fighters[0].currentMana >=2) {
				if(guard_ennemy != -1 && !board.playerBoards[1].fighters[guard_ennemy].isDead) {
					action_paladin = "A1,CHARGE,E" + Integer.toString(guard_ennemy +1);
				} else if(chaman_ennemy != -1 && !board.playerBoards[1].fighters[chaman_ennemy].isDead) {
					action_paladin = "A1,CHARGE,E" + Integer.toString(chaman_ennemy +1);
				}
				else {
					action_paladin = "A1,CHARGE,E" + Integer.toString(index_cible);
				}
			}
			else {
				action_paladin = "A1,ATTACK,E" + Integer.toString(index_cible);
			}
			
			
			
			action = action_paladin + 
					"$A2,ATTACK,E" + Integer.toString(index_cible) + 
					"$A3,ATTACK,E" + Integer.toString(index_cible);
			index_tour = 0;
		}
		
		return action;
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
		return -1;
	}
}