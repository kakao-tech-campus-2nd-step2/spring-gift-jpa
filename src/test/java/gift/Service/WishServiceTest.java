package gift.Service;

import gift.DTO.WishDTO;
import gift.Entity.ProductEntity;
import gift.Entity.UserEntity;
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
        UserEntity user = new UserEntity(); // Assuming you have a UserEntity class
        user.setId(1L);
        ProductEntity product1 = new ProductEntity(); // Assuming you have a ProductEntity class
        product1.setId(1L);
        ProductEntity product2 = new ProductEntity();
        product2.setId(2L);

        WishEntity wish1 = new WishEntity(1L, user, product1, "Wish1");
        WishEntity wish2 = new WishEntity(2L, user, product2, "Wish2");
        Page<WishEntity> wishPage = new PageImpl<>(Arrays.asList(wish1, wish2), pageable, 2);

        when(wishRepository.findAll(pageable)).thenReturn(wishPage);

        Page<WishDTO> result = wishService.getWishes(pageable);

        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("Wish1", result.getContent().get(0).getProductName());
        assertEquals("Wish2", result.getContent().get(1).getProductName());
        assertEquals(1L, result.getContent().get(0).getUserId());
        assertEquals(1L, result.getContent().get(0).getProductId());
        assertEquals(1L, result.getContent().get(1).getUserId());
        assertEquals(2L, result.getContent().get(1).getProductId());
    }
}
