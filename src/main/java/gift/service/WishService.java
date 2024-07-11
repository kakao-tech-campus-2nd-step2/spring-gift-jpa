package gift.service;

import gift.dto.wish.AddWishRequest;
import gift.dto.wish.UpdateWishRequest;
import gift.dto.wish.WishResponse;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import gift.exception.wish.WishNotFoundException;
import gift.repository.WishRepository;
import gift.util.mapper.WishMapper;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductService productService;
    private final UserService userService;

    public WishService(WishRepository wishRepository, ProductService productService, UserService userService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<WishResponse> getWishes(Long userId) {
        List<Wish> wishes = wishRepository.findByUserId(userId);

        if(wishes == null || wishes.isEmpty()) {
            throw new WishNotFoundException("위시리스트가 존재하지 않습니다.");
        }

        return wishes.stream()
            .map(WishMapper::toResponse)
            .toList();
    }

    public Long addWish(Long userId, AddWishRequest request) {
        Product product = productService.getProductById(request.productId());
        User user = userService.getUserById(userId);

        Wish wish = Wish.builder()
            .quantity(request.quantity())
            .user(user)
            .product(product)
            .build();

        Wish savedWish = wishRepository.save(wish);
        return savedWish.id();
    }

    public void updateWishes(List<UpdateWishRequest> requests) {
        for (UpdateWishRequest request : requests) {
            Wish wish = getWish(request.id());
            wish.changeQuantity(request.quantity());
            updateWish(wish);
        }
    }

    private void updateWish(Wish wish) {
        if (wish.isQuantityZero()) {
            deleteWish(wish);
            return;
        }

        wishRepository.save(wish);
    }

    public void deleteWishes(List<UpdateWishRequest> requests) {
        for (UpdateWishRequest request : requests) {
            deleteWish(getWish(request.id()));
        }
    }

    private void deleteWish(Wish wish) {
        wishRepository.delete(wish);
    }

    private Wish getWish(Long id) {
        return wishRepository.findById(id)
            .orElseThrow(() -> new WishNotFoundException("위시리스트를 찾을 수 없습니다."));
    }

}
