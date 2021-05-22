package com.bogdan.inc.contoller;

import com.bogdan.inc.model.Celebrity;
import com.bogdan.inc.service.CelebrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/name")
public class CelebrityController {

    @Autowired
    private CelebrityService celebrityService;

    @GetMapping
    public List<Celebrity> findByName(@RequestParam("name") String name) {
        return celebrityService.findByName(name);
    }

    @GetMapping("/{id}")
    public Celebrity findById(@PathVariable String id) {
        return celebrityService.findById(id);
    }
}
