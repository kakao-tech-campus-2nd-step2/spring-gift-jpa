package gift.wish.service;

import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import gift.user.domain.User;
import gift.user.persistence.UserRepository;
import gift.wish.domain.Wish;
import gift.wish.exception.WishNotFoundException;
import gift.wish.persistence.WishRepository;
import gift.wish.service.dto.WishInfo;
import gift.wish.service.dto.WishParam;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishService(WishRepository wishRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository
    ) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Long saveWish(WishParam wishRequest) {
        Product product = productRepository.findById(wishRequest.productId())
                .orElseThrow(() -> ProductNotFoundException.of(wishRequest.productId()));
        User user = userRepository.getReferenceById(wishRequest.userId());

        Wish wish = new Wish(wishRequest.amount(), product, user);
        wishRepository.save(wish);
        return wish.getId();
    }

    @Transactional
    public void updateWish(WishParam wishRequest, final Long wishId) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));

        wish.modify(wishRequest.userId(), wishRequest.productId(), wishRequest.amount());
    }

    @Transactional(readOnly = true)
    public List<WishInfo> getWishList(final Long userId) {
        List<Wish> wishes = wishRepository.findWishesByUserId(userId);

        var responses = wishes.stream()
                .map(WishInfo::from)
                .toList();

        return responses;
    }


    @Transactional(readOnly = true)
    public WishInfo getWish(final Long wishId, final Long userId) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));
        if (!wish.isOwner(userId)) {
            throw WishNotFoundException.of(wishId);
        }

        return WishInfo.from(wish);
    }

    @Transactional
    public void deleteWish(final Long wishId, final Long userId) {
        Wish wish = wishRepository.findById(wishId)
                .orElseThrow(() -> WishNotFoundException.of(wishId));
        if (!wish.isOwner(userId)) {
            throw WishNotFoundException.of(wishId);
        }

        wishRepository.delete(wish);
    }
}
