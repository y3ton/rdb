package ru.rdb.parser.Repositories;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.rdb.models.RawData;

@Repository
public class RawRepository {

    private final String url;

    public RawRepository(@Value("${parser.baseUrl}") String baseUrl) {
        url = baseUrl + "/data";
    }

    public void add(RawData rawData) {
        HttpEntity<RawData> requestBody = new HttpEntity<>(rawData);
        new RestTemplate().put(url, requestBody, RawData.class);
    }

}