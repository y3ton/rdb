package ru.rdb.parser.Repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.rdb.models.DbId;
import ru.rdb.models.Link;

@Repository
public class LinkRepository {

    private final String url;

    public LinkRepository(@Value("${parser.baseUrl}") String baseUrl) {
        url = baseUrl + "/links";
    }

    public Link add(Link link) {
        HttpEntity<Link> requestBody = new HttpEntity<>(link);
        return new RestTemplate().exchange(url, HttpMethod.PUT, requestBody, Link.class).getBody();
    }

    public Link getRandom(String group) {
        ResponseEntity<Link> response = (new RestTemplate())
                .postForEntity(url + "/" + group + "/random", new HttpEntity<>(""), Link.class);
        return response.getBody();
    }

    public void delete(DbId id) {
        new RestTemplate().delete(url + "/" + id.getGroup() + "/" + id.getId());
    }

}
