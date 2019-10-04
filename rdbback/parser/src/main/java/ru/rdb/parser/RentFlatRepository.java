package ru.rdb.parser;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.rdb.models.ListDto;
import ru.rdb.models.RentFlat;

import java.util.List;

@Repository
public class RentFlatRepository {

    private final String url;
    private final String customUrl;

    public RentFlatRepository(@Value("${parser.baseUrl}") String baseUrl) {
        url = baseUrl + "/rentflat";
        customUrl = baseUrl + "/custom/rentflat";
    }

    public void clear() {
        new RestTemplate().delete(customUrl + "/clear");
    }

    public RentFlat add(RentFlat rentFlat) {
        HttpEntity<RentFlat> requestBody = new HttpEntity<>(rentFlat);
        return new RestTemplate().postForObject(url, requestBody, RentFlat.class);
    }

    public RentFlat get(String id) {
        throw new RuntimeException("Not implemented");
    }

    public List<RentFlat> get() {
        return new RestTemplate().exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ListDto<RentFlat>>() {})
            .getBody().getContent();
    }



}
