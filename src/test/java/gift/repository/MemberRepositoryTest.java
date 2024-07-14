package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.user.entity.User;
import gift.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

  @Autowired
  private UserRepository memberRepository;

  @Test
  public void testFindByEmail() {
    User member = new User();
    member.setEmail("test@example.com");
    member.setPassword("password");
    memberRepository.save(member);

    Optional<User> foundMember = memberRepository.findByEmail("test@example.com");

    assertThat(foundMember).isPresent();
  }

  @Test
  public void testUpdateMember() {
    User member = new User();
    member.setEmail("update@example.com");
    member.setPassword("initialPassword");
    memberRepository.save(member);

    member.setPassword("updatedPassword");
    memberRepository.save(member);

    Optional<User> updatedMember = memberRepository.findByEmail("update@example.com");

    assertThat(updatedMember).isPresent();
    updatedMember.ifPresent(m -> assertThat(m.getPassword()).isEqualTo("updatedPassword"));
  }

  @Test
  public void testDeleteMember() {
    User member = new User();
    member.setEmail("delete@example.com");
    member.setPassword("password");
    memberRepository.save(member);

    memberRepository.deleteByEmail("delete@example.com");

    Optional<User> deletedMember = memberRepository.findByEmail("delete@example.com");

    assertThat(deletedMember).isNotPresent();
  }

  @Test
  public void testFindByNonExistingEmail() {
    Optional<User> foundMember = memberRepository.findByEmail("nonexistent@example.com");

    assertThat(foundMember).isNotPresent();
  }
}
