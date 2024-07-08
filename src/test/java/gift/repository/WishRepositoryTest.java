package gift.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import gift.model.Wish;
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

public class WishRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WishRepository wishRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("위시리스트 항목 추가 및 ID로 조회")
    public void testSaveAndFindById() {
        Wish wish = new Wish(null, 1L, 1L);
        Wish savedWish = new Wish(1L, 1L, 1L);

        when(jdbcTemplate.update(any(String.class), anyLong(), anyLong())).thenReturn(1);
        when(jdbcTemplate.query(any(String.class), any(RowMapper.class), anyLong())).thenReturn(List.of(savedWish));

        wishRepository.create(wish);
        Optional<Wish> foundWish = wishRepository.findById(1L);

        assertTrue(foundWish.isPresent());
        assertEquals(1L, foundWish.get().getMemberId());
        assertEquals(1L, foundWish.get().getProductId());
    }

    @Test
    @DisplayName("모든 위시리스트 항목 조회")
    public void testFindAllByMemberId() {
        Wish wish1 = new Wish(1L, 1L, 1L);
        Wish wish2 = new Wish(2L, 1L, 2L);

        when(jdbcTemplate.query(any(String.class), any(RowMapper.class), anyLong())).thenReturn(List.of(wish1, wish2));

        List<Wish> wishes = wishRepository.findAllByMemberId(1L);
        assertEquals(2, wishes.size());
    }

    @Test
    @DisplayName("위시리스트 항목 삭제")
    public void testDeleteById() {
        Wish wish = new Wish(1L, 1L, 1L);

        when(jdbcTemplate.update(any(String.class), anyLong())).thenReturn(1);
        when(jdbcTemplate.query(any(String.class), any(RowMapper.class), anyLong())).thenReturn(List.of());

        wishRepository.create(wish);
        wishRepository.deleteById(1L);

        Optional<Wish> foundWish = wishRepository.findById(1L);
        assertFalse(foundWish.isPresent());
    }

    @Test
    @DisplayName("회원 ID와 상품 ID로 위시리스트 항목 존재 여부 확인")
    public void testExistsByMemberIdAndProductId() {
        when(jdbcTemplate.queryForObject(any(String.class), any(Class.class), anyLong(), anyLong())).thenReturn(1);

        boolean exists = wishRepository.existsByMemberIdAndProductId(1L, 1L);
        assertTrue(exists);
    }
}
