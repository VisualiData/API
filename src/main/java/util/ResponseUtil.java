package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class ResponseUtil {
    private static final Logger LOGGER = LogManager.getLogger();
    private ResponseUtil(){}
    @SuppressWarnings("unchecked")
    public static JSONObject generateSuccess(JSONArray data){
        JSONObject output = new JSONObject();
        output.put("message", "");
        output.put("status", "success");
        output.put("statuscode", 200);
        output.put("data", data);
        return output;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject generateSuccess(JSONObject data){
        JSONObject output = new JSONObject();
        output.put("message", "");
        output.put("status", "success");
        output.put("statuscode", 200);
        output.put("data", data);
        return output;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject generateSuccessMessage(String message){
        JSONObject output = new JSONObject();
        output.put("message", "");
        output.put("status", message);
        output.put("statuscode", 200);
        output.put("data", new JSONArray());
        return output;
    }

    @SuppressWarnings("unchecked")
    public static String generateFailed(String message, int statuscode){
        JSONObject output = new JSONObject();
        output.put("message", message);
        output.put("status", "error");
        output.put("statuscode", statuscode);
        output.put("data", new JSONArray());
        return output.toJSONString();
    }
}
