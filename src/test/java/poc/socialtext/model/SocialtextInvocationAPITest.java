package poc.socialtext.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.avro.generic.GenericData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.Assertions.assertThat;

public class SocialtextInvocationAPITest {

    public static final String USER = "cosmin.ene@cegeka.be";
    public static final String PASS = "c0smin";
    public Integer offset = 0;
    public Integer limit = 50;

    RequestSpecification request;
    Gson gson = new Gson();

    @Before
    public void setUp(){
        request = given().auth().basic(USER, PASS);
        request.header("Accept", "application/json");
    }

    @Test
    public void canGetAllSignalsFromSocialText() {
        assertThat(getAllSignals()).isEqualTo(142);
    }

    @Test
    public void canGet50SignalsAsStringElementsFromSocialText() {
        assertThat(getAllSignalsAsStringElements().size()).isEqualTo(50);
    }
    
    private Integer getAllSignals() {
        String path = "";
        Integer numberOfSignals = 0;
        for (int i = offset; i < (limit + offset); i = i + limit) {

            path = "https://cegeka.socialtext.net/data/signals?limit="+limit+"&offset="+offset;
            String json = request.get(path).asString();

            Signal[] signals = gson.fromJson(json, Signal[].class);
            numberOfSignals += signals.length;
            offset = limit;
            limit = limit + offset + 1;
        }
        return numberOfSignals;
    }
    
    private List<String> getAllSignalsAsStringElements() {        
        String path = "https://cegeka.socialtext.net/data/signals?limit="+limit+"&offset="+offset;
        List<String> elements = new ArrayList<String>();
        JsonParser parser = new JsonParser();
        JsonArray arrayElements = parser.parse(request.get(path).asString()).getAsJsonArray();
        for (int i = 0; i < arrayElements.size(); i++) {
            elements.add(arrayElements.get(i).toString());
        }
        return elements;
    }

}

