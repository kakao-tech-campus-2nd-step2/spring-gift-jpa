package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.user.entity.User;
import gift.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {

  @Autowired
  private UserRepository memberRepository;

  @BeforeEach
  public void setUp() {
    memberRepository.deleteAll();
  }

  private User createAndSaveUser(String email, String password) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    return memberRepository.save(user);
  }

  @Test
  public void testFindByEmail() {
    // given
    User member = createAndSaveUser("test@example.com", "password");

    // when
    Optional<User> foundMember = memberRepository.findByEmail("test@example.com");

    // then
    assertThat(foundMember).isPresent();
  }

  @Test
  public void testUpdateMember() {
    // given
    User member = createAndSaveUser("update@example.com", "initialPassword");

    // when
    member.setPassword("updatedPassword");
    memberRepository.save(member);
    Optional<User> updatedMember = memberRepository.findByEmail("update@example.com");

    // then
    assertThat(updatedMember).isPresent();
    updatedMember.ifPresent(m -> assertThat(m.getPassword()).isEqualTo("updatedPassword"));
  }

  @Test
  public void testDeleteMember() {
    // given
    User member = createAndSaveUser("delete@example.com", "password");

    // when
    memberRepository.deleteByEmail("delete@example.com");
    Optional<User> deletedMember = memberRepository.findByEmail("delete@example.com");

    // then
    assertThat(deletedMember).isNotPresent();
  }

  @Test
  public void testFindByNonExistingEmail() {
    // when
    Optional<User> foundMember = memberRepository.findByEmail("nonexistent@example.com");

    // then
    assertThat(foundMember).isNotPresent();
  }
}
