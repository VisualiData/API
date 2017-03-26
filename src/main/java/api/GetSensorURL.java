package api;

import org.json.simple.JSONObject;
import sensors.SensorsRoute;
import spark.Request;
import spark.Response;
import util.ResponseUtil;

import static spark.Spark.get;

public class GetSensorURL implements IURL{
    @Override
    public void openUrl() {get("/sensor/:id", this::interact);}

    @Override
    public JSONObject interact(Request req, Response res) {
        SensorsRoute sensorsRoute = new SensorsRoute();
        return ResponseUtil.generateSuccess(sensorsRoute.getSensor(req.params("id")));
    }
}
