package gift.service;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;

import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

  private final WishRepository wishRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;


  @Autowired
  public WishService(WishRepository wishRepository, MemberRepository memberRepository,
      ProductRepository productRepository) {
    this.wishRepository = wishRepository;
    this.memberRepository = memberRepository;
    this.productRepository = productRepository;
  }

  public List<Wish> getWishesByMemberEmail(String memberEmail) {
    Member member = memberRepository.findByEmail(memberEmail)
        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
    return wishRepository.findByMember(member);
  }

  public Wish addWish(Wish wish) {
    Member member = wish.getMember();
    Product product = wish.getProduct();

    if (wishRepository.findByMemberAndProduct(member, product).isPresent()) {
      throw new RuntimeException("중복된 위시리스트 항목입니다.");
    }
    return wishRepository.save(wish);
  }

  public void removeWish(String memberEmail, Long productId) {
    Member member = memberRepository.findByEmail(memberEmail)
        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품 정보를 찾을 수 없습니다."));
    wishRepository.deleteByMemberAndProduct(member, product);

  }
}
