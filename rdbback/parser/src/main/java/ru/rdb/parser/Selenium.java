package ru.rdb.parser;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

@Service
public class Selenium {

    private static final Logger LOGGER = LoggerFactory.getLogger(Selenium.class);

    private WebDriver driver;

    @Value("${parser.pathDriver}")
    String pathDriver;
    @Value("${parser.headless}")
    Boolean headless;
    @Value("${parser.proxyUrl:#{null}}")
    String proxyUrl;


    public void changeProxy(String proxyUrl) {
        close();
        this.proxyUrl = proxyUrl;
        start();
    }

    @PostConstruct
    public void start() {
        LOGGER.info("start selenium with param: [path: {}, headless: {}, proxy: {}]", pathDriver, headless, proxyUrl);
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

        if (StringUtils.isNotEmpty(proxyUrl)) {
            chromeOptions.addArguments("--proxy-server=" + proxyUrl);
        }
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

    public void saveScreen(String path) {
        LOGGER.info("save screen: {}", path);
        if (!(driver instanceof TakesScreenshot)) {
            throw new IllegalStateException("This driver does not support Screenshot");
        }
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            org.apache.commons.io.FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object execJs(String jsScript)  {
        LOGGER.debug("execute: " + jsScript);
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("This driver does not support JavaScript");
        }
        Object result = ((JavascriptExecutor)driver).executeScript(jsScript);
        LOGGER.debug(result != null ? result.toString() : "null");
        return result;
    }

    public WebDriver getDriver() {
        return driver;
    }



}
