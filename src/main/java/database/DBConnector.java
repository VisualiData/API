package database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

@SuppressWarnings("unchecked")
public class DBConnector {
    private static final Logger LOGGER = LogManager.getLogger(DBConnector.class);
    private static final DBConnector ourInstance = new DBConnector();
    private static MongoDatabase db;
    private static MongoClient client;
    public static DBConnector getInstance() {
        return ourInstance;
    }

    // Initialize db connection
    public static void initDB(){
        String mongodbHost = "localhost";
        if(System.getenv("MONGODB_HOST") != null){
            mongodbHost = System.getenv("MONGODB_HOST");
        }
        String user = System.getenv("MONGODB_USER");
        String password = System.getenv("MONGODB_PASS");
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();
            optionsBuilder.serverSelectionTimeout(1000);
            optionsBuilder.writeConcern(WriteConcern.ACKNOWLEDGED);
        ServerAddress address = new ServerAddress(mongodbHost, 27017);
        try {
            MongoCredential credential = MongoCredential.createCredential(user, "admin", password.toCharArray());
            client = new MongoClient(address, Collections.singletonList(credential), optionsBuilder.build());
        }catch (NullPointerException e){
            LOGGER.error("Username or password not defined in environment variables");
            LOGGER.error(e);
            client = new MongoClient(address, optionsBuilder.build());
        }

        try{
            client.getConnectPoint();
            LOGGER.debug("Connected to db");
        } catch (MongoTimeoutException e){
            LOGGER.error("Could not connect to db");
            LOGGER.error(e);
        }
        db = client.getDatabase("visualidata");
    }

    // Insert single document
    public JSONObject insert(String collectionName, BasicDBObject document){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONObject result = new JSONObject();
        boolean inserted;
        try {
            collection.insertOne(document);
            inserted = true;
        }catch (Exception e){
            LOGGER.debug(e);
            inserted = false;
        }
        result.put("inserted", inserted);
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

    public JSONArray findQuery(String collectionName, BasicDBObject whereQuery, BasicDBObject fields){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery).projection(fields)){
            result.add(document);
        }
        return result;
    }

    public JSONObject updateQuery(String collectionName, BasicDBObject find, BasicDBObject replace){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        BasicDBObject updateResult = collection.findOneAndUpdate(find, new BasicDBObject("$set", replace));
        boolean updated = false;
        if(updateResult != null) {
            updated = true;
        }
        JSONObject result = new JSONObject();
        result.put("success", updated);
        return result;
    }

    public JSONObject deleteQuery(String collectionName, BasicDBObject find){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        DeleteResult deleteResult = collection.deleteOne(find);
        boolean deleted = false;
        if(deleteResult.getDeletedCount() > 0){
            deleted = true;
        }
        JSONObject result = new JSONObject();
        result.put("success", deleted);
        return result;
    }

    // Get sensors for which a collection exists and sort the result
    public JSONArray getAllSensors(){
        List<String> sensors = new ArrayList<>();
        for(String collectionName: db.listCollectionNames()){
            if(!"sensordata".equals(collectionName)) {
                sensors.add(collectionName);
            }
        }
        Collections.sort(sensors);
        JSONArray result = new JSONArray();
        result.addAll(sensors);
        return result;
    }
}
