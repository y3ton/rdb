package ru.rdb.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rdb.models.DbId;
import ru.rdb.models.Link;
import ru.rdb.models.RawData;
import ru.rdb.parser.Repositories.LinkRepository;
import ru.rdb.parser.Repositories.RawRepository;

import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static Logger LOGER = LoggerFactory.getLogger(App.class);

    @Autowired
    AvitoParser avitoParser;
    @Autowired
    RawRepository rawRepository;
    @Autowired
    LinkRepository linkRepository;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {

        linkRepository.add(new Link(
                new DbId(AvitoParser.AVITO_GROUP, "init"),
                AvitoParser.AVITO_BASE_URL,
                null,
                null));

        Link link = linkRepository.getRandom(AvitoParser.AVITO_GROUP);
        LOGER.info("get url {}", link.getUrl());

        List<RawData> list = avitoParser.process(link.getUrl());
        if (list.size() == 0) {
            throw new RuntimeException("flat not found");
        }
        LOGER.info("find {} flat", list.size());
        list.forEach(rawRepository::add);
        LOGER.info("flat was seved", list.size());

        linkRepository.delete(link.getId());
    }






}
