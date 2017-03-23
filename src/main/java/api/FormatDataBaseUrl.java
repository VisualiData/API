package api;

import database.DBFormatter;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

import static spark.Spark.get;

/**
 * Created by Gebruiker on 23-3-2017.
 */
public class FormatDataBaseUrl implements IURL {
    public void OpenUrl(){
        get("/formatDB/:timeframe/:newtimeframe", (req, res) -> {
            return Interact(req, res);
        });
    }
    public JSONObject Interact(Request req, Response res){
        DBFormatter formatter = new DBFormatter();
        if (formatter.formatDB(req.params(":timeframe"),req.params("newtimeframe"))){
            return ResponseUtil.generateSuccessMessage("Data formatted");
        }else{
            return ResponseUtil.generateFailed("Data not formatted",400);
        }
    }
}
