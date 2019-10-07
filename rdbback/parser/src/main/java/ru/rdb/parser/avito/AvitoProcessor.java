package ru.rdb.parser.avito;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rdb.models.Consts;
import ru.rdb.models.Link;
import ru.rdb.parser.Repositories.LinkRepository;
import ru.rdb.parser.Repositories.RawRepository;
import ru.rdb.parser.Selenium;

import java.util.List;

@Service
public class AvitoProcessor {

    private static Logger LOGER = LoggerFactory.getLogger(AvitoProcessor.class);

    public static final int EXCEPTION_TIMEOUT = 2 * 60 * 1000;
    public static final int ATTEMPTS_PARSE_COUNT = 3;

    private final AvitoParser avitoParser;
    private final RawRepository rawRepository;
    private final LinkRepository linkRepository;
    private final Selenium selenium;

    @Autowired
    public AvitoProcessor(AvitoParser avitoParser, RawRepository rawRepository, LinkRepository linkRepository, Selenium selenium) {
        this.avitoParser = avitoParser;
        this.rawRepository = rawRepository;
        this.linkRepository = linkRepository;
        this.selenium = selenium;
    }

    public void createAvitoLink(String startLink) {
        if (StringUtils.isNotEmpty(startLink)) {
            List<Link> links = avitoParser.getAllLink(startLink);
            links.forEach(linkRepository::add);
        }
    }

    public void processAvitoLink() {
        Link link;
        while ((link = linkRepository.getRandom(Consts.AVITO_GROUP)) != null) {
            LOGER.info("get url {}", link.getUrl());
            for (int i = 1; i <= ATTEMPTS_PARSE_COUNT; i++) {
                try {
                    avitoParser.parseList(link.getUrl())
                            .stream()
                            .map(avitoParser::parsePage)
                            .forEach(rawRepository::add);
                    break;
                } catch (Exception e) {
                    LOGER.error("fail load url {}", link.getUrl(), e);
                    LOGER.info("wait {} ...", EXCEPTION_TIMEOUT * i);
                    try {
                        Thread.sleep(EXCEPTION_TIMEOUT * i);
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                    selenium.restart(null);
                }
            }
            linkRepository.delete(link.getId());
        }
    }



}
