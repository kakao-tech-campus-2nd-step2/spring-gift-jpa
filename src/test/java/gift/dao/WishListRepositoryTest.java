package gift.dao;

import gift.product.dao.MemberDao;
import gift.product.dao.ProductDao;
import gift.product.dao.WishListDao;
import gift.product.model.Member;
import gift.product.model.Product;
import gift.product.model.WishProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private WishListDao wishListDao;

    private Product originProduct;
    private Member originMember;

    @BeforeEach
    void setUp() {
        originProduct = productDao.save(new Product("product", 1000, "image.url"));
        originMember = memberDao.save(new Member("user@email.com", "1234"));
    }

    @Test
    void testRegisterWishList() {
        WishProduct product = new WishProduct(originProduct.getId(), originMember.getId());
        wishListDao.save(product);
    }

    @Test
    void testDeleteWishList() {
        WishProduct wishProduct = wishListDao.save(new WishProduct(originProduct.getId(), originMember.getId()));
        wishListDao.deleteById(wishProduct.getId());
    }

}
