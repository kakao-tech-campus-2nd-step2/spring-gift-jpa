package gift.user;

import gift.user.infrastructure.persistence.JpaUserAccountRepository;
import gift.user.infrastructure.persistence.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserAccountRepositoryTests {
    @Autowired
    private JpaUserAccountRepository jpaUserAccountRepository;

    @Test
    public void saveUserAccount() {
        UserAccountEntity userAccount = new UserAccountEntity(0L, "test", "test");

        userAccount = jpaUserAccountRepository.save(userAccount);

        assertThat(jpaUserAccountRepository.findById(userAccount.getUserId())).isPresent();
        assertThat(jpaUserAccountRepository.findById(userAccount.getUserId()).get()).isEqualTo(userAccount);
    }

    @Test
    public void findByEmail() {
        UserAccountEntity userAccount = new UserAccountEntity(0L, "test", "test");
        jpaUserAccountRepository.save(userAccount);

        assertThat(jpaUserAccountRepository.findByEmail("test")).isPresent();
    }

    @Test
    public void existsByEmail() {
        UserAccountEntity userAccount = new UserAccountEntity(0L, "test", "test");
        jpaUserAccountRepository.save(userAccount);

        assertThat(jpaUserAccountRepository.existsByEmail("test")).isTrue();
    }
}
