package gift.util;


import gift.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class UserValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void save_emailSuccess() {
        //given
        User user = new User("test@naver.com", "abc");

        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        //then
        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    public void save_emailFailure() {
        //given
        User user = new User("test@123@naver,com", "abc");

        //when
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        //then
        Assertions.assertThat(violations).isNotEmpty();
    }
}
