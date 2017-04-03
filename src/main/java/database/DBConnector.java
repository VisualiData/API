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
/*TODO Rewrite to more files and include dbstate*/
public class DBConnector {
    private static final Logger LOGGER = LogManager.getLogger(DBConnector.class);
    private static final String DB_NAME = "visualidata";
    private static final DBConnector ourInstance = new DBConnector();
    private static MongoDatabase db;
    private static MongoClient client;
    private static DatabaseState state;
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
            state = DatabaseState.STATE_RUNNING;
        } catch (MongoTimeoutException e){
            LOGGER.error("Could not connect to db");
            LOGGER.error(e);
            state = DatabaseState.STATE_DOWN;
        }
        db = client.getDatabase(DB_NAME);
    }

    MongoDatabase getDB(){
        return db;
    }

    MongoClient getClient(){
        return client;
    }

    public static DatabaseState getDBState() {
        return state;
    }
}
