package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.dto.MemberRequest;
import gift.entity.MemberEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Test
    @DisplayName("findByEmail 테스트")
    void findByEmail() {
        // given
        MemberRequest request = new MemberRequest("test@google.co.kr", "password");
        MemberEntity expected = memberRepository.save(request.toMemberEntity());

        // when
        MemberEntity actual = memberRepository.findByEmail(request.getEmail()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("findById 테스트")
    void findById(){
        // given
        MemberRequest request = new MemberRequest("test@google.co.kr", "password");
        MemberEntity expected = memberRepository.save(request.toMemberEntity());

        // when
        MemberEntity actual = memberRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void save(){
        // given
        MemberRequest request = new MemberRequest("test@google.co.kr", "password");
        MemberEntity expected = request.toMemberEntity();

        // when
        MemberEntity actual = memberRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
            () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void delete(){
        // given
        MemberRequest request = new MemberRequest("test@google.co.kr", "password");
        MemberEntity savedMember = memberRepository.save(request.toMemberEntity());

        // when
        memberRepository.delete(savedMember);

        // then
        assertTrue(memberRepository.findById(savedMember.getId()).isEmpty());
    }
}