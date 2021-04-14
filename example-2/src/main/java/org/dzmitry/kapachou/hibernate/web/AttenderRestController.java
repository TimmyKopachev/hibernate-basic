package org.dzmitry.kapachou.hibernate.web;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.model.Attender;
import org.dzmitry.kapachou.hibernate.model.AttenderTask;
import org.dzmitry.kapachou.hibernate.service.AttenderService;
import org.dzmitry.kapachou.hibernate.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;

@AllArgsConstructor
@RestController
public class AttenderRestController {

    private final AttenderService attenderService;

    private final TaskService taskService;

    @PostConstruct
    private void setUp() {
        //
    }

    @GetMapping
    public Collection<Attender> getAttenders() {
        return attenderService.findAll();
    }

    @GetMapping("/{attenderId}")
    public Collection<AttenderTask> getAttenders(@PathVariable Long attenderId, @RequestParam Long taskId) {
        return attenderService.findAllByPK(attenderId, taskId);
    }


}
