package gift.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gift.member.domain.Member;
import gift.member.domain.TokenDTO;
import gift.member.repository.MemberRepository;
import gift.member.service.MemberService;
import gift.member.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS members");
        jdbcTemplate.execute("CREATE TABLE members (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "email VARCHAR(255) NOT NULL, " +
            "password VARCHAR(255) NOT NULL)");
        jdbcTemplate.execute("TRUNCATE TABLE members");
        jdbcTemplate.execute(
            "INSERT INTO members (email, password) VALUES ('test@example.com', '1234')");
    }

    @Test
    @DisplayName("토큰 생성 확인")
    public void testGenerateToken() {
        //given
        Member member = new Member("test@example.com","1234");
        //when
        String token = jwtUtil.generateToken(member);
        //then
        assertNotNull(token);
    }

    @Test
    @DisplayName("토큰 인증 확인")
    public void testAuthenticate() {
        //given
        Member member = new Member("test@example.com","1234");
        TokenDTO expectedToken = new TokenDTO(jwtUtil.generateToken(member));
        //when
        TokenDTO actualToken = memberService.login(member);
        //then
        assertEquals(expectedToken, actualToken);
    }

}
