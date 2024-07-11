package gift;

import gift.model.Gift;
import gift.model.User;
import gift.repository.GiftRepository;
import gift.repository.UserRepository;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
