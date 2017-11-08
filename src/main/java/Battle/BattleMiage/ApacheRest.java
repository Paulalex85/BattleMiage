package Battle.BattleMiage;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ApacheRest {
	
	public ApacheRest()
	{
		
	}
	
	public String Request_GET(String url) {
	    Client restClient = Client.create();
	    WebResource webResource = restClient.resource(url);
	    ClientResponse resp = webResource.accept("application/json")
	                                                .get(ClientResponse.class);
	    if(resp.getStatus() != 200){
	        System.err.println("Unable to connect to the server");
	    }
	    String output = resp.getEntity(String.class);
	    System.out.println("response: "+output);
	    return output;
	}
}
