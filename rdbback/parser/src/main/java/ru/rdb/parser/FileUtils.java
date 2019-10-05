package ru.rdb.parser;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileUtils {

    public String readResourceFile(String path) {
        try {
            return IOUtils.toString(new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8.name());
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
