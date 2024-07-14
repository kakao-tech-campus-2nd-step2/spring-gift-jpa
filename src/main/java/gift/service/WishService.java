package gift.service;

import gift.common.dto.PageResponse;
import gift.common.exception.ExistWishException;
import gift.common.exception.ProductNotFoundException;
import gift.common.exception.UserNotFoundException;
import gift.common.exception.WishNotFoundException;
import gift.model.product.Product;
import gift.model.user.User;
import gift.model.wish.Wish;
import gift.model.wish.WishRequest;
import gift.model.wish.WishResponse;
import gift.model.wish.WishUpdateRequest;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository,
        UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public PageResponse<WishResponse> findAllWish(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Wish> wishList = wishRepository.findByUserId(userId, pageRequest);

        List<WishResponse> wishResponses = wishList.getContent().stream()
            .map(WishResponse::from)
            .toList();

        return PageResponse.from(wishResponses, wishList);
    }

    @Transactional
    public void addWistList(Long userId, WishRequest wishRequest) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        Product product = productRepository.findById(wishRequest.productId())
            .orElseThrow(ProductNotFoundException::new);

        if (wishRepository.existsByProductIdAndUserId(product.getId(), userId)) {
            throw new ExistWishException();
        }

        wishRepository.save(wishRequest.toEntity(user, product, wishRequest.count()));
    }

    @Transactional
    public void updateWishList(Long userId, Long wishId, WishUpdateRequest wishRequest) {
        if (wishRequest.count() == 0) {
            deleteWishList(userId, wishId);
            return;
        }

        Wish wish = wishRepository.findById(wishId).orElseThrow(WishNotFoundException::new);

        if (!wish.isOwner(userId)) {
            throw new WishNotFoundException();
        }

        wish.updateWish(wishRequest.count());
    }

    @Transactional
    public void deleteWishList(Long userId, Long wishId) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(WishNotFoundException::new);

        if (!wish.isOwner(userId)) {
            throw new WishNotFoundException();
        }

        wishRepository.deleteById(wishId);
    }
}
