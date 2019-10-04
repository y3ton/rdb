package ru.rdb.parser;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileUtils {

    public String getResourceFile(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return (new File(classLoader.getResource(name).getFile())).getAbsolutePath();
    }

    public String readResourceFile(String name) {
        return readFile(getResourceFile(name));
    }

    public String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readFile(String fileName, Map<String, String> cache ) {
        String text = cache.get(fileName);
        if (StringUtils.isBlank(text)) {
            text = readResourceFile( fileName);
            if (StringUtils.isBlank(text)) {
                throw new RuntimeException("file " + fileName + " is blank");
            }
            cache.put(fileName, text);
        }
        return text;
    }


}
