package org.dzmitry.kapachou.hibernate.web;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.model.Bunch;
import org.dzmitry.kapachou.hibernate.service.BunchService;
import org.dzmitry.kapachou.hibernate.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;

@AllArgsConstructor
@RestController
public class BunchRestController {

    private final BunchService bunchService;

    private final StudentService studentService;

    @PostConstruct
    private void setUp() {
        //
    }

    @GetMapping
    public Collection<Bunch> getGroups() {
        return bunchService.findAll();
    }


}
