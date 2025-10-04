package org.dzmitry.kapachou.hibernate;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dzmitry.kapachou.hibernate.model.Client;
import org.dzmitry.kapachou.hibernate.model.Project;
import org.dzmitry.kapachou.hibernate.service.ClientRepository;
import org.dzmitry.kapachou.hibernate.service.ProjectFeedRepository;
import org.dzmitry.kapachou.hibernate.service.FeedSearcherService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.List;

@AllArgsConstructor
@Slf4j
@SpringBootApplication(scanBasePackages = "org/dzmitry/kapachou/hibernate")
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {
    final GeometryFactory geometryFactory;
    final ClientRepository clientRepository;
    final FeedSearcherService feedSearcherService;
    final ProjectFeedRepository projectRepository;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ApplicationRunner.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) {

        System.out.println("--result:--");
        //System.out.println(feedSearcherService.buildFeedForClient(1L));
        feedSearcherService.lookupRelevantProjects(1L)
                        .forEach(p -> System.out.printf("project:<%s>%n", p));
        System.out.println("----------");
    }
}
