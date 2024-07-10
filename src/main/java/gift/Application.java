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


    //초기 더미데이터 추가
    @Bean
    public ApplicationRunner loadData(GiftRepository giftRepository, UserRepository userRepository) {
        return (args) -> {
            giftRepository.save(new Gift("Gift1", 1000, "http://example.com/image1.jpg"));
            giftRepository.save(new Gift("Gift2", 2000, "http://example.com/image2.jpg"));

            userRepository.save(new User("user@example.com", "password123"));
        };
    }
}
