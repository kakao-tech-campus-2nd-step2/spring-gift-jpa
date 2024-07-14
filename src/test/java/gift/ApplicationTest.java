package gift;

import gift.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void contextLoads() {
        // MemberRepository가 주입되었는지 확인
        assertThat(memberRepository).isNotNull();
    }
}
