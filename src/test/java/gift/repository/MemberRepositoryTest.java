package gift.repository;

import gift.vo.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;
    Member member;

    @BeforeEach
    void setUp() {
        member = new Member("kookies@google.com", "@123");
        repository.save(member); // Member pre-save
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll(); // Clean up after each test
    }

    @Test
    @DisplayName("Member save")
    void save() {
        // when
        Member actual = repository.save(new Member("newMember@google.com", "@kakaoTech"));

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo("newMember@google.com")
        );
    }

    @Test
    @DisplayName("Find Member by email")
    void findByEmail() {
        // given
        String expected = "kookies@google.com";

        // when
        repository.save(member);
        Optional<Member> foundMember = repository.findByEmail(expected);

        // then
        assertTrue(foundMember.isPresent());
        assertThat(foundMember.get().getEmail()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Update Member password")
    void updateMember() {
        // given
        String newEmail = "updated@google.com";
        member.setEmail(newEmail);

        // when
        Member updatedMember = repository.save(member);

        // then
        assertAll(
                () -> assertThat(updatedMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(updatedMember.getPassword()).isEqualTo(member.getPassword())
        );
    }

    @Test
    @DisplayName("Delete Member")
    void deleteMember() {
        // when
        repository.delete(member);
        boolean exists = repository.existsById(member.getId());

        // then
        assertThat(exists).isFalse();
    }

}