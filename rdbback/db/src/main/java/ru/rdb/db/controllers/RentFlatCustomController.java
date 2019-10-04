package ru.rdb.db.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RentFlatCustomController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentFlatCustomController.class);

    @Autowired
    RentFlatRepositoryController flatRepository;


    @RequestMapping(path = "/custom/rentflat")
    public String getPage(Model model) {
        LOGGER.info("page load /custom/rentflat");
        model.addAttribute("list", flatRepository.findAll());
        return "FlatRentPage";
    }

    @DeleteMapping("/custom/rentflat/clear")
    @ResponseBody
    public void clear() {
        LOGGER.info("clear all rentflat");
        flatRepository.deleteAll();
    }

}
