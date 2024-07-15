package gift;

import gift.model.Wishlist;
import gift.model.Member;
import gift.model.Product;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishlistRepositoryTest {
  @Autowired
  private WishlistRepository wishlistRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ProductRepository productRepository;

  private Member member;
  private Product product;

  @BeforeEach
  void setUp() {
    member = createAndSaveMember("test@example.com", "password");
    product = createAndSaveProduct("Product A", 100, "http://example.com/product-a");
  }

  @Test
  @Transactional
  void save() {
    Wishlist wishlist = new Wishlist();
    wishlist.setMember(member);
    wishlist.setProduct(product);

    Wishlist savedWishlist = wishlistRepository.save(wishlist);

    assertAll(
            () -> assertThat(savedWishlist.getId()).isNotNull(),
            () -> assertThat(savedWishlist.getMember().getId()).isEqualTo(member.getId()),
            () -> assertThat(savedWishlist.getProduct().getName()).isEqualTo(product.getName()),
            () -> assertThat(savedWishlist.getProduct().getImageUrl()).isEqualTo(product.getImageUrl())
    );
  }

  @Test
  void findByMemberId() {
    Wishlist wishlist = new Wishlist();
    wishlist.setMember(member);
    wishlist.setProduct(product);

    wishlistRepository.save(wishlist);

    Pageable pageable = PageRequest.of(0, 10);
    Page<Wishlist> wishlistsPage = wishlistRepository.findByMemberId(member.getId(), pageable);
    List<Wishlist> wishlists = wishlistsPage.getContent();

    assertThat(wishlists).hasSize(1);
    Wishlist foundWishlist = wishlists.get(0);

    assertAll(
            () -> assertThat(foundWishlist.getMember().getId()).isEqualTo(member.getId()),
            () -> assertThat(foundWishlist.getProduct().getName()).isEqualTo(product.getName()),
            () -> assertThat(foundWishlist.getProduct().getImageUrl()).isEqualTo(product.getImageUrl())
    );
  }

  @Test
  void findByMemberIdWithPagination() {
    for (int i = 1; i <= 5; i++) {
      Product newProduct = createAndSaveProduct("Product " + i, 100 + i, "http://example.com/product-" + i);
      Wishlist wishlist = new Wishlist();
      wishlist.setMember(member);
      wishlist.setProduct(newProduct);
      wishlistRepository.save(wishlist);
    }

    Pageable pageable = PageRequest.of(0, 3);
    Page<Wishlist> wishlistPage = wishlistRepository.findByMemberId(member.getId(), pageable);

    assertThat(wishlistPage.getContent()).hasSize(3);
    assertThat(wishlistPage.getTotalElements()).isEqualTo(5);
    assertThat(wishlistPage.getTotalPages()).isEqualTo(2);
    assertThat(wishlistPage.getNumber()).isEqualTo(0);
    assertThat(wishlistPage.getSize()).isEqualTo(3);
  }
  private Member createAndSaveMember(String email, String password) {
    Member member = new Member();
    member.setEmail(email);
    member.setPassword(password);
    return memberRepository.save(member);
  }

  private Product createAndSaveProduct(String name, int price, String imageUrl) {
    Product product = new Product();
    product.update(name, price, imageUrl);
    return productRepository.save(product);
  }
}
