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
	
	public String Init_versus_joueur()
	{
		List<String> args = new ArrayList<String>();
		args.add("player");
		args.add("getIdEquipe");
		args.add(nomEquipe);
		args.add(mdpEquipe);
		
		return Request_GET(args);
	}
	
}
