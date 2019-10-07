package ru.rdb.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rdb.parser.avito.AvitoProcessor;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    AvitoProcessor avitoProcessor;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            avitoProcessor.createAvitoLink(args[0]);
        }
        avitoProcessor.processAvitoLink();
    }






}
