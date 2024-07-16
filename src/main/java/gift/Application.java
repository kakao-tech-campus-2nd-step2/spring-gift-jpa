package gift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "gift.repository")
@EntityScan(basePackages = "gift.entity")
@ComponentScan(basePackages = "gift")
@Configuration
class JpaConfiguration {}

@SpringBootApplication(scanBasePackages = "gift")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

