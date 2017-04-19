package api;

import sensors.SensorDataRoute;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.post;

/**
 * Passes data to DB class and returns OK status
 */
public class PostSensorDataUrl implements IURL{
    /**
     * @api {post} /sensordata Post Sensor data
     * @apiHeader {String} Authorization Authorization key.
     * @apiName PostData
     * @apiGroup Data
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {"statuscode":202,"data":[],"message":"Data added","status":"success"}
     * @apiVersion 1.0.0
     */
    @Override
    public void openUrl(){
        post("/sensordata", ( req, res) -> {
            if("application/json".equals(req.contentType())){
                interact(req, res);
                return ResponseUtil.generateSuccessMessage("Data added", ResponseCodes.ACCEPTED);
            }
            return ResponseUtil.generateFailed("Send json format", 400);
        });

        /**
         * @api {post} /sensordata/dummy Post dummy sensor data
         * @apiHeader {String} Authorization Authorization key.
         * @apiName PostDummyData
         * @apiGroup Data
         * @apiDescription Post to this route does not add a timestamp unlike a post to /sensordata
         * @apiSuccessExample {json} Success-Response:
         *     HTTP/1.1 200 OK
         *     {"statuscode":202,"data":[],"message":"Data added","status":"success"}
         * @apiVersion 1.0.0
         */
        post("/sensordata/dummy", (req,res) ->{
            if("application/json".equals(req.contentType())){
                if(interactDummy(req)) {
                    return ResponseUtil.generateSuccessMessage("Data added", ResponseCodes.ACCEPTED);
                }else{
                    return ResponseUtil.generateFailed("Data not added", 400);
                }
            }
            return ResponseUtil.generateFailed("Send json format", 500);
        });
    }
    @Override
    public JSONObject interact(Request req, Response res){
        SensorDataRoute sensorDataRoute = new SensorDataRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        return sensorDataRoute.insertSensorData(requestBody);
    }

    private boolean interactDummy(Request req){
        SensorDataRoute sensorDataRoute = new SensorDataRoute();
        BasicDBObject requestBody = (BasicDBObject) JSON.parse(req.body());
        JSONObject result = sensorDataRoute.insertSensorDummyData(requestBody);
        return (boolean) result.get("inserted");
    }
}
