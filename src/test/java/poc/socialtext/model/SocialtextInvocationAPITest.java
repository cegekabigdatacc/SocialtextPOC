package poc.socialtext.model;

import com.google.gson.Gson;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.fest.assertions.Assertions.assertThat;

public class SocialtextInvocationAPITest {

    public static final String USER = "cosmin.ene@cegeka.be";
    public static final String PASS = "c0smin";
    public Integer offset = 0;
    public Integer limit = 100;

    RequestSpecification request;
    Gson gson = new Gson();

    @Before
    public void setUp(){
        request = given().auth().basic(USER, PASS);
        request.header("Accept", "application/json");
    }

    @Test
    public void canGetAUserFromSocialText() {
        assertThat(getAllSignals()).isEqualTo(151);
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
}

