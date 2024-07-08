package gift.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import gift.model.Member;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원 저장 및 ID로 조회")
    public void testSaveAndFindById() {
        Member member = new Member(null, "test@example.com", "password");
        Member savedMember = new Member(1L, "test@example.com", "password");

        when(jdbcTemplate.update(anyString(), anyString(), anyString())).thenReturn(1);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong())).thenReturn(List.of(savedMember));

        memberRepository.create(member);
        Optional<Member> foundMember = memberRepository.findById(1L);

        assertTrue(foundMember.isPresent());
        assertEquals("test@example.com", foundMember.get().getEmail());
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void testFindAll() {
        Member member1 = new Member(1L, "user1@example.com", "password1");
        Member member2 = new Member(2L, "user2@example.com", "password2");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(Arrays.asList(member1, member2));

        List<Member> members = memberRepository.findAll();
        assertEquals(2, members.size());
    }

    @Test
    @DisplayName("회원 삭제")
    public void testDelete() {
        Member member = new Member(1L, "test@example.com", "password");

        when(jdbcTemplate.update(anyString(), anyLong())).thenReturn(1);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong())).thenReturn(List.of());

        memberRepository.create(member);
        memberRepository.delete(1L);

        Optional<Member> foundMember = memberRepository.findById(1L);
        assertFalse(foundMember.isPresent());
    }
}
