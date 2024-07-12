package gift.repository;

import gift.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    void findByEmail() {
        String email = "test@gmail.com";
        Member expected = entityManager.persist(new Member(email, "password"));
        Member actual = memberRepository.findByEmail(email).orElse(null);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void saveDuplicateEmail() {
        String email = "test@gmail.com";
        entityManager.persist(new Member(email, "password"));

        Assertions.assertThatThrownBy(() -> {
            memberRepository.save(new Member(email, "password"));
        }).isInstanceOf(DataIntegrityViolationException.class);
    }
}
