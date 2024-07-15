//package gift;
//
//import gift.member.model.Member;
//import gift.member.repository.MemberRepository;
//import gift.product.model.Product;
//import gift.product.repository.ProductRepository;
//import gift.wishlist.model.WishList;
//import gift.wishlist.repository.WishListRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@Transactional
//public class WishListRepositoryTest {
//
//    @Autowired
//    private WishListRepository wishListRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setUp() {
//        Member member = new Member("example@test.com", "password");
//        memberRepository.save(member);
//    }
//
//    @Test
//    public void saveWish() {
//        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
//        Product product = new Product("test", 100, "https://www.google.com");
//        product = productRepository.save(product);
//
//        WishList wishlist = new WishList(member, product);
//        wishlist = wishListRepository.save(wishlist);
//
//        assertThat(wishListRepository.existsById(wishlist.getId())).isTrue();
//    }
//
//    @Test
//    public void removeWish() {
//        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
//        Product product = new Product("test", 100, "https://www.google.com");
//        product = productRepository.save(product);
//
//        WishList wishlist = new WishList(member, product);
//        wishlist = wishListRepository.save(wishlist);
//
//        wishListRepository.deleteById(wishlist.getId());
//
//        assertThat(wishListRepository.existsById(wishlist.getId())).isFalse();
//    }
//
//    @Test
//    public void findAllByUserId() {
//        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
//        Product product = new Product("test", 100, "https://www.google.com");
//        product = productRepository.save(product);
//
//        WishList wishlist = new WishList(member, product);
//        wishListRepository.save(wishlist);
//
//        assertThat(wishListRepository.findByMember(member)).hasSize(1);
//    }
//}