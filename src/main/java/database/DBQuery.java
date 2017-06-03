package database;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.List;

@SuppressWarnings("unchecked")
public class DBQuery {
    private static final Logger LOGGER = LogManager.getLogger(DBQuery.class);
    private static final String DB_NAME = "visualidata";
    private DBQuery(){}

    public static void checkDBUp(){
        try {
            MongoClient client = DBConnector.getInstance().getClient();
            client.getConnectPoint();
            DBConnector.setDBState(DatabaseState.STATE_RUNNING);
        } catch(MongoTimeoutException e){
            LOGGER.error(e);
            LOGGER.debug("DB down");
            DBConnector.setDBState(DatabaseState.STATE_DOWN);
        }
    }

    public static boolean checkAuthorized(String key){
        checkDBUp();
        JSONArray result = find("auth_keys", "key", key, new BasicDBObject());
        return result.size() == 1;
    }

    public static JSONObject deleteQuery(String collectionName, BasicDBObject find){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        DeleteResult deleteResult = collection.deleteOne(find);
        boolean deleted = false;
        if(deleteResult.getDeletedCount() > 0){
            deleted = true;
        }
        JSONObject result = new JSONObject();
        result.put("deleted", deleted);
        return result;
    }

    // Find document by specific value
    public static JSONArray find(String collectionName, String key, String value, BasicDBObject fields){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(key, value);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery).projection(fields)){
            result.add(document);
        }
        return result;
    }

    public static JSONArray findQuery(String collectionName, BasicDBObject whereQuery, BasicDBObject fields){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery).projection(fields)){
            result.add(document);
        }
        return result;
    }

    public static JSONArray findQuery(String collectionName, BasicDBObject whereQuery, BasicDBObject fields, BasicDBObject sort){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONArray result = new JSONArray();
        for(BasicDBObject document: collection.find(whereQuery).projection(fields).sort(sort)){
            result.add(document);
        }
        return result;
    }

    // Insert single document
    public static JSONObject insert(String collectionName, BasicDBObject document){
        MongoDatabase db = DBConnector.getInstance().getDB();
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

    public static JSONObject insertMany(String collectionName, List<BasicDBObject> documents){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        JSONObject result = new JSONObject();
        boolean inserted;
        try {
            collection.insertMany(documents);
            inserted = true;
        }catch (Exception e){
            LOGGER.debug(e);
            inserted = false;
        }
        result.put("inserted", inserted);
        return result;
    }

    public static void renameCollection(String collectionName, String newCollectionName){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        collection.renameCollection(new MongoNamespace(DB_NAME, newCollectionName));
    }

    public static JSONObject updateQuery(String collectionName, BasicDBObject find, BasicDBObject replace){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        BasicDBObject updateResult = collection.findOneAndUpdate(find, new BasicDBObject("$set", replace));
        boolean updated = false;
        if(updateResult != null) {
            updated = true;
        }
        JSONObject result = new JSONObject();
        result.put("updated", updated);
        return result;
    }

    public static JSONObject replaceQuery(String collectionName, BasicDBObject find, BasicDBObject replace){
        MongoDatabase db = DBConnector.getInstance().getDB();
        MongoCollection<BasicDBObject> collection = db.getCollection(collectionName, BasicDBObject.class);
        BasicDBObject updateResult = collection.findOneAndReplace(find, replace);
        boolean updated = false;
        if(updateResult != null) {
            updated = true;
        }
        JSONObject result = new JSONObject();
        result.put("updated", updated);
        return result;
    }
}
