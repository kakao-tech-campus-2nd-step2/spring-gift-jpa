package gift;

import gift.permission.repository.PermissionRepository;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.user.entity.User;
import gift.wishlist.entity.WishList;
import gift.wishlist.model.WishListId;
import gift.wishlist.repository.WishListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    private WishListRepository wishListRepository;
    private ProductRepository productRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public WishListRepositoryTest(WishListRepository wishListRepository,
        ProductRepository productRepository, PermissionRepository permissionRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.permissionRepository = permissionRepository;
    }

    @Test
    public void createTest() {
        Product product = new Product("물감", 3000, "mulgam.png");
        User user = new User("luckyrkd@naver.com", "aaaaa11111");
        Product actualProduct = productRepository.save(product);
        User actualUser = permissionRepository.save(user);
        WishList wishList = new WishList(new WishListId(actualUser, actualProduct), 1);

        WishList actualWishList = wishListRepository.save(wishList);

        // 다른 두 테스트에서는 동일성도 보장이 됐는데, 왜 이번 테스트에서는 동등성만 보장이 되는지 잘 모르겠습니다.
        // id가 not null이라서 save 연산 중에 persist가 되지 않고, 영속성 컨텍스트에 보관되지 않아서 동일성이 보장되지 않는 것인가요?
        Assertions.assertThat(wishList == actualWishList).isTrue(); // 실패하는 테스트 (동일성 보장)

        // 동등성 보장은 통과
        Assertions.assertThat(wishList.getQuantity() == actualWishList.getQuantity()).isTrue();
        Assertions.assertThat(
            wishList.getWishListId().getProduct() == actualWishList.getWishListId()
                .getProduct()).isTrue();
        Assertions.assertThat(
            wishList.getWishListId().getUser() == actualWishList.getWishListId()
                .getUser()).isTrue();
    }

    @Test
    public void readTest() {
        Product product = new Product("물감", 3000, "mulgam.png");
        User user = new User("luckyrkd@naver.com", "aaaaa11111");
        Product actualProduct = productRepository.save(product);
        User actualUser = permissionRepository.save(user);
        WishListId wishListId = new WishListId(actualUser, actualProduct);
        WishList wishList = new WishList(wishListId, 1);

        WishList actualWishList = wishListRepository.save(wishList);

        // 위시리스트 조회해서 삽입한 것과 같은지 확인
        Assertions.assertThat(wishListRepository.findById(wishListId).get().getWishListId().getProduct() == actualProduct).isTrue();
        Assertions.assertThat(wishListRepository.findById(wishListId).get().getWishListId().getUser() == actualUser).isTrue();

        // userId를 기반으로 DB를 검색하는 명령어 확인 (WishList -> WishListId -> User의 userId)
        Assertions.assertThat(wishListRepository.findByWishListIdUserUserId(actualUser.getUserId())).size().isEqualTo(1);
    }

    @Test
    public void UpdateTest() {
        Product product = new Product("물감", 3000, "mulgam.png");
        User user = new User("luckyrkd@naver.com", "aaaaa11111");
        Product actualProduct = productRepository.save(product);
        User actualUser = permissionRepository.save(user);
        WishListId wishListId = new WishListId(actualUser, actualProduct);
        WishList wishList = new WishList(wishListId, 1);

        WishList actualWishList = wishListRepository.save(wishList);
        actualWishList.updateQuantity(5);

        // 바꾼 quantity가 잘 적용됐는지 확인
        Assertions.assertThat(wishListRepository.findById(wishListId).get().getQuantity()).isEqualTo(5);
    }

    @Test
    public void deleteTest() {
        Product product = new Product("물감", 3000, "mulgam.png");
        User user = new User("luckyrkd@naver.com", "aaaaa11111");
        Product actualProduct = productRepository.save(product);
        User actualUser = permissionRepository.save(user);
        WishListId wishListId = new WishListId(actualUser, actualProduct);
        WishList wishList = new WishList(wishListId, 1);

        WishList actualWishList = wishListRepository.save(wishList);
        wishListRepository.delete(wishList);

        Assertions.assertThat(wishListRepository.count()).isEqualTo(0);
    }
}
