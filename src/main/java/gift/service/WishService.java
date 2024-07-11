package gift.service;

import gift.controller.wish.WishRequest;
import gift.domain.Wish;
import gift.exception.WishNotExistsException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishRequest> findAll(String email) {
        return wishRepository.findByEmail(email).stream()
            .map(WishRequest::of)
            .toList();
    }

    public WishRequest update(String email, WishRequest wish) {
        Optional<Wish> foundWish = wishRepository.findByEmailAndProductId(email, wish.productId());
        if (foundWish.isEmpty()) {
            return WishRequest.of(wishRepository.save(new Wish(email, wish.productId(), wish.count())));
        }
        Wish wishToUpdate = foundWish.get();
        wishToUpdate.setCount(wish.count());
        return WishRequest.of(wishRepository.save(wishToUpdate));
    }

    @Transactional
    public void delete(String email, Long productId) {
        wishRepository.findByEmailAndProductId(email, productId).orElseThrow(WishNotExistsException::new);
        wishRepository.deleteByEmailAndProductId(email, productId);
    }
}