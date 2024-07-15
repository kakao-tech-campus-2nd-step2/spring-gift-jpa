package gift.repository.member;

import gift.domain.Member;
import gift.dto.request.MemberRequest;
import gift.exception.DuplicateMemberException;
import gift.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MemberService.class)  // MemberService를 컨텍스트에 추가
public class MemberSpringDataJpaRepositoryTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberSpringDataJpaRepository memberRepository;

    @BeforeEach
    public void setUp() {
        Member member = new Member("test@example.com", "password");
        memberRepository.save(member);
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    public void testSaveMember() {
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByEmail() {
        Optional<Member> foundMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testSaveMember_이메일_중복() {
        MemberRequest memberRequest = new MemberRequest("test@example.com", "password");

        assertThatThrownBy(() -> memberService.register(memberRequest))
                .isInstanceOf(DuplicateMemberException.class)
                .hasMessage("이미 등록된 이메일입니다.");
    }
}
