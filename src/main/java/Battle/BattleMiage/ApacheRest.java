package Battle.BattleMiage;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ApacheRest {
	
	final String nomEquipe = "Les%20Lions%20Sots";
	final String mdpEquipe = "HakunaMatataMaisQuellePhraseMagnifique";
	String url = "";
	String id = "";
	public ApacheRest(String url_serv)
	{
		this.url = url_serv;
		Set_Identifiant_Team();
	}
	
	private String Request_GET(List<String> list_arg) {
		String urlget = url;
		for(int i = 0; i < list_arg.size(); i++)
		{
			urlget += "/" + list_arg.get(i);
		}
		System.out.println("envoie: " + urlget);
	    Client restClient = Client.create();
	    WebResource webResource = restClient.resource(urlget);
	    ClientResponse resp = webResource.accept("application/json")
	                                                .get(ClientResponse.class);
	    if(resp.getStatus() != 200){
	        System.err.println("Unable to connect to the server");
	    }
	    String output = resp.getEntity(String.class);
	    System.out.println("response: "+output);
	    return output;
	}
	
	public String PingPong() {
		List<String> args = new ArrayList<String>();
		args.add("ping");
		return Request_GET(args);
	}
	
	private void Set_Identifiant_Team() {
		List<String> args = new ArrayList<String>();
		args.add("player");
		args.add("getIdEquipe");
		args.add(nomEquipe);
		args.add(mdpEquipe);
		
		this.id = Request_GET(args);
	}
	
	/***
	 * init pour vs joueurs 
	 * @return : id partie
	 */
	public String Init_versus_joueur()
	{
		List<String> args = new ArrayList<String>();
		args.add("versus");
		args.add("next");
		args.add(id);
		
		return Request_GET(args);
	}
	
	/***
	 * Init partie vs bot
	 * @param nBot : difficulte du bot
	 * @return id de la partie
	 */
	public String Init_versus_bot(int nBot)
	{
		List<String> args = new ArrayList<String>();
		args.add("practice");
		args.add("new");
		args.add(String.valueOf(nBot));
		args.add(id);
		
		return Request_GET(args);
	}
	
	/***
	 * 
	 * @param idpartie : id de la partie
	 * @return retourne le statut de la partie
	 */
	public String Statut_partie(String idpartie)
	{
		List<String> args = new ArrayList<String>();
		args.add("game");
		args.add("status");
		args.add(idpartie);
		args.add(id);
		
		return Request_GET(args);
	}
	
	/***
	 * 
	 * @param idpartie : id de la partie
	 * @return retourne plateau de jeu, equipe 1 est la notre 
	 */
	public String Plateau_jeu(String idpartie)
	{
		List<String> args = new ArrayList<String>();
		args.add("game");
		args.add("board");
		args.add(idpartie);
		args.add(id);
		
		return Request_GET(args);
	}
	
	/***
	 * 
	 * @param idpartie : id de la partie
	 * @return retourne dernier coup
	 */
	public String Dernier_Coup(String idpartie)
	{
		List<String> args = new ArrayList<String>();
		args.add("game");
		args.add("getlastmove");
		args.add(idpartie);
		args.add(id);
		
		return Request_GET(args);
	}
	
	/***
	 * 
	 * @param idpartie id de la partie
	 * @param coup detail coup joue
	 * @return retourne statut
	 */
	public String Joue_Coup(String idpartie, String coup)
	{
		List<String> args = new ArrayList<String>();
		args.add("game");
		args.add("play");
		args.add(idpartie);
		args.add(id);
		args.add(coup);
		
		return Request_GET(args);
	}
	
	
}
