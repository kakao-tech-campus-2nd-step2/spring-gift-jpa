//package gift;
//
//import gift.member.model.Member;
//import gift.member.repository.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class MemberRepositoryTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void saveTest() {
//        // Given
//        Member member = new Member();
//
//        // When
//        member = memberRepository.save(member);
//
//        // Then
//        assertThat(memberRepository.findById(member.id())).isPresent();
//    }
//
//    @Test
//    public void deleteTest() {
//        // Given
//        Member member = new Member();
//        member = memberRepository.save(member);
//
//        // When
//        memberRepository.deleteById(member.id());
//
//        // Then
//        assertThat(memberRepository.findById(member.id())).isEmpty();
//    }
//
//    @Test
//    public void findByIdTest() {
//        // Given
//        Member member = new Member();
//        member = memberRepository.save(member);
//
//        // When
//        Member foundUser = memberRepository.findById(member.id()).orElse(null);
//
//        // Then
//        assertThat(foundUser.id()).isEqualTo(member.id());
//        // assertThat(foundUser.name()).isEqualTo(member.name());
//    }
//}