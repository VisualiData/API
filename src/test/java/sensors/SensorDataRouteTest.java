package sensors;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class SensorDataRouteTest {
    @Test
    public void testOne() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = SensorDataRoute.class.getDeclaredMethod("createDocument", BasicDBObject.class);
        method.setAccessible(true);
        List<BasicDBObject> result = (List<BasicDBObject>) method.invoke(new SensorDataRoute(), createInputData());
        assertEquals("Data formatted correctly", expectedResult(), result);
    }

    private BasicDBObject createInputData(){
        return (BasicDBObject) JSON.parse("{\"sensor_id\":\"Test-Node-03\",\"values\":[{\"value\": 20, \"type\":\"Temperature\", \"timestamp\": 1492007671552}" +
                                                    ",{\"value\": 980, \"type\":\"Pressure\", \"timestamp\": 1492007671552}]}");
    }

    private  List<BasicDBObject> expectedResult(){
        return (List<BasicDBObject>) JSON.parse("[{ \"id\" : \"Test-Node-03\" , \"timeframe\" : \"frame\" , \"timestamp\" : \"2017-04-12T16:34:31\" , " +
                                                            "\"type\" : \"Temperature\" , \"value\" : 20.0}, { \"id\" : \"Test-Node-03\" , \"timeframe\" : \"frame\" ," +
                                                            "\"timestamp\" : \"2017-04-12T16:34:31\" , \"type\" : \"Pressure\" , \"value\" : 980.0}]");
    }
}
