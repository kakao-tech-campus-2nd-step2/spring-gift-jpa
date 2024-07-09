package gift.Model;

import org.springframework.context.annotation.Bean;

public record UserInfo(String email, String password, Role role) {
}