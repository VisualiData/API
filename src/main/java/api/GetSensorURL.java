package api;

import org.json.simple.JSONObject;
import sensors.SensorsRoute;
import spark.Request;
import spark.Response;
import util.ResponseCodes;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetSensorURL implements IURL{
    @Override
    public void openUrl() {get("/sensor/:id", this::interact);}

    @Override
    public JSONObject interact(Request req, Response res) {
        SensorsRoute sensorsRoute = new SensorsRoute();
        JSONObject result = sensorsRoute.getSensor(req.params("id"));
        if (result != null) {
            return ResponseUtil.generateSuccess(result, ResponseCodes.SUCCESS);
        }else{
            return ResponseUtil.generateFailed("Sensor not found", ResponseCodes.NOT_FOUND);
        }
    }
}
