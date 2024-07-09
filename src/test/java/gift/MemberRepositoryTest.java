package gift;

import gift.Model.Product;
import gift.Model.Role;
import gift.Model.UserInfo;
import gift.Repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class UserInfoRepositoryTest {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void save(){
        UserInfo expected = new UserInfo("admin","1234", Role.ADMIN);
        UserInfo actual = userInfoRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail())
        );
    }

    @Test
    void findByName() {
        String expectedEmail = "admin";
        String expectedPassword = "1234";
        Role expectedRole = Role.ADMIN;
        userInfoRepository.save(new UserInfo(expectedEmail, expectedPassword, expectedRole));
        String actual = userInfoRepository.findByEmail(expectedEmail).getEmail();
        assertThat(actual).isEqualTo(expectedEmail);
    }
}
