package gift.repository.token;

import gift.domain.Member;
import gift.domain.TokenAuth;
import gift.repository.member.MemberSpringDataJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TokenSpringDataJpaRepositoryTest {

    @Autowired
    private TokenSpringDataJpaRepository tokenRepository;

    @Autowired
    private MemberSpringDataJpaRepository memberRepository;

    @Test
    public void testSaveToken() {
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);

        TokenAuth token = new TokenAuth("test-token", member);
        tokenRepository.save(token);

        Optional<TokenAuth> foundToken = tokenRepository.findByToken("test-token");
        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getMember().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindToken() {
        Member member = new Member("test2@example.com", "password");
        memberRepository.save(member);

        TokenAuth token = new TokenAuth("test-token-2", member);
        tokenRepository.save(token);
        tokenRepository.save(token);

        Optional<TokenAuth> foundToken = tokenRepository.findByToken("test-token-2");
        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getMember().getEmail()).isEqualTo("test2@example.com");
    }

    @Test
    public void testDeleteToken() {
        Member member = new Member("test3@example.com", "password");
        memberRepository.save(member);

        TokenAuth token = new TokenAuth("test-token-3", member);
        tokenRepository.save(token);

        tokenRepository.delete(token);

        Optional<TokenAuth> foundToken = tokenRepository.findByToken("test-token-3");
        assertThat(foundToken).isNotPresent();
    }
}
