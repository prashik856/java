import com.prashik.flexy.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;


/**
 * Class to test all Utility functions.
 */
public class UtilsTest {

    /**
     * Function to test if JsonBuilder is working as expected.
     */
    @Test
    public void testJSONBuilder() {
        JsonBuilderFactory builderUtils = Utils.getJSONBuilder();
        JsonObject jsonObjectUtils = builderUtils.createObjectBuilder()
                .add("testKey", "testValue")
                .add("prashik", "raut")
                .build();

        JsonBuilderFactory builderFactory = Json.createBuilderFactory(
                Collections.emptyMap());
        JsonObject jsonObject = builderFactory.createObjectBuilder()
                .add("testKey", "testValue")
                .add("prashik", "raut")
                .build();

        Assertions.assertEquals(
                jsonObjectUtils.getString("testKey"),
                jsonObject.getString("testKey"));

        Assertions.assertEquals(
                jsonObjectUtils.getString("prashik"),
                jsonObject.getString("prashik")
        );
    }
}
