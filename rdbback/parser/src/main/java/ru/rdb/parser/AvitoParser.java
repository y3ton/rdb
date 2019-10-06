package ru.rdb.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rdb.models.DbId;
import ru.rdb.models.RawData;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AvitoParser {

    public static final String AVITO_BASE_URL = "https://www.avito.ru/moskva_i_mo/kvartiry/sdam/na_dlitelnyy_srok?cd=1&pmax=50000&pmin=0&user=1&s=104";
    public static final String AVITO_GROUP = "avito";

    private final Selenium selenium;
    private final Map<String, String> scriptCache;
    private final FileUtils fileUtils;

    @Autowired
    public AvitoParser(Selenium selenium) {
        this.selenium = selenium;
        this.scriptCache = new HashMap<>();
        this.fileUtils = new FileUtils();
    }


    public List<RawData> process(String linkUrl) {
        List<String> urls = parseList(linkUrl);
        return urls.stream().limit(25).map(this::parsePage).collect(Collectors.toList());
    }

    public List<String> parseList(String url) {
        selenium.get(url);
        String js = fileUtils.readFile("jsparser/avito/parseList.js", scriptCache);
        if (url.equals(selenium.getDriver().getCurrentUrl())) {
            return (List<String>) selenium.execJs(js);
        } else {
            return new ArrayList<>();
        }
    }

    public RawData parsePage(String url) {
        selenium.get(url);
        selenium.execJs("document.getElementsByClassName('item-phone-button-sub-text')[0].click()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        selenium.execJs("document.getElementsByClassName('close')[0].click()");
        String js = fileUtils.readFile("jsparser/avito/parseRentFlat.js", scriptCache);
        String json = (String) selenium.execJs(js);
        return new RawData(new DbId("avito", url), json, null, url, null);
    }

}
