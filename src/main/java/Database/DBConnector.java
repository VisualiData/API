package Database;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Gebruiker on 13-3-2017.
 */
public class DBConnector {
    private static DBConnector ourInstance = new DBConnector();
    private MongoDatabase db;

    public static DBConnector getInstance() {
        return ourInstance;
    }

    private DBConnector() {
        MongoClient client;
        String mongodb_host = "localhost";

        if(System.getenv("MONGODB_HOST") != null){
            mongodb_host = System.getenv("MONGODB_HOST");
        }
        try {
            client = new MongoClient(mongodb_host, 27017);
            db = client.getDatabase("dev");
            System.out.println("Connected to db");
        } catch (Exception e){
            System.err.println("Could not connect to MongoDB: " + e.getMessage());
        }
    }
    public JSONObject insert(BasicDBObject document, String collection_name){
        MongoCollection<BasicDBObject> collection = db.getCollection(collection_name, BasicDBObject.class);
        collection.insertOne(document);
        JSONObject result = new JSONObject();
        result.put("message", "inserted");
        return result;
    }

    public JSONArray find(String collection_name, String key, String value){
        MongoCollection<BasicDBObject> collection = db.getCollection(collection_name, BasicDBObject.class);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(key, value);
        System.out.println(value);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery)){
            result.add(document);
        }
        System.out.println(result.size() + "");
        return result;
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
