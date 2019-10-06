package ru.rdb.db.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.rdb.db.repositories.RawDataRepository;
import ru.rdb.models.RawData;
import ru.rdb.models.RentFlat;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class RentFlatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentFlatController.class);

    private final RawDataRepository rawDataRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RentFlatController(RawDataRepository rawDataRepository) {
        this.rawDataRepository = rawDataRepository;
    }

    @RequestMapping(path = "/avitoflat")
    public String getPage(Model model) {
        LOGGER.info("page load /custom/rentflat");
        List<RentFlat> list = rawDataRepository.findByGroup("avito").stream()
                .map(this::fromRawData)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        model.addAttribute("list", list);
        return "FlatRentPage";
    }

    RentFlat fromRawData(RawData data) {
        try {
            return objectMapper.readValue(data.getJson(), RentFlat.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
