package gift;

import gift.model.Wishlist;
import gift.model.Member;
import gift.repository.MemberRepository;
import gift.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishlistRepositoryTest {
  @Autowired
  private WishlistRepository wishlistRepository;
  @Autowired
  private MemberRepository memberRepository;
  private Member member;

  @Test
  @Transactional
  void save(){
    member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    Member savedMember = memberRepository.save(member);

    Wishlist wishlist = new Wishlist();
    wishlist.setMemberId(savedMember.getId());
    wishlist.setProductName("Product A");
    wishlist.setProductUrl("http://example.com/product-a");

    Wishlist savedWishlist = wishlistRepository.save(wishlist);
    assertAll(
            ()-> assertThat(savedWishlist.getId()).isNotNull(),
            ()-> assertThat(savedWishlist.getMemberId()).isEqualTo(wishlist.getMemberId()),
            ()-> assertThat(savedWishlist.getProductName()).isEqualTo(wishlist.getProductName()),
            ()-> assertThat(savedWishlist.getProductUrl()).isEqualTo(wishlist.getProductUrl())
    );
  }
  @Test
  void findByMemberId(){
    member = new Member();
    member.setEmail("test@example.com");
    member.setPassword("password");
    Member savedMember = memberRepository.save(member);

    Wishlist wishlist = new Wishlist();
    wishlist.setMemberId(savedMember.getId());
    wishlist.setProductName("Product A");
    wishlist.setProductUrl("http://example.com/product-a");

    Wishlist savedWishlist = wishlistRepository.save(wishlist);

    String expected = "Product A";
    String actual = savedWishlist.getProductName();
    assertThat(actual).isEqualTo(expected);
  }
}
