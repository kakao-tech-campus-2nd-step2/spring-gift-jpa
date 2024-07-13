package gift.wish.service;

import gift.product.dto.ProductDto;
import gift.user.dto.UserDto;
import gift.product.entity.Product;
import gift.user.entity.User;
import gift.wish.dto.WishDto;
import gift.wish.entity.Wish;
import gift.user.repository.UserRepository;
import gift.product.repository.ProductRepository;
import gift.wish.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

  private final WishRepository wishRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  @Autowired
  public WishService(WishRepository wishRepository, UserRepository userRepository,
      ProductRepository productRepository) {
    this.wishRepository = wishRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  public List<WishDto> getWishesByMemberEmail(String memberEmail) {
    User user = userRepository.findByEmail(memberEmail)
        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
    List<Wish> wishes = wishRepository.findByUser(user);
    return wishes.stream().map(wish -> new WishDto(
        wish.getId(),
        new UserDto(
            wish.getUser().getId(),
            wish.getUser().getEmail(),
            wish.getUser().getPassword(),
            wish.getUser().getRole()
        ),
        new ProductDto(
            wish.getProduct().getId(),
            wish.getProduct().getName(),
            wish.getProduct().getPrice(),
            wish.getProduct().getImageUrl()
        )
    )).collect(Collectors.toList());
  }

  public WishDto addWish(WishDto wishDto) {
    User user = userRepository.findById(wishDto.getUser().getId())
        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
    Product product = productRepository.findById(wishDto.getProduct().getId())
        .orElseThrow(() -> new RuntimeException("상품 정보를 찾을 수 없습니다."));

    if (wishRepository.findByUserAndProduct(user, product).isPresent()) {
      throw new RuntimeException("중복된 위시리스트 항목입니다.");
    }

    Wish wish = new Wish();
    wish.setUser(user);
    wish.setProduct(product);
    Wish savedWish = wishRepository.save(wish);

    return new WishDto(
        savedWish.getId(),
        new UserDto(
            savedWish.getUser().getId(),
            savedWish.getUser().getEmail(),
            savedWish.getUser().getPassword(),
            savedWish.getUser().getRole()
        ),
        new ProductDto(
            savedWish.getProduct().getId(),
            savedWish.getProduct().getName(),
            savedWish.getProduct().getPrice(),
            savedWish.getProduct().getImageUrl()
        )
    );
  }

  public void removeWish(String memberEmail, Long productId) {
    User user = userRepository.findByEmail(memberEmail)
        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품 정보를 찾을 수 없습니다."));
    wishRepository.deleteByUserAndProduct(user, product);
  }
}
