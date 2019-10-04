package ru.rdb.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.rdb.models.RentFlat;

import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static Logger LOGER = LoggerFactory.getLogger(App.class);

    @Autowired
    AvitoParser avitoParser;
    @Autowired
    RentFlatRepository rentFlatRepository;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        List<RentFlat> list = avitoParser.process();
        LOGER.info("find {} flat", list.size());
        rentFlatRepository.clear();
        list.forEach(rentFlatRepository::add);
        LOGER.info("flat was seved", list.size());
    }






}
