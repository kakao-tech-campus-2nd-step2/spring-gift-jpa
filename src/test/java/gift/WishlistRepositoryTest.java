package gift;

import gift.model.Wishlist;
import gift.model.Member;
import gift.model.Product;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

  @Test
  @Transactional
  void save() {
    member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    Member savedMember = memberRepository.save(member);

    product = new Product();
    product.setName("Product A");
    product.setPrice(100);
    product.setImageUrl("http://example.com/product-a");
    Product savedProduct = productRepository.save(product);

    Wishlist wishlist = new Wishlist();
    wishlist.setMember(savedMember);
    wishlist.setProduct(savedProduct);

    Wishlist savedWishlist = wishlistRepository.save(wishlist);

    assertAll(
            () -> assertThat(savedWishlist.getId()).isNotNull(),
            () -> assertThat(savedWishlist.getMember().getId()).isEqualTo(savedMember.getId()),
            () -> assertThat(savedWishlist.getProduct().getName()).isEqualTo(savedProduct.getName()),
            () -> assertThat(savedWishlist.getProduct().getImageUrl()).isEqualTo(savedProduct.getImageUrl())
    );
  }

  @Test
  void findByMemberId() {
    member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    Member savedMember = memberRepository.save(member);

    product = new Product();
    product.setName("Product A");
    product.setPrice(100);
    product.setImageUrl("http://example.com/product-a");
    Product savedProduct = productRepository.save(product);

    Wishlist wishlist = new Wishlist();
    wishlist.setMember(savedMember);
    wishlist.setProduct(savedProduct);

    wishlistRepository.save(wishlist);

    List<Wishlist> wishlists = wishlistRepository.findByMemberId(savedMember.getId());

    assertThat(wishlists).hasSize(1);
    Wishlist foundWishlist = wishlists.get(0);

    assertAll(
            () -> assertThat(foundWishlist.getMember().getId()).isEqualTo(savedMember.getId()),
            () -> assertThat(foundWishlist.getProduct().getName()).isEqualTo(savedProduct.getName()),
            () -> assertThat(foundWishlist.getProduct().getImageUrl()).isEqualTo(savedProduct.getImageUrl())
    );
  }
}
