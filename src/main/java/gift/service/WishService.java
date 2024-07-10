package gift.service;

import gift.dto.WishRequest;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import gift.exception.ProductNotFoundException;
import gift.exception.UserAuthException;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public WishService(WishRepository wishRepository, ProductRepository productRepository,
        UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addWish(Long userId, WishRequest request) {
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new ProductNotFoundException("product가 없습니다."));

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserAuthException("ID에 해당하는 유저가 없습니다."));

        Wish wish = new Wish(user, product, request.getNumber());
        wishRepository.save(wish);
    }

    public List<Wish> getWishes(Long userId) {
        return wishRepository.findByUserId(userId);
    }

    public Wish getOneWish(Long userId, Long wishId) {
        return wishRepository.findByUserIdAndId(userId, wishId);
    }

    public void removeWish(Long userId, Long wishId) {
        wishRepository.deleteByUserIdAndId(userId, wishId);
    }

    public void updateNumber(Long userId, Long wishId, int number) {
        wishRepository.updateWishNumber(userId, wishId, number);
    }
}
