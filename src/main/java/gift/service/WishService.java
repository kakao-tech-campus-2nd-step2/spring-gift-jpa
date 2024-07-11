package gift.service;

import gift.entity.Wish;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

  private final WishRepository wishRepository;

  @Autowired
  public WishService(WishRepository wishRepository) {
    this.wishRepository = wishRepository;
  }

  public List<Wish> getWishesByMemberEmail(String memberEmail) {
    return wishRepository.findByMemberEmail(memberEmail);
  }

  public Wish addWish(Wish wish) {
    if (wishRepository.findByMemberEmailAndProductId(wish.getMemberEmail(), wish.getProductId()).isPresent()){
      throw new RuntimeException("중복된 위시리스트 항목입니다.");
    }
    return wishRepository.save(wish);
  }

  public void removeWish(String memberEmail, Long productId) {
    wishRepository.deleteByMemberEmailAndProductId(memberEmail, productId);
  }
}
