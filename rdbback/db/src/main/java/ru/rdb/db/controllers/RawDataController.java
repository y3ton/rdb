package ru.rdb.db.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rdb.db.repositories.RawDataRepository;
import ru.rdb.models.DbId;
import ru.rdb.models.RawData;

@RestController
@RequestMapping("/data")
public class RawDataController {

    private final RawDataRepository rawDataRepository;

    @Autowired
    public RawDataController(RawDataRepository rawDataRepository) {
        this.rawDataRepository = rawDataRepository;
    }

    @PutMapping()
    public @ResponseBody RawData save(@RequestBody RawData link) {
        link.setCreateDate(System.currentTimeMillis());
        return rawDataRepository.save(link);
    }

    @DeleteMapping("/{group}/{id}")
    @ResponseBody
    public void delete(@PathVariable("group") String group, @PathVariable("id") String id) {
        rawDataRepository.deleteById(new DbId(group, id));
    }

    @GetMapping
    @ResponseBody
    public Iterable<RawData> findAll() {
        return rawDataRepository.findAll();
    }


}
