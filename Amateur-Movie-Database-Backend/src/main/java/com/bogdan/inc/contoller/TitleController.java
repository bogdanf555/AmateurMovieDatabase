package com.bogdan.inc.contoller;

import com.bogdan.inc.model.Title;
import com.bogdan.inc.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/title")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @GetMapping
    public List<Title> findByName(@RequestParam("title") String title) {
        return titleService.findByName(title);
    }

    @GetMapping("/{id}")
    public Title findById(@PathVariable String id) {
        return titleService.findById(id);
    }
}
