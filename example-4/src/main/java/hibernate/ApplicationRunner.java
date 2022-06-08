package hibernate;


import hibernate.service.UnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@AllArgsConstructor
@SpringBootApplication(scanBasePackages = "org/dzmitry/kapachou/hibernate")
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

  final UnitRepository unitRepository;

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(ApplicationRunner.class).bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("-------RESULT:-------");
    unitRepository.findAll().forEach(System.out::println);
    System.out.println("--------------");
  }
}
