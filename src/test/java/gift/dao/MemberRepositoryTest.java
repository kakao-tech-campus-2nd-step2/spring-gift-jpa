package gift.dao;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.dao.MemberDao;
import gift.product.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberDao memberDao;

    @Test
    void testSignUp() {
        Member member = new Member("member@email.com", "1234");
        Member registerMember = memberDao.save(member);
        assertThat(registerMember.getId()).isNotNull();
        assertThat(registerMember.getEmail()).isEqualTo("member@email.com");
        assertThat(registerMember.getPassword()).isEqualTo("1234");
    }
}
