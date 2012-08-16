package poc.socialtext.model;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

public class SocialtextRepository {

    private static final String USERNAME = "cosmin.ene@cegeka.be";
    private static final String PASSWORD = "c0smin";
    private static final String SIGNALS_URL = "https://cegeka.socialtext.net/data/signals";
    private static final String OFFSET = "0";
    private static final String LIMIT = "10";

    List<String> retrieveSignals() {
        WebResource webResource = getClient().resource(SIGNALS_URL);
        String response = webResource.queryParams(getWebResourceParams()).accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        return JsonResponseParser.parseAsListOfElements(response);
    }

    private Client getClient() {

        Client c = Client.create();
        c.setFollowRedirects(true);
        c.addFilter(new HTTPBasicAuthFilter(USERNAME, PASSWORD));
        return c;
    }

    private MultivaluedMap<String, String> getWebResourceParams() {
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("offset", OFFSET);
        params.add("limit", LIMIT);
        return params;
    }
}