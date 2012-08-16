package poc.socialtext.model;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;

public class DataMiner {

    public static void main(String[] args) {

        WebResource r = getClient().resource(getURL());
        String response = r.queryParams(getParams()).accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
        Signal[] signals = new Gson().fromJson(response, Signal[].class);
        System.out.println(Arrays.toString(signals));
        new HBaseSignalDAO().putRawSignals(signals);
    }

    private static Client getClient() {

        Client c = Client.create();
        c.setFollowRedirects(true);
        c.addFilter(new HTTPBasicAuthFilter("cosmin.ene@cegeka.be", "c0smin"));
        return c;
    }

    private static MultivaluedMap<String, String> getParams() {
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        params.add("offset", "0");
        params.add("limit", "1");
        return params;
    }

    private static String getURL() {
        return "https://cegeka.socialtext.net/data/signals";
    }
}
