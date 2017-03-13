package API;

import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public interface IURL {
    public void OpenUrl();
    public JSONObject Interact(Request req, Response res);
}
