package api;

import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public interface IURL {
    void openUrl();
    JSONObject interact(Request req, Response res);
}
