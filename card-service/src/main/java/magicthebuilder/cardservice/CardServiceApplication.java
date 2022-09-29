package magicthebuilder.cardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableMongoRepositories(basePackageClasses = CardRepository.class)
public class CardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(CardRepository repository) {
//        return args -> {
//
//        };
//    }
}
