package ru.rdb.db.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.rdb.db.repositories.LinkRepository;
import ru.rdb.models.DbId;
import ru.rdb.models.Link;

@RestController
@RequestMapping("/links")
public class LinkController {

    public static long LINK_HOLD_TIMEOUT = 5 * 60 * 1000L;


    private final LinkRepository linkRepositories;

    @Autowired
    public LinkController(LinkRepository linkRepositories) {
        this.linkRepositories = linkRepositories;
    }

    @PostMapping("/{group}/random")
    @ResponseBody
    @Transactional
    public Link getRandom(@PathVariable("group") String group) {
        Link link = linkRepositories.getRandom(group, System.currentTimeMillis() - LINK_HOLD_TIMEOUT);
        if (link != null) {
            link.setReadDate(System.currentTimeMillis());
        }
        return link;
    }

    @PutMapping
    public @ResponseBody Link save(@RequestBody Link link) {
        link.setCreateDate(System.currentTimeMillis());
        return linkRepositories.save(link);
    }

    @DeleteMapping("/{group}/{id}")
    @ResponseBody
    public void delete(@PathVariable("group") String group, @PathVariable("id") String id) {
        linkRepositories.deleteById(new DbId(group, id));
    }

    @GetMapping
    @ResponseBody
    public Iterable<Link> findAll() {
        return linkRepositories.findAll();
    }
}
