package api;

import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

public interface IURL {
    void openUrl();
    JSONObject interact(Request req, Response res);
}
