package DataBase;

import org.json.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class DBConnector {
    private static DBConnector ourInstance = new DBConnector();

    public static DBConnector getInstance() {
        return ourInstance;
    }

    private DBConnector() {

    }

    public JSONObject Create (String key, JSONObject Value){
        return new JSONObject();
    }

    public JSONObject Read(String key){
        return new JSONObject();
    }

    public JSONObject Update (String key, JSONObject Value){
        return new JSONObject();
    }

    public JSONObject Delete (String key){
        return new JSONObject();
    }
}
