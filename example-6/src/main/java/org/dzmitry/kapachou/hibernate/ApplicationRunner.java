package org.dzmitry.kapachou.hibernate;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dzmitry.kapachou.hibernate.model.Comment_;
import org.dzmitry.kapachou.hibernate.model.Feedback;
import org.dzmitry.kapachou.hibernate.model.Feedback_;
import org.dzmitry.kapachou.hibernate.service.FeedbackService;
import org.dzmitry.kapachou.hibernate.service.ProjectService;
import org.dzmitry.kapachou.hibernate.service.graph.EntityGraphBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@AllArgsConstructor
@Slf4j
@SpringBootApplication(scanBasePackages = "org/dzmitry/kapachou/hibernate")
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    private final ProjectService projectService;
    private final FeedbackService feedbackService;

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ApplicationRunner.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("-----result------");
//        System.out.println(projectService.getProject(10L,
//                new EntityGraphBuilder<>(projectService.getEntityManager(), Project.class)
//                        .fetch(Project_.stages,
//                                s -> s.fetch(Stage_.brigades))));
//        System.out.println("-----------------");
//        System.out.println(projectService.getProject(10L,
//                new EntityGraphBuilder<>(projectService.getEntityManager(), Project.class)
//                        .fetch(Project_.owner)
//                        .fetch(Project_.stages,
//                                s -> s.fetch(Stage_.brigades,
//                                        b -> b.fetch(Brigade_.employees)
//                                                .fetch(Brigade_.tasks))
//                        )));

        System.out.println(feedbackService.getFeedback(1001L,
                new EntityGraphBuilder<>(feedbackService.getEntityManager(), Feedback.class)
                        .fetch(Feedback_.comments, c -> c.fetch(Comment_.author))));
        System.out.println("-----------------");
    }
}
