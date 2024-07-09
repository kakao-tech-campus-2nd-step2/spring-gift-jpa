package gift.repository.member;

import gift.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("저장 테스트")
    void 저장_테스트(){
        //given
        Member member = new Member.Builder()
                .email("test@pusan.ac.kr")
                .password("abc")
                .build();
        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertAll(
                () -> assertThat(savedMember.getId()).isNotNull(),
                () -> assertThat(savedMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(savedMember.getPassword()).isEqualTo(member.getPassword())
        );
    }

    @Test
    @DisplayName("단건 조회")
    void 단건_조회_테스트(){
        //given
        Member member = new Member.Builder()
                .email("test@pusan.ac.kr")
                .password("abc")
                .build();

        Member savedMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        //then
        assertAll(
                () -> assertThat(findMember.getId()).isNotNull(),
                () -> assertThat(findMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(findMember.getPassword()).isEqualTo(member.getPassword())
        );
    }

    @Test
    @DisplayName("전체 조회")
    void 전체_조회_테스트(){
        //given
        Member member1 = new Member.Builder()
                .email("test1@pusan.ac.kr")
                .password("abc")
                .build();

        Member member2 = new Member.Builder()
                .email("test2@pusan.ac.kr")
                .password("abc")
                .build();

        Member member3 = new Member.Builder()
                .email("test3@pusan.ac.kr")
                .password("abc")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("이메일 조회")
    void 이메일_조회_테스트(){
        //given
        Member member1 = new Member.Builder()
                .email("test1@pusan.ac.kr")
                .password("abc")
                .build();

        Member member2 = new Member.Builder()
                .email("test2@pusan.ac.kr")
                .password("abc")
                .build();

        Member savedMember = memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findMemberByEmail(member1.getEmail()).get();

        //then
        assertAll(
                () -> assertThat(findMember.getId()).isEqualTo(savedMember.getId()),
                () -> assertThat(findMember.getEmail()).isEqualTo(savedMember.getEmail()),
                () -> assertThat(findMember.getPassword()).isEqualTo(savedMember.getPassword())
        );
    }

    @Test
    @DisplayName("이메일 패스워드 조회")
    void 이메일_패스워드_조회_테스트(){
        //given
        Member member1 = new Member.Builder()
                .email("test1@pusan.ac.kr")
                .password("abc")
                .build();

        Member member2 = new Member.Builder()
                .email("test2@pusan.ac.kr")
                .password("abc")
                .build();

        Member savedMember = memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findMemberByEmailAndPassword(member1.getEmail(), member1.getPassword()).get();

        //then
        assertAll(
                () -> assertThat(findMember.getId()).isEqualTo(savedMember.getId()),
                () -> assertThat(findMember.getEmail()).isEqualTo(savedMember.getEmail()),
                () -> assertThat(findMember.getPassword()).isEqualTo(savedMember.getPassword())
        );
    }

}