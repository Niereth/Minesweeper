package minesweeper.json;

import java.lang.reflect.Type;
/**
public class JsonAdapter {
    private String directory = "records/";
    private String fileNameRecords = "records";

    private JsonProvider provider;

    public JsonAdapter() {
        provider = new JsonProvider();
    }

    public void saveJson(Object object, Type type) {
        provider.saveJson(object, type, directory, fileNameRecords);
    }

    public Object loadJson(Type type) {
        return provider.loadJson(type, directory, fileNameRecords);
    }
}
*/