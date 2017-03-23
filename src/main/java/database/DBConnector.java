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
    private static final Logger LOGGER = LogManager.getLogger(DBConnector.class);
    private static DBConnector ourInstance = new DBConnector();
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
        ServerAddress address = new ServerAddress(mongodbHost, 27018);
        MongoCredential credential = null;
        try {
            credential = MongoCredential.createCredential(user, "admin", password.toCharArray());
            client = new MongoClient(address, Arrays.asList(credential), optionsBuilder.build());
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

    // Close db connection
    public static void close(){
        client.close();
    }

    // Insert single document
    @SuppressWarnings("unchecked")
    public JSONObject insert(BasicDBObject document, String collectionName){
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
    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public JSONArray findQuery(String collectionName, BasicDBObject whereQuery){
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery)){
            result.add(document);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    /* TODO sort result */
    public JSONArray getAllSensors(){
        JSONArray result = new JSONArray();
        for(String collectionName: db.listCollectionNames()){
            if(!"sensordata".equals(collectionName)) {
                result.add(collectionName);
            }
        }
        return result;
    }

    /* TODO update */

    public JSONObject Update (String key, JSONObject Value){
        return new JSONObject();
    }

    public JSONObject Delete (String key){
        return new JSONObject();
    }
}
