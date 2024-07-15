package gift.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testMemberCreation() {
        Member member = new Member("test@example.com", "password123");
        assertNotNull(member);
        assertEquals("test@example.com", member.getEmail());
        assertEquals("password123", member.getPassword());
    }

    @Test
    void testMemberEquality() {
        Member member1 = new Member("test@example.com", "password123");
        Member member2 = new Member("test@example.com", "password123");

        assertNotEquals(member1, member2);
    }
}
