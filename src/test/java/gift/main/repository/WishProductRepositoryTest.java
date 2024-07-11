package gift.main.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import gift.main.entity.WishProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishProductRepositoryTest {
    @Autowired
    private  WishProductRepository wishProductRepository;


    @Test
    public void 유저아이디값으로여러개조회하기() {
        //given(준비)
        Long userId = 1L;
        WishProduct wishProduct1 = new WishProduct(1L, userId);
        WishProduct wishProduct2 = new WishProduct(1L, userId);
        WishProduct wishProduct3 = new WishProduct(1L, 2L);
        wishProductRepository.save(wishProduct1);
        wishProductRepository.save(wishProduct2);
        wishProductRepository.save(wishProduct3);

        //when(실행)
        List<WishProduct> wishProducts = wishProductRepository.findAllByUserId(userId);
        //then(결과)
        assertEquals(2, wishProducts.size());
    }


    @Test
    public void 값삭제하기() {
        //given(준비)
        Long userId = 1L;
        Long productId = 2L;
        WishProduct wishProduct = new WishProduct(productId, userId);
        wishProductRepository.save(wishProduct);

        //when(실행)
        wishProductRepository.deleteByProductIdAndUserId(productId,userId);
        //then(결과)
        assertEquals(0, wishProductRepository.findAllByUserId(userId).size());

    }


}