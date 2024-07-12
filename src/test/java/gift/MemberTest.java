package gift;

import gift.entity.Member;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;

    @BeforeEach
    public void setUp() {
        testMember = new Member(1, "testEmail", "testPassword", "testToken");
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    void testFindById() {
        memberRepository.save(testMember);
        Member findMember = memberRepository.findById(1);
        assertEquals(testMember, findMember);
    }

    @Test
    void testAddProduct() {
        Member savedMember = memberRepository.save(testMember);
        assertNotNull(savedMember);
        assertEquals(testMember, savedMember);
    }

    @Test
    void testUpdateProduct() {
        memberRepository.save(testMember);
        var updateMember = new Member(1, "updateEmail", "updatePassword", "updateToken");
        Member updatedMember = memberRepository.save(updateMember);
        assertNotNull(updatedMember);
        assertEquals(updateMember, updatedMember);
    }
}
