package org.dzmitry.kapachou.hibernate.web;

import lombok.AllArgsConstructor;
import org.dzmitry.kapachou.hibernate.model.Training;
import org.dzmitry.kapachou.hibernate.service.SessionService;
import org.dzmitry.kapachou.hibernate.service.TrainingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Collection;

@AllArgsConstructor
@RestController
public class TrainingRestController {

    private final SessionService sessionService;

    private final TrainingService trainingService;

    @PostConstruct
    private void setUp() {
        //
    }

    @GetMapping("/trainings")
    public Collection<Training> getTrainings() {
        return trainingService.findAll();
    }

}
