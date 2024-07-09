package gift.repository;

import gift.entity.Product;
import gift.entity.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WishRepositoryTest {

    private static final Long MEMBER_ID = 1L;

    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void findAllByMemberIdWithProduct() {
        //Given
        Product product1 = productRepository.save(new Product("아몬드", 500, "image.jpg"));
        Product product2 = productRepository.save(new Product("초코", 500, "image.2jpg"));
        Wish wish1 = new Wish(MEMBER_ID, 5, product1);
        Wish wish2 = new Wish(MEMBER_ID, 10, product2);
        wishRepository.save(wish1);
        wishRepository.save(wish2);

        //When
        List<Wish> wishes = wishRepository.findAllByMemberIdWithProduct(MEMBER_ID);

        //Then
        assertThat(wishes).isNotEmpty();
        for (Wish wish : wishes) {
            System.out.println(wish.getProduct().getName());
        }
    }

    @Test
    void findByMemberIdAndProductId() {
        //Given

        //When

        //Then
    }
}