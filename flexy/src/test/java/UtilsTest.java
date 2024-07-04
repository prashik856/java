import com.prashik.flexy.utils.Utils;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
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

    /**
     * Function to test Utils.checkDirectory method.
     */
    @Test
    public void testCheckDirectory() throws NoSuchFileException {
        String currentDirectory = System.getProperty("user.dir") + "/gradle/wrapper";
        Path filePath = Path.of(currentDirectory);
        Path filePathUtils = Utils.checkDirectory(currentDirectory, "test");
        Assertions.assertEquals(filePath.toString(), filePathUtils.toString());
    }


}
