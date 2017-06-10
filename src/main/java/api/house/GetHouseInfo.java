package api.house;

import api.IURL;
import com.mongodb.BasicDBObject;
import org.json.simple.JSONObject;
import sensors.HouseRoute;
import spark.Request;
import spark.Response;
import util.DBNames;
import util.HttpCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetHouseInfo implements IURL{
    /**
     * @api {get} /house-info/:id Request Sensors from specific floor in house
     * @apiHeader {String} Authorization Authorization key.
     * @apiName GetHouseInfo
     * @apiGroup House
     *
     * @apiParam {String} id House ID.
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl() {
        get("/house-info/:id", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res) {
        HouseRoute houseRoute = new HouseRoute();
        BasicDBObject requestBody = new BasicDBObject(DBNames.HOUSE_ID, req.params("id"));
        JSONObject result = houseRoute.getHouseInfo(requestBody);
        if (result != null) {
            return ResponseUtil.generateSuccess(result, HttpCodes.SUCCESS);
        } else {
            return ResponseUtil.generateFailed("No house found", HttpCodes.NOT_FOUND);
        }
    }
}
