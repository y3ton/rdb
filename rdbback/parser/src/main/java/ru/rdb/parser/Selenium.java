package ru.rdb.parser;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;

@Service
public class Selenium{

    private static final Logger LOGGER = LoggerFactory.getLogger(Selenium.class);

    private WebDriver driver;

    final private String pathDriver;
    final private Boolean headless;

    public Selenium(@Value("${parser.pathDriver}")String pathDriver, @Value("${parser.headless}") Boolean headless) {
        this.pathDriver = pathDriver;
        this.headless = headless;
    }

    @PostConstruct
    public void start() {
        LOGGER.info("start selenium with param: [path: {}, headless: {}]", pathDriver, headless);
        System.setProperty("webdriver.chrome.driver", pathDriver);
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) {
            chromeOptions.setHeadless(true);
        }
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(chromeOptions);
    }

    @PreDestroy
    public void close() {
        LOGGER.info("close selenium");
        try {
            driver.close();
        } catch (Exception e) {
            LOGGER.error("fail close selenium", e);
        }
    }

    public void get(String url) {
        LOGGER.info("get url: " + url);
        // todo try catch and timeout
        driver.get(url);
    }

    public Object execJs(String jsScript)  {
        LOGGER.debug("execute: " + jsScript);
        JavascriptExecutor javascriptExecutor;
        if (driver instanceof JavascriptExecutor) {
            javascriptExecutor = (JavascriptExecutor)driver;
        } else {
            throw new IllegalStateException("This driver does not support JavaScript");
        }
        return javascriptExecutor.executeScript(jsScript);
    }

    public WebDriver getDriver() {
        return driver;
    }



}
