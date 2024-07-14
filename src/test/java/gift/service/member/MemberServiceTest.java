package gift.service.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.member.Member;
import gift.domain.member.MemberRepository;
import gift.web.dto.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(new Member("wjdghtjd06@naver.com", "1234"));
        memberRepository.save(new Member("ghtlr0506@naver.com", "5678"));
    }

    @Test
    void update() {
        Member member = memberRepository.findByEmail("wjdghtjd06@naver.com").get();
        member.updateMember("ghtlr0607@naver.com", "5678");
        Member member2 = memberRepository.findByEmail("ghtlr0607@naver.com").get();
        assertThat(member2).isNotNull();
    }
}