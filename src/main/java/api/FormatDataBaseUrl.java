package api;

import database.DBFormatter;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class FormatDataBaseUrl implements IURL {
    /**
     * @api {get} /formatDB/:timeframe/:newtimeframe Start formatting of db values
     * @apiHeader {String} Authorization Authorization key.
     * @apiName FormatDb
     * @apiGroup Data
     *
     * @apiParam {String} timeframe from time frame
     * @apiParam {String} newtimeframe to time frame
     * @apiVersion 1.0.0
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
