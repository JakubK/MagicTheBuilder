package magicthebuilder.cardservice;

import magicthebuilder.cardservice.repository.FileRepository;
import magicthebuilder.cardservice.repository.InMemoryRepository;
import magicthebuilder.cardservice.repository.RestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardServiceApplication {

    public static void main(String[] args) {
		SpringApplication.run(CardServiceApplication.class, args);
    }
}
