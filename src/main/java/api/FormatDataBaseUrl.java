package api;

import database.DBFormatter;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.get;

/**
 * Created by Gebruiker on 23-3-2017.
 */
public class FormatDataBaseUrl implements IURL {
    /**
     * @api {get} /formatDB/:timeframe/:newtimeframe Start formatting of db values
     * @apiName FormatDb
     * @apiGroup Data
     *
     * @apiParam {String} timeframe from timeframe.
     * @apiParam {String} newtimeframe to timeframe
     */
    @Override
    public void openUrl(){
        get("/formatDB/:timeframe/:newtimeframe", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res){
        DBFormatter formatter = new DBFormatter();
        if (formatter.formatDB(req.params(":timeframe"),req.params("newtimeframe"))){
            return ResponseUtil.generateSuccessMessage("Data formatted", ResponseCodes.SUCCESS);
        }else{
            return ResponseUtil.generateFailed("Data not formatted",400);
        }
    }
}
