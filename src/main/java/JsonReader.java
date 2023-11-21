import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/*
CLass to read json data from TestData.json file and convert it into a list of hashmaps, each hashmap representing one table row
 */
public class JsonReader {
    List<HashMap<String, String>> entries;
    String jsonString;
    public JsonReader() throws IOException {
        jsonString = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/main/resources/TestData.json"));
        ObjectMapper mapper = new ObjectMapper();
        entries = mapper.readValue(jsonString, new TypeReference<List<HashMap<String, String>>>() {
        });
    }
}
