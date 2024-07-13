package gift.Service;

import gift.DTO.WishDTO;
import gift.Entity.WishEntity;
import gift.Repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WishServiceTest {

    @Mock
    private WishRepository wishRepository;

    @InjectMocks
    private WishService wishService;

    public WishServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWishes() {
        Pageable pageable = PageRequest.of(0, 10);
        WishEntity wish1 = new WishEntity(1L, null, null, "Wish1");
        WishEntity wish2 = new WishEntity(2L, null, null, "Wish2");
        Page<WishEntity> wishPage = new PageImpl<>(Arrays.asList(wish1, wish2), pageable, 2);

        when(wishRepository.findAll(pageable)).thenReturn(wishPage);

        Page<WishDTO> result = wishService.getWishes(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("Wish1", result.getContent().get(0).getComment());
        assertEquals("Wish2", result.getContent().get(1).getComment());
    }
}
