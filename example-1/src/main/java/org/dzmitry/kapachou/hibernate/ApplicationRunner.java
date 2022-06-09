package org.dzmitry.kapachou.hibernate;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dzmitry.kapachou.hibernate.service.TrainingService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@AllArgsConstructor
@SpringBootApplication(scanBasePackages = "org.dzmitry.kapachou.hibernate")
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

  private final TrainingService trainingService;

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(ApplicationRunner.class).bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @Override
  public void run(ApplicationArguments args) {
    log.info("---------");
    trainingService.findAll().forEach(System.out::println);
    log.info("---------");

  }
}
