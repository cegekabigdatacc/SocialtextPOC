package poc.socialtext.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JsonResponseParser {

    static List<String> parseAsListOfElements(String response) {
        List<String> elements = new LinkedList<String>();
        JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();
        Iterator<JsonElement> jsonArrayIt = jsonArray.iterator();
        while (jsonArrayIt.hasNext()) {
            elements.add(jsonArrayIt.next().toString());
        }
        return elements;
    }
}