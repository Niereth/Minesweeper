package minesweeper.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonProvider {

    private static final Logger log = LoggerFactory.getLogger(JsonProvider.class);
    private static final String DIRECTORY = "records/";
    private static final String FILE_NAME = "records";

    public void saveJson(Object object, Type type) {

        File file = loadFileFromResources();
        if (file == null) {
            return;
        }

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        try (
                OutputStream outputStream = new FileOutputStream(file);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"))
        ) {
            gson.toJson(object, type, bufferedWriter);
        } catch (IOException e) {
            log.warn("Не удалось записать рекорды.", e);
        }
    }

    public Object loadJson(Type type) {

        File file = loadFileFromResources();
        if (file == null) {
            return null;
        }

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        try (
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader streamReader = new InputStreamReader(inputStream, "UTF-8")
        ) {
            return gson.fromJson(streamReader, type);
        } catch (IOException e) {
            log.info("Не удалось загрузить файлы рекордов. Следующий шаг - попытка создать файлы с рекордами по умолчанию, затем загрузить их.", e);
        }
        return null;
    }

    private File loadFileFromResources() {
        String relativePath = DIRECTORY + FILE_NAME;
        URL absolutePath = JsonProvider.class.getClassLoader().getResource(relativePath);
        if (absolutePath == null) {
            log.error("Заданной директории не существует");
            return null;
        }

        URI uri;
        try {
            uri = absolutePath.toURI();
        } catch (URISyntaxException e) {
            log.error("Ошибка при создании URI",e);
            return null;
        }

        return new File(uri);
    }
}