package database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class DBConnector {
    private static final Logger LOGGER = LogManager.getLogger();
    private static DBConnector ourInstance = new DBConnector();
    private static MongoDatabase db;
    private static MongoClient client;
    public static DBConnector getInstance() {
        return ourInstance;
    }

    // Initialize db connection
    public static void initDB(){
        String mongodbHost = "localhost";
        String user = System.getenv("MONGODB_USER");
        String password = System.getenv("MONGODB_PASS");

        if(System.getenv("MONGODB_HOST") != null){
            mongodbHost = System.getenv("MONGODB_HOST");
        }
        MongoCredential credential = MongoCredential.createCredential(user, "admin", password.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder()
                .serverSelectionTimeout(1000)
                .build();
        ServerAddress address = new ServerAddress(mongodbHost, 27017);
        client = new MongoClient(address, Arrays.asList(credential), options);
        try{
            client.getConnectPoint();
            LOGGER.debug("Connected to db");
        } catch (MongoTimeoutException e){
            LOGGER.error("Could not connect to db");
            LOGGER.error(e);
        }
        db = client.getDatabase("dev");
    }

    // Close db connection
    public static void close(){
        client.close();
    }

    // Insert single document
    public JSONObject insert(BasicDBObject document, String collectionName){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        collection.insertOne(document);
        JSONObject result = new JSONObject();
        result.put("message", "inserted");
        return result;
    }

    // Find document by specific value
    public JSONArray find(String collectionName, String key, String value){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(key, value);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery)){
            result.add(document);
        }
        return result;
    }

    public JSONArray findQuery(String collectionName, BasicDBObject whereQuery){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery)){
            result.add(document);
        }
        return result;
    }
    /* TODO update */
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
