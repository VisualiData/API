package util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public final class ResponseUtil {
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String STATUSCODE = "statuscode";

    private ResponseUtil(){}

    @SuppressWarnings("unchecked")
    public static JSONObject generateSuccess(JSONArray data, int statusCode){
        JSONObject output = new JSONObject();
        output.put(MESSAGE, "");
        output.put(STATUS, "success");
        output.put(STATUSCODE, statusCode);
        output.put("data", data);
        return output;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject generateSuccess(JSONObject data, int statusCode){
        JSONObject output = new JSONObject();
        output.put(MESSAGE, "");
        output.put(STATUS, "success");
        output.put(STATUSCODE, statusCode);
        output.put("data", data);
        return output;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject generateSuccessMessage(String message, int statusCode){
        JSONObject output = new JSONObject();
        output.put(MESSAGE, message);
        output.put(STATUS, "success");
        output.put(STATUSCODE, statusCode);
        output.put("data", new JSONArray());
        return output;
    }

    @SuppressWarnings("unchecked")
    public static JSONObject generateFailed(String message, int statusCode){
        JSONObject output = new JSONObject();
        output.put(MESSAGE, message);
        output.put(STATUS, "error");
        output.put(STATUSCODE, statusCode);
        output.put("data", new JSONArray());
        return output;
    }
}
