package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@JdbcTest
class MemberDaoTest {

    private @Autowired JdbcClient jdbcClient;
    private MemberDao memberDao;

    @BeforeEach
    void beforeEach() {
        memberDao = new MemberDao(jdbcClient);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void register() {
        Member member = new Member("sgoh", "sgohpass");
        int result = memberDao.register(member);
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("email 로 가져오기 테스트")
    void findByEmail() {
        Member member1 = new Member("sgoh", "sgohpass");
        memberDao.register(member1);
        Member member2 = memberDao.findByEmail("sgoh").get();
        assertThat(member2).isNotNull();
    }
}