package gift.service;

import gift.controller.wish.WishDto;
import gift.exception.WishNotExistsException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
    }

    public List<WishDto> findAll(String email) {
        return wishRepository.findAll(email).stream()
            .map(WishDto::of)
            .toList();
    }

    public WishDto update(String email, WishDto wish) {
        if (wishRepository.find(email, wish.productId()).isEmpty()) {
            return WishDto.of(wishRepository.save(email, wish));
        }
        return WishDto.of(wishRepository.update(email, wish));
    }

    public void delete(String email, Long productId) {
        wishRepository.find(email, productId).orElseThrow(WishNotExistsException::new);
        wishRepository.delete(email, productId);
    }
}
