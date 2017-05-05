package api;

import com.mongodb.BasicDBObject;
import org.json.simple.JSONObject;
import sensors.HouseRoute;
import spark.Request;
import spark.Response;
import util.DBNames;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetSensorsHouseUrl implements IURL{
    /**
     * @api {get} /house/:id/:floor Request Sensors from specific floor in house
     * @apiHeader {String} Authorization Authorization key.
     * @apiName GetHouseSensors
     * @apiGroup Sensor
     *
     * @apiParam {String} id House ID.
     * @apiParam {String} floor Floor (optional).
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl() {
        get("/house/:id", this::interact);
        get("/house/:id/:floor", this::interact);
    }

    @Override
    public JSONObject interact(Request req, Response res) {
        HouseRoute houseRoute = new HouseRoute();
        BasicDBObject requestBody = new BasicDBObject();
        requestBody.put(DBNames.HOUSE_ID, req.params("id"));
        if(req.params("floor") != null) {
            requestBody.put(DBNames.FLOOR_ID, req.params("floor"));
        }
        return ResponseUtil.generateSuccess(houseRoute.getAllSensors(requestBody), ResponseCodes.SUCCESS);
    }
}
