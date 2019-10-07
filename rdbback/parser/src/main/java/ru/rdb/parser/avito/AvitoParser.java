package ru.rdb.parser.avito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rdb.models.Consts;
import ru.rdb.models.DbId;
import ru.rdb.models.Link;
import ru.rdb.models.RawData;
import ru.rdb.parser.FileUtils;
import ru.rdb.parser.Selenium;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AvitoParser {

    private final Selenium selenium;
    private final Map<String, String> scriptCache;
    private final FileUtils fileUtils;

    @Autowired
    public AvitoParser(Selenium selenium) {
        this.selenium = selenium;
        this.scriptCache = new HashMap<>();
        this.fileUtils = new FileUtils();
    }

    public List<String> parseList(String url) {
        selenium.get(url);
        String js = fileUtils.readFile("jsparser/avito/parseList.js", scriptCache);
        if (url.equals(selenium.getDriver().getCurrentUrl())) {
            try {
                return (List<String>) selenium.execJs(js);
            } catch (Exception e) {
                throw new RuntimeException("fail parse url " + url, e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public List<Link> getAllLink(String startUrl) {
        String js = "return [...document.getElementsByClassName('pagination-page')].filter(e => e.innerText == 'Последняя')[0].href";
        selenium.get(startUrl);
        String lastUrl = (String) selenium.execJs(js);
        int lastLinkIndex = getLastPageIndex(lastUrl);
        String pStr = "p=" + lastLinkIndex;
        List<Link> list = IntStream.rangeClosed(1, lastLinkIndex)
                .mapToObj(i -> lastUrl.replace(pStr, "p=" + String.valueOf(i)))
                .map(url -> new Link(toId(url), url, null, null))
                .collect(Collectors.toList());
        list.add(0, new Link(toId(startUrl), startUrl, null, null));
        return list;
    }

    public RawData parsePage(String url) {
        try {
            selenium.get(url);
            selenium.execJs("document.getElementsByClassName('item-phone-button-sub-text')[0] && document.getElementsByClassName('item-phone-button-sub-text')[0].click()");
            Thread.sleep(1000);
            selenium.execJs("document.getElementsByClassName('close')[0].click()");
            String js = fileUtils.readFile("jsparser/avito/parseRentFlat.js", scriptCache);
            String json = (String) selenium.execJs(js);
            return new RawData(toId(url), json, null, url, null);
        } catch (Exception e) {
            throw new RuntimeException("fail parse url " + url, e);
        }
    }

    int getLastPageIndex(String lastUrl) {
        String arr[] = lastUrl.split("p=");
        if (arr.length == 1) {
            return 0;
        }
       return Integer.valueOf(arr[1].split("&")[0]);
    }

    DbId toId(String str) {
        String id = str.replaceAll("[^A-Za-z0-9]", "-");
        return new DbId(Consts.AVITO_GROUP, id);
    }

}
